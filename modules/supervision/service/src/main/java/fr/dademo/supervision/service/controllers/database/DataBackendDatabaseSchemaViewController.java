/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.database;

import fr.dademo.supervision.service.controllers.database.exceptions.DatabaseSchemaViewNotFoundException;
import fr.dademo.supervision.service.services.DatabaseSchemaViewService;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaViewDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaViewStatisticsDto;
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
@RequestMapping(path = "/database_schema_views", produces = {
    MediaType.APPLICATION_JSON_VALUE,
    MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE,
    MediaTypes.HAL_JSON_VALUE,
})
public class DataBackendDatabaseSchemaViewController implements ProblemHandling {

    private static final TemporalAmount DEFAULT_TEMPORAL_AMOUNT = Duration.ofMinutes(15);

    @Autowired
    private DatabaseSchemaViewService databaseSchemaViewService;

    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseSchemaViewDto> databaseDescriptionDtoPagedResourcesAssembler;

    @PageableAsQueryParam
    @Operation(summary = "Get a list of views for a database schema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found database schemas for a database",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseSchemaDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Schema does not exists or no views are linked to the schema",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDatabaseSchemaViewDto>> findDatabaseSchemasForDatabase(@RequestParam(name = "view") Long viewId,
                                                                                                    @ParameterObject Pageable pageable) {

        final var databaseSchemaViews = databaseSchemaViewService.findViewsForDatabaseSchema(viewId, pageable);
        if (databaseSchemaViews.getTotalElements() == 0) {
            throw new DatabaseSchemaViewNotFoundException(viewId);
        } else {
            return databaseDescriptionDtoPagedResourcesAssembler.toModel(
                databaseSchemaViews,
                this::mapToEntityModel,
                WebMvcLinkBuilder.linkTo(DataBackendDatabaseSchemaViewController.class).withSelfRel()
            );
        }
    }

    @PageableAsQueryParam
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found database schema view",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Database schema view not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<DataBackendDatabaseSchemaViewDto> findDataBackendDatabaseSchemaViewById(@PathVariable("id") @Min(1) @Nonnull Long viewId) {

        final var databaseSchemaView = databaseSchemaViewService.findDatabaseSchemaViewById(viewId)
            .orElseThrow(() -> new DatabaseSchemaViewNotFoundException(viewId));

        return EntityModel.of(
            databaseSchemaView,
            getLinks(viewId, databaseSchemaView.getId())
        );
    }

    @PageableAsQueryParam
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Database schema view statistics",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Database schema view not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/{id:\\d+}/statistics")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<DataBackendDatabaseSchemaViewStatisticsDto> findDataBackendDatabaseSchemaViewStatisticsById(
        @PathVariable("id") @Min(1) @Nonnull Long viewId,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(name = "from", description = "The minimum date range", required = true) Date from,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(name = "to", description = "The maximum date range", required = true) Date to) {

        final var databaseSchemaView = databaseSchemaViewService.findDatabaseSchemaViewById(viewId)
            .orElseThrow(() -> new DatabaseSchemaViewNotFoundException(viewId));
        final var databaseSchemaViewStatistics = databaseSchemaViewService.findDatabaseSchemaViewStatisticsBetween(viewId, from, to);

        return CollectionModel.of(
            databaseSchemaViewStatistics,
            getLinks(viewId, databaseSchemaView.getDataBackendDatabaseSchemaId())
        );
    }

    @Nonnull
    private EntityModel<DataBackendDatabaseSchemaViewDto> mapToEntityModel(DataBackendDatabaseSchemaViewDto dataBackendDatabaseSchemaViewDto) {

        return EntityModel.of(
            dataBackendDatabaseSchemaViewDto,
            getLinks(dataBackendDatabaseSchemaViewDto.getId(), dataBackendDatabaseSchemaViewDto.getDataBackendDatabaseSchemaId())
        );
    }

    private Link[] getLinks(@Nonnull Long databaseSchemaViewId, @Nonnull Long databaseSchemaId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaViewController.class).findDataBackendDatabaseSchemaViewById(databaseSchemaViewId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaViewController.class).findDatabaseSchemasForDatabase(databaseSchemaId, Pageable.unpaged())
            ).withRel("views"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaViewController.class).findDataBackendDatabaseSchemaViewStatisticsById(
                    databaseSchemaViewId,
                    Date.from(LocalDateTime.now().minus(DEFAULT_TEMPORAL_AMOUNT).toInstant(ZoneOffset.UTC)),
                    new Date()
                )
            ).withRel("view_statistics"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaViewController.class).findDataBackendDatabaseSchemaViewById(databaseSchemaId)
            ).withRel("schema"),
        };
    }
}
