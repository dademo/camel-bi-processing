/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.database;

import fr.dademo.supervision.service.controllers.DataBackendController;
import fr.dademo.supervision.service.controllers.database.exceptions.DatabaseNotFoundException;
import fr.dademo.supervision.service.controllers.database.exceptions.DatabaseSchemaNotFoundException;
import fr.dademo.supervision.service.services.DatabaseSchemaService;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaDto;
import fr.dademo.supervision.service.services.dto.DataBackendDescriptionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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
@RequestMapping(path = "/database_schemas", produces = {
    MediaType.APPLICATION_JSON_VALUE,
    MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE,
    MediaTypes.HAL_JSON_VALUE,
})
public class DataBackendDatabaseSchemaController implements ProblemHandling {

    @Autowired
    private DatabaseSchemaService databaseSchemaService;

    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseSchemaDto> databaseDescriptionDtoPagedResourcesAssembler;

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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDatabaseSchemaDto>> findDatabaseSchemasForDatabase(@RequestParam(name = "database") Long databaseId,
                                                                                                @ParameterObject Pageable pageable) {

        final var databaseSchemas = databaseSchemaService.findSchemasForDatabase(databaseId, pageable);
        if (databaseSchemas.getTotalElements() == 0) {
            throw new DatabaseNotFoundException(databaseId);
        } else {
            return databaseDescriptionDtoPagedResourcesAssembler.toModel(
                databaseSchemas,
                this::mapToEntityModel,
                WebMvcLinkBuilder.linkTo(DataBackendDatabaseSchemaController.class).withSelfRel()
            );
        }
    }

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
    @GetMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<DataBackendDatabaseSchemaDto> findDataBackendDatabaseSchemaById(@PathVariable("id") @Min(1) @Nonnull Long schemaId) {

        final var databaseSchema = databaseSchemaService.findSchemaById(schemaId)
            .orElseThrow(() -> new DatabaseSchemaNotFoundException(schemaId));

        return EntityModel.of(
            databaseSchema,
            getLinks(schemaId, databaseSchema.getDataBackendDatabaseId())
        );
    }

    @Nonnull
    private EntityModel<DataBackendDatabaseSchemaDto> mapToEntityModel(DataBackendDatabaseSchemaDto dataBackendDatabaseSchemaDto) {

        return EntityModel.of(
            dataBackendDatabaseSchemaDto,
            getLinks(dataBackendDatabaseSchemaDto.getId(), dataBackendDatabaseSchemaDto.getDataBackendDatabaseId())
        );
    }

    private Link[] getLinks(@Nonnull Long databaseSchemaId, @Nonnull Long databaseId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaController.class).findDataBackendDatabaseSchemaById(databaseSchemaId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaController.class).findDatabaseSchemasForDatabase(databaseId, Pageable.unpaged())
            ).withRel("database schemas"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableById(databaseId)
            ).withRel("tables"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaViewController.class).findDataBackendDatabaseSchemaViewById(databaseId)
            ).withRel("views"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexById(databaseId)
            ).withRel("indexes"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDataBackendById(databaseId)
            ).withRel("database"),
        };
    }
}
