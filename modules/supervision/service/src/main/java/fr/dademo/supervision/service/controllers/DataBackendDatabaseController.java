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

import fr.dademo.supervision.service.controllers.exceptions.DatabaseNotFoundException;
import fr.dademo.supervision.service.controllers.hal.DataBackendDatabaseRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.DataBackendDatabaseSchemaRepresentationModelAssembler;
import fr.dademo.supervision.service.services.DatabaseSchemaService;
import fr.dademo.supervision.service.services.DatabaseService;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaDto;
import fr.dademo.supervision.service.services.dto.DataBackendDescriptionDto;
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
    private PagedResourcesAssembler<DataBackendDatabaseDto> databaseDescriptionDtoPagedResourcesAssembler;
    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseSchemaDto> databaseSchemaDescriptionDtoPagedResourcesAssembler;

    @PageableAsQueryParam
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found database",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionDto.class))
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
        @ApiResponse(responseCode = "404", description = "Database does not exists or no schemas are linked to the database",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/schemas")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDatabaseSchemaDto>> findDatabaseSchemasForDatabase(@PathVariable("id") @Min(1) Long databaseId,
                                                                                                @ParameterObject Pageable pageable) {

        final var databaseSchemas = databaseSchemaService.findSchemasForDatabase(databaseId, pageable);
        if (databaseSchemas.getTotalElements() == 0) {
            throw new DatabaseNotFoundException(databaseId);
        } else {
            return databaseSchemaDescriptionDtoPagedResourcesAssembler.toModel(
                databaseSchemas,
                DataBackendDatabaseSchemaRepresentationModelAssembler.INSTANCE,
                WebMvcLinkBuilder.linkTo(DataBackendDatabaseSchemaController.class, databaseId).withSelfRel()
            );
        }
    }
}
