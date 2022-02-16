/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers;

import fr.dademo.supervision.service.controllers.exceptions.DataBackendNotFoundException;
import fr.dademo.supervision.service.services.DataBackendService;
import fr.dademo.supervision.service.services.dto.DataBackendDescriptionDto;
import fr.dademo.supervision.service.services.dto.DataBackendDescriptionLightDto;
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
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import javax.annotation.Nonnull;
import javax.validation.constraints.Min;

/**
 * @author dademo
 */
@RestController
@RequestMapping(path = "/data-backends", produces = {
    MediaType.APPLICATION_JSON_VALUE,
    MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE,
    MediaTypes.HAL_JSON_VALUE,
})
public class DataBackendController implements ProblemHandling {

    @Autowired
    private DataBackendService dataBackendService;

    @Autowired
    private PagedResourcesAssembler<DataBackendDescriptionLightDto> dataBackendDescriptionLightDtoPagedResourcesAssembler;

    @Autowired
    private PagedResourcesAssembler<DataBackendDescriptionDto> dataBackendDescriptionDtoPagedResourcesAssembler;

    @Operation(summary = "Get a list of data backends")
    @PageableAsQueryParam
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found data backends",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionDto.class))
            })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDescriptionDto>> findAllDataBackends(@ParameterObject Pageable pageable) {

        return dataBackendDescriptionDtoPagedResourcesAssembler.toModel(
            dataBackendService.findAllDataBackends(pageable),
            this::mapToEntityModel,
            WebMvcLinkBuilder.linkTo(DataBackendController.class).withSelfRel()
        );
    }

    @PageableAsQueryParam
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found data backend",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionLightDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Data backend not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionLightDto.class))
            })
    })
    @GetMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<DataBackendDescriptionLightDto> findDataBackendById(
        @PathVariable("id") @Min(1) @Nonnull Long dataBackendId) {

        return EntityModel.of(
            dataBackendService.findDataBackendById(dataBackendId)
                .orElseThrow(() -> new DataBackendNotFoundException(dataBackendId)),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDataBackendById(dataBackendId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findAllDataBackends(Pageable.unpaged())
            ).withRel("databases")
        );
    }

    @Nonnull
    private EntityModel<DataBackendDescriptionLightDto> mapLightToEntityModel(DataBackendDescriptionLightDto dataBackendDescriptionLightDto) {

        return EntityModel.of(
            dataBackendDescriptionLightDto,
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDataBackendById(dataBackendDescriptionLightDto.getId())
            ).withSelfRel()
        );
    }

    @Nonnull
    private EntityModel<DataBackendDescriptionDto> mapToEntityModel(DataBackendDescriptionDto dataBackendDescriptionDto) {

        return EntityModel.of(
            dataBackendDescriptionDto,
            WebMvcLinkBuilder.linkTo(DataBackendController.class).slash(dataBackendDescriptionDto.getId()).withRel("database").withSelfRel()
        );
    }

    @ExceptionHandler(value = {
        NullPointerException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Problem handleException(Exception exception) {

        return Problem.builder()
            .withTitle("Internal server error")
            .withStatus(Status.INTERNAL_SERVER_ERROR)
            .withDetail(exception.getMessage())
            .build();
    }

    @ExceptionHandler(DataBackendNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Problem handleNotFound(DataBackendNotFoundException exception) {

        return Problem.builder()
            .withCause(exception)
            .withType(WebMvcLinkBuilder.linkTo(DataBackendController.class).slash("/").toUri())
            .withTitle("Not found")
            .withStatus(Status.NOT_FOUND)
            .withDetail(exception.getMessage())
            .with("id", exception.getId())
            .build();
    }
}
