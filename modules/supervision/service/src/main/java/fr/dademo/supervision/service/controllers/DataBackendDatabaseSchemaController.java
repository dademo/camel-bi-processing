/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers;

import fr.dademo.supervision.service.controllers.exceptions.DatabaseSchemaNotFoundException;
import fr.dademo.supervision.service.controllers.hal.DataBackendDatabaseSchemaIndexRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.DataBackendDatabaseSchemaRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.DataBackendDatabaseSchemaTableRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.DataBackendDatabaseSchemaViewRepresentationModelAssembler;
import fr.dademo.supervision.service.services.DatabaseSchemaIndexService;
import fr.dademo.supervision.service.services.DatabaseSchemaService;
import fr.dademo.supervision.service.services.DatabaseSchemaTableService;
import fr.dademo.supervision.service.services.DatabaseSchemaViewService;
import fr.dademo.supervision.service.services.dto.*;
import io.swagger.v3.oas.annotations.Operation;
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
import javax.validation.constraints.Min;

/**
 * @author dademo
 */
@RestController
@RequestMapping(path = "/database_schema/{id:\\d+}", produces = {
    MediaType.APPLICATION_JSON_VALUE,
    MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE,
    MediaTypes.HAL_JSON_VALUE,
})
@Tag(name = "data-backend/database")
public class DataBackendDatabaseSchemaController implements ProblemHandling {

    @Autowired
    private DatabaseSchemaService databaseSchemaService;
    @Autowired
    private DatabaseSchemaTableService databaseSchemaTableService;
    @Autowired
    private DatabaseSchemaIndexService databaseSchemaIndexService;
    @Autowired
    private DatabaseSchemaViewService databaseSchemaViewService;
    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseSchemaDto> databaseSchemaDescriptionDtoPagedResourcesAssembler;
    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseSchemaTableDto> databaseSchemaTableDescriptionDtoPagedResourcesAssembler;
    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseSchemaIndexDto> databaseSchemaIndexDescriptionDtoPagedResourcesAssembler;
    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseSchemaViewDto> databaseSchemaViewDescriptionDtoPagedResourcesAssembler;

    @PageableAsQueryParam
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found database schema",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Database schema not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<DataBackendDatabaseSchemaDto> findDataBackendDatabaseSchemaById(@PathVariable("id") @Min(1) @Nonnull Long schemaId) {

        return DataBackendDatabaseSchemaRepresentationModelAssembler.INSTANCE.toModel(
            databaseSchemaService.findSchemaById(schemaId)
                .orElseThrow(() -> new DatabaseSchemaNotFoundException(schemaId))
        );
    }

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
    @GetMapping("/tables")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDatabaseSchemaTableDto>> findDatabaseSchemaTablesForDatabase(@PathVariable("id") @Min(1) Long databaseSchemaId,
                                                                                                          @ParameterObject Pageable pageable) {

        final var databaseSchemaTables = databaseSchemaTableService.findTablesForDatabaseSchema(databaseSchemaId, pageable);
        if (databaseSchemaTables.getTotalElements() == 0) {
            throw new DatabaseSchemaNotFoundException(databaseSchemaId);
        } else {
            return databaseSchemaTableDescriptionDtoPagedResourcesAssembler.toModel(
                databaseSchemaTables,
                DataBackendDatabaseSchemaTableRepresentationModelAssembler.INSTANCE,
                WebMvcLinkBuilder.linkTo(DataBackendDatabaseSchemaTableController.class, databaseSchemaId).withSelfRel()
            );
        }
    }

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
    @GetMapping("/views")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDatabaseSchemaViewDto>> findDatabaseSchemaViewsForDatabase(@PathVariable("id") @Min(1) Long databaseSchemaId,
                                                                                                        @ParameterObject Pageable pageable) {

        final var databaseSchemaViews = databaseSchemaViewService.findViewsForDatabaseSchema(databaseSchemaId, pageable);
        if (databaseSchemaViews.getTotalElements() == 0) {
            throw new DatabaseSchemaNotFoundException(databaseSchemaId);
        } else {
            return databaseSchemaViewDescriptionDtoPagedResourcesAssembler.toModel(
                databaseSchemaViews,
                DataBackendDatabaseSchemaViewRepresentationModelAssembler.INSTANCE,
                WebMvcLinkBuilder.linkTo(DataBackendDatabaseSchemaViewController.class, databaseSchemaId).withSelfRel()
            );
        }
    }

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
    @GetMapping("/indexes")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDatabaseSchemaIndexDto>> findDatabaseSchemaIndexesForDatabase(@PathVariable("id") @Min(1) Long databaseSchemaId,
                                                                                                           @ParameterObject Pageable pageable) {

        final var databaseSchemaIndexes = databaseSchemaIndexService.findIndexesForDatabaseSchema(databaseSchemaId, pageable);
        if (databaseSchemaIndexes.getTotalElements() == 0) {
            throw new DatabaseSchemaNotFoundException(databaseSchemaId);
        } else {
            return databaseSchemaIndexDescriptionDtoPagedResourcesAssembler.toModel(
                databaseSchemaIndexes,
                DataBackendDatabaseSchemaIndexRepresentationModelAssembler.INSTANCE,
                WebMvcLinkBuilder.linkTo(DataBackendDatabaseSchemaIndexController.class, databaseSchemaId).withSelfRel()
            );
        }
    }
}
