/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.database;

import fr.dademo.supervision.service.controllers.database.exceptions.DatabaseSchemaIndexNotFoundException;
import fr.dademo.supervision.service.services.DatabaseSchemaIndexService;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaIndexDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaIndexStatisticsDto;
import fr.dademo.supervision.service.services.dto.DataBackendDescriptionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import javax.annotation.Nonnull;
import javax.validation.constraints.Min;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAmount;
import java.util.Date;

/**
 * @author dademo
 */
@RestController
@RequestMapping(path = "/database_schema_indexes", produces = {
    MediaType.APPLICATION_JSON_VALUE,
    MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE,
    MediaTypes.HAL_JSON_VALUE,
})
public class DataBackendDatabaseSchemaIndexController implements ProblemHandling {

    private static final TemporalAmount DEFAULT_TEMPORAL_AMOUNT = Duration.ofMinutes(15);

    @Autowired
    private DatabaseSchemaIndexService databaseSchemaIndexService;

    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseSchemaIndexDto> databaseDescriptionDtoPagedResourcesAssembler;

    @PageableAsQueryParam
    @Operation(summary = "Get a list of indexes for a database schema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found database schemas for a database",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseSchemaDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Schema does not exists or no indexes are linked to the schema",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDatabaseSchemaIndexDto>> findDatabaseSchemasForDatabase(@RequestParam(name = "index") Long indexId,
                                                                                                     @ParameterObject Pageable pageable) {

        final var databaseSchemaIndexes = databaseSchemaIndexService.findIndexesForDatabaseSchema(indexId, pageable);
        if (databaseSchemaIndexes.getTotalElements() == 0) {
            throw new DatabaseSchemaIndexNotFoundException(indexId);
        } else {
            return databaseDescriptionDtoPagedResourcesAssembler.toModel(
                databaseSchemaIndexes,
                this::mapToEntityModel,
                WebMvcLinkBuilder.linkTo(DataBackendDatabaseSchemaIndexController.class).withSelfRel()
            );
        }
    }

    @PageableAsQueryParam
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found database schema index",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Database schema index not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<DataBackendDatabaseSchemaIndexDto> findDataBackendDatabaseSchemaIndexById(@PathVariable("id") @Min(1) @Nonnull Long indexId) {

        final var databaseSchemaIndex = databaseSchemaIndexService.findDatabaseSchemaIndexById(indexId)
            .orElseThrow(() -> new DatabaseSchemaIndexNotFoundException(indexId));

        return EntityModel.of(
            databaseSchemaIndex,
            getLinks(indexId, databaseSchemaIndex.getId())
        );
    }

    @PageableAsQueryParam
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Database schema index statistics",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Database schema index not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/{id:\\d+}/statistics")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<DataBackendDatabaseSchemaIndexStatisticsDto> findDataBackendDatabaseSchemaIndexStatisticsById(
        @PathVariable("id") @Min(1) @Nonnull Long indexId,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(name = "from", description = "The minimum date range", required = true) Date from,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(name = "to", description = "The maximum date range", required = true) Date to) {

        final var databaseSchemaIndex = databaseSchemaIndexService.findDatabaseSchemaIndexById(indexId)
            .orElseThrow(() -> new DatabaseSchemaIndexNotFoundException(indexId));
        final var databaseSchemaIndexStatistics = databaseSchemaIndexService.findDatabaseSchemaIndexStatisticsBetween(indexId, from, to);

        return CollectionModel.of(
            databaseSchemaIndexStatistics,
            getLinks(indexId, databaseSchemaIndex.getDataBackendDatabaseSchemaId())
        );
    }

    @Nonnull
    private EntityModel<DataBackendDatabaseSchemaIndexDto> mapToEntityModel(DataBackendDatabaseSchemaIndexDto dataBackendDatabaseSchemaIndexDto) {

        return EntityModel.of(
            dataBackendDatabaseSchemaIndexDto,
            getLinks(dataBackendDatabaseSchemaIndexDto.getId(), dataBackendDatabaseSchemaIndexDto.getDataBackendDatabaseSchemaId())
        );
    }

    private Link[] getLinks(@Nonnull Long databaseSchemaIndexId, @Nonnull Long databaseSchemaId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexById(databaseSchemaIndexId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDatabaseSchemasForDatabase(databaseSchemaId, Pageable.unpaged())
            ).withRel("indexes"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexStatisticsById(
                    databaseSchemaIndexId,
                    Date.from(LocalDateTime.now().minus(DEFAULT_TEMPORAL_AMOUNT).toInstant(ZoneOffset.UTC)),
                    new Date()
                )
            ).withRel("index_statistics"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexById(databaseSchemaId)
            ).withRel("schema"),
        };
    }
}
