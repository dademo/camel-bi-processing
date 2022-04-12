/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.database;

import fr.dademo.supervision.service.controllers.database.exceptions.DatabaseSchemaTableNotFoundException;
import fr.dademo.supervision.service.services.DatabaseSchemaTableService;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaTableDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaTableStatisticsDto;
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
@RequestMapping(path = "/database_schema_tables", produces = {
    MediaType.APPLICATION_JSON_VALUE,
    MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE,
    MediaTypes.HAL_JSON_VALUE,
})
public class DataBackendDatabaseSchemaTableController implements ProblemHandling {

    private static final TemporalAmount DEFAULT_TEMPORAL_AMOUNT = Duration.ofMinutes(15);

    @Autowired
    private DatabaseSchemaTableService databaseSchemaTableService;

    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseSchemaTableDto> databaseDescriptionDtoPagedResourcesAssembler;

    @PageableAsQueryParam
    @Operation(summary = "Get a list of tables for a database schema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found database schemas for a database",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseSchemaDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Schema does not exists or no tables are linked to the schema",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDatabaseSchemaTableDto>> findDatabaseSchemasForDatabase(@RequestParam(name = "table") Long tableId,
                                                                                                     @ParameterObject Pageable pageable) {

        final var databaseSchemaTables = databaseSchemaTableService.findTablesForDatabaseSchema(tableId, pageable);
        if (databaseSchemaTables.getTotalElements() == 0) {
            throw new DatabaseSchemaTableNotFoundException(tableId);
        } else {
            return databaseDescriptionDtoPagedResourcesAssembler.toModel(
                databaseSchemaTables,
                this::mapToEntityModel,
                WebMvcLinkBuilder.linkTo(DataBackendDatabaseSchemaTableController.class).withSelfRel()
            );
        }
    }

    @PageableAsQueryParam
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found database schema table",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Database schema table not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<DataBackendDatabaseSchemaTableDto> findDataBackendDatabaseSchemaTableById(@PathVariable("id") @Min(1) @Nonnull Long tableId) {

        final var databaseSchemaTable = databaseSchemaTableService.findDatabaseSchemaTableById(tableId)
            .orElseThrow(() -> new DatabaseSchemaTableNotFoundException(tableId));

        return EntityModel.of(
            databaseSchemaTable,
            getLinks(tableId, databaseSchemaTable.getId())
        );
    }

    @PageableAsQueryParam
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Database schema table statistics",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Database schema table not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/{id:\\d+}/statistics")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<DataBackendDatabaseSchemaTableStatisticsDto> findDataBackendDatabaseSchemaTableStatisticsById(
        @PathVariable("id") @Min(1) @Nonnull Long tableId,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(name = "from", description = "The minimum date range", required = true) Date from,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(name = "to", description = "The maximum date range", required = true) Date to) {

        final var databaseSchemaTable = databaseSchemaTableService.findDatabaseSchemaTableById(tableId)
            .orElseThrow(() -> new DatabaseSchemaTableNotFoundException(tableId));
        final var databaseSchemaTableStatistics = databaseSchemaTableService.findDatabaseSchemaTableStatisticsBetween(tableId, from, to);

        return CollectionModel.of(
            databaseSchemaTableStatistics,
            getLinks(tableId, databaseSchemaTable.getDataBackendDatabaseSchemaId())
        );
    }

    @Nonnull
    private EntityModel<DataBackendDatabaseSchemaTableDto> mapToEntityModel(DataBackendDatabaseSchemaTableDto dataBackendDatabaseSchemaTableDto) {

        return EntityModel.of(
            dataBackendDatabaseSchemaTableDto,
            getLinks(dataBackendDatabaseSchemaTableDto.getId(), dataBackendDatabaseSchemaTableDto.getDataBackendDatabaseSchemaId())
        );
    }

    private Link[] getLinks(@Nonnull Long databaseSchemaTableId, @Nonnull Long databaseSchemaId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableById(databaseSchemaTableId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDatabaseSchemasForDatabase(databaseSchemaId, Pageable.unpaged())
            ).withRel("tables"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableStatisticsById(
                    databaseSchemaTableId,
                    Date.from(LocalDateTime.now().minus(DEFAULT_TEMPORAL_AMOUNT).toInstant(ZoneOffset.UTC)),
                    new Date()
                )
            ).withRel("table_statistics"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableById(databaseSchemaId)
            ).withRel("schema"),
        };
    }
}
