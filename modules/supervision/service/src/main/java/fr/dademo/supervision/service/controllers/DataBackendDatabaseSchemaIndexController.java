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

import fr.dademo.supervision.service.controllers.exceptions.DatabaseSchemaIndexNotFoundException;
import fr.dademo.supervision.service.controllers.hal.DataBackendDatabaseSchemaIndexRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.DataBackendDatabaseSchemaIndexStatisticsRepresentationModelAssembler;
import fr.dademo.supervision.service.services.DatabaseSchemaIndexService;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaIndexDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaIndexStatisticsDto;
import fr.dademo.supervision.service.services.dto.DataBackendDescriptionDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import javax.annotation.Nonnull;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * @author dademo
 */
@RestController
@RequestMapping(path = "/database_schema_index/{id:\\d+}", produces = {
    MediaType.APPLICATION_JSON_VALUE,
    MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE,
    MediaTypes.HAL_JSON_VALUE,
})
@Tag(name = "data-backend/database")
public class DataBackendDatabaseSchemaIndexController implements ProblemHandling {

    @Autowired
    private DatabaseSchemaIndexService databaseSchemaIndexService;
    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseSchemaIndexDto> databaseDescriptionDtoPagedResourcesAssembler;

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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<DataBackendDatabaseSchemaIndexDto> findDataBackendDatabaseSchemaIndexById(@PathVariable("id") @Min(1) @Nonnull Long indexId) {

        return DataBackendDatabaseSchemaIndexRepresentationModelAssembler.INSTANCE.toModel(
            databaseSchemaIndexService.findDatabaseSchemaIndexById(indexId)
                .orElseThrow(() -> new DatabaseSchemaIndexNotFoundException(indexId))
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
    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<DataBackendDatabaseSchemaIndexStatisticsDto> findDataBackendDatabaseSchemaIndexStatisticsById(
        @PathVariable("id") @Min(1) @Nonnull Long indexId,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(name = "from", description = "The minimum date range", required = true) Date from,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(name = "to", description = "The maximum date range", required = true) Date to) {

        return DataBackendDatabaseSchemaIndexStatisticsRepresentationModelAssembler.of(indexId, from, to)
            .toCollectionModel(databaseSchemaIndexService.findDatabaseSchemaIndexStatisticsBetween(indexId, from, to));
    }
}
