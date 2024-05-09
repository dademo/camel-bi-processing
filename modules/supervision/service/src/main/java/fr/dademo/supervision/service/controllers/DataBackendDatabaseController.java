/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers;

import fr.dademo.supervision.service.controllers.exceptions.DatabaseNotFoundException;
import fr.dademo.supervision.service.controllers.hal.database.DataBackendDatabaseRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.database.DataBackendDatabaseStatisticsRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.databaseschema.DataBackendDatabaseSchemaRepresentationModelAssembler;
import fr.dademo.supervision.service.services.DatabaseSchemaService;
import fr.dademo.supervision.service.services.DatabaseService;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseStatisticsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import javax.annotation.Nonnull;

import jakarta.validation.constraints.Min;
import java.util.Date;

/**
 * @author dademo
 */
@RestController
@RequestMapping(path = "/database/{id:\\d+}", produces = {
    MediaType.APPLICATION_JSON_VALUE,
    MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE,
    MediaTypes.HAL_JSON_VALUE,
})
@Tag(name = "data-backend/database")
public class DataBackendDatabaseController implements ProblemHandling {

    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private DatabaseSchemaService databaseSchemaService;
    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseSchemaDto> databaseSchemaDescriptionDtoPagedResourcesAssembler;

    @Operation(summary = "Get a data backend database description")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found database",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Data backend not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<DataBackendDatabaseDto> findDataBackendDatabaseById(@PathVariable("id") @Min(1) @Nonnull Long databaseId) {

        return DataBackendDatabaseRepresentationModelAssembler.INSTANCE
            .toModel(
                databaseService.findDatabaseById(databaseId)
                    .orElseThrow(() -> new DatabaseNotFoundException(databaseId))
            );
    }

    @PageableAsQueryParam
    @Operation(summary = "Get a list of schemas for a database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found schemas for a database",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseSchemaDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Database does not exists",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/schemas")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDatabaseSchemaDto>> findDatabaseSchemasForDatabase(@PathVariable("id") @Min(1) Long databaseId,
                                                                                                @ParameterObject Pageable pageable) {

        if (Boolean.FALSE.equals(databaseService.existsById(databaseId))) {
            throw new DatabaseNotFoundException(databaseId);
        }
        return databaseSchemaDescriptionDtoPagedResourcesAssembler.toModel(
            databaseSchemaService.findSchemasForDatabase(databaseId, pageable),
            DataBackendDatabaseSchemaRepresentationModelAssembler.INSTANCE,
            WebMvcLinkBuilder.linkTo(DataBackendDatabaseSchemaController.class, databaseId).withSelfRel()
        );
    }

    @Operation(summary = "Get a data backend database statistics by id within a date range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Database schema statistics",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseStatisticsDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Database schema not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<DataBackendDatabaseStatisticsDto> findDataBackendDatabaseStatisticsById(
        @PathVariable("id") @Min(1) @Nonnull Long databaseId,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(name = "from", description = "The minimum date range", required = true) Date from,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(name = "to", description = "The maximum date range", required = true) Date to) {

        return DataBackendDatabaseStatisticsRepresentationModelAssembler.of(databaseId, from, to)
            .toCollectionModel(databaseService.findDatabaseStatisticsBetween(databaseId, from, to));
    }

    @Operation(summary = "Get a data backend database latest statistic")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Latest database schema statistic",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseStatisticsDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Database schema not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/latest-statistic")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<DataBackendDatabaseStatisticsDto> findLatestDataBackendDatabaseStatisticById(
        @PathVariable("id") @Min(1) @Nonnull Long databaseId) {

        return databaseService.findLatestDatabaseStatistics(databaseId)
            .map(
                DataBackendDatabaseStatisticsRepresentationModelAssembler
                    .ofDatabaseWithDefault(databaseId)::toModel
            )
            .orElseThrow(() -> new DatabaseNotFoundException(databaseId));
    }
}
