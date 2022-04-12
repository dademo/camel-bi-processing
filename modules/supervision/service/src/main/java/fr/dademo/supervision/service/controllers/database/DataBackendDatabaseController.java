/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.database;

import fr.dademo.supervision.service.controllers.DataBackendController;
import fr.dademo.supervision.service.controllers.database.exceptions.DatabaseNotFoundException;
import fr.dademo.supervision.service.controllers.exceptions.DataBackendNotFoundException;
import fr.dademo.supervision.service.services.DatabaseService;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseDto;
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
@RequestMapping(path = "/databases", produces = {
    MediaType.APPLICATION_JSON_VALUE,
    MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE,
    MediaTypes.HAL_JSON_VALUE,
})
public class DataBackendDatabaseController implements ProblemHandling {

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseDto> databaseDescriptionDtoPagedResourcesAssembler;

    @PageableAsQueryParam
    @Operation(summary = "Get a list of data databases for a data backend")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found databases for a data backend",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Data backend does not exists or no databases are linked to the data backend",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDatabaseDto>> findDatabasesForDataBackend(@RequestParam(name = "dataBackend") Long dataBackendId,
                                                                                       @ParameterObject Pageable pageable) {

        final var databases = databaseService.findDatabasesForDataBackend(dataBackendId, pageable);
        if (databases.getTotalElements() == 0) {
            throw new DataBackendNotFoundException(dataBackendId);
        } else {
            return databaseDescriptionDtoPagedResourcesAssembler.toModel(
                databases,
                this::mapToEntityModel,
                WebMvcLinkBuilder.linkTo(DataBackendDatabaseController.class).withSelfRel()
            );
        }
    }

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
    @GetMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<DataBackendDatabaseDto> findDataBackendDatabaseById(@PathVariable("id") @Min(1) @Nonnull Long databaseId) {

        final var database = databaseService.findDatabaseById(databaseId)
            .orElseThrow(() -> new DatabaseNotFoundException(databaseId));

        return EntityModel.of(
            database,
            getLinks(databaseId, database.getDataBackendDescriptionId())
        );
    }

    @Nonnull
    private EntityModel<DataBackendDatabaseDto> mapToEntityModel(DataBackendDatabaseDto dataBackendDatabaseDto) {

        return EntityModel.of(
            dataBackendDatabaseDto,
            getLinks(dataBackendDatabaseDto.getId(), dataBackendDatabaseDto.getId())
        );
    }

    private Link[] getLinks(@Nonnull Long databaseId, @Nonnull Long backendDescriptionId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseController.class).findDataBackendDatabaseById(databaseId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseController.class).findDatabasesForDataBackend(backendDescriptionId, Pageable.unpaged())
            ).withRel("databases"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDataBackendById(backendDescriptionId)
            ).withRel("data backend"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaController.class).findDataBackendDatabaseSchemaById(databaseId)
            ).withRel("schemas"),
        };
    }
}
