/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers;

import fr.dademo.supervision.service.controllers.exceptions.DataBackendNotFoundException;
import fr.dademo.supervision.service.controllers.exceptions.DatabaseNotFoundException;
import fr.dademo.supervision.service.controllers.hal.databackend.DataBackendClusterNodeModelAssembler;
import fr.dademo.supervision.service.controllers.hal.databackend.DataBackendRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.database.DataBackendDatabaseRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.databasereplicationpeer.DataBackendDatabaseReplicationPeerRepresentationModelAssembler;
import fr.dademo.supervision.service.services.DataBackendClusterNodeService;
import fr.dademo.supervision.service.services.DataBackendService;
import fr.dademo.supervision.service.services.DatabaseReplicationPeerService;
import fr.dademo.supervision.service.services.DatabaseService;
import fr.dademo.supervision.service.services.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PagedResourcesAssembler;
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

import java.util.Optional;

/**
 * @author dademo
 */
@RestController
@RequestMapping(path = "/data-backend", produces = {
    MediaType.APPLICATION_JSON_VALUE,
    MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE,
    MediaTypes.HAL_JSON_VALUE,
})
@Tag(name = "data-backend")
public class DataBackendController implements ProblemHandling {

    public static final Long DEFAULT_EXECUTIONS_SIZE = 10L;

    @Autowired
    private DataBackendService dataBackendService;

    @Autowired
    private DataBackendClusterNodeService dataBackendClusterNodeService;
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private DatabaseReplicationPeerService databaseReplicationPeerService;
    @Autowired
    private PagedResourcesAssembler<DataBackendDescriptionDto> dataBackendDescriptionDtoPagedResourcesAssembler;
    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseDto> databaseDescriptionDtoPagedResourcesAssembler;
    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseReplicationPeerDto> databaseReplicationPeerDtoPagedResourcesAssembler;

    @PageableAsQueryParam
    @Operation(summary = "Get a list of data backends")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found data backends",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDescriptionDto.class))
            })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDescriptionDto>> findDataBackends(@ParameterObject Pageable pageable) {

        return dataBackendDescriptionDtoPagedResourcesAssembler.toModel(
            dataBackendService.findDataBackends(pageable),
            DataBackendRepresentationModelAssembler.INSTANCE,
            WebMvcLinkBuilder.linkTo(DataBackendController.class).withSelfRel()
        );
    }

    @Operation(summary = "Get a data backend description")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found data backend",
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
    public EntityModel<DataBackendDescriptionDto> findDataBackendById(@PathVariable("id") @Min(1) @Nonnull Long dataBackendId) {

        return DataBackendRepresentationModelAssembler.INSTANCE.toModel(
            dataBackendService.findDataBackendById(dataBackendId)
                .orElseThrow(() -> new DataBackendNotFoundException(dataBackendId))
        );
    }

    @PageableAsQueryParam
    @Operation(summary = "Get a list of nodes for a data backend")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found nodes for a data backend",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Data backend does not exists",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/{id:\\d+}/nodes")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<DataBackendClusterNodeDto> findClusterNodesForDataBackend(
        @PathVariable("id") @Min(1) Long dataBackendId,
        @Nullable @Param("count") @Min(0) @Parameter(name = "from", description = "The number of timestamps to query with", required = true) Long count) {

        if (Boolean.FALSE.equals(dataBackendService.existsById(dataBackendId))) {
            throw new DataBackendNotFoundException(dataBackendId);
        }
        return DataBackendClusterNodeModelAssembler.usingDataBackendId(dataBackendId).toCollectionModel(
            dataBackendClusterNodeService.findDataBackendClusterNodeByDataBackendId(
                dataBackendId,
                Optional.ofNullable(count).orElse(DEFAULT_EXECUTIONS_SIZE)
            ));
    }

    @PageableAsQueryParam
    @Operation(summary = "Get a data backend replication peers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of data backend database replication peers",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseStatisticsDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Database schema not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/{id:\\d+}/database-replication-peers")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDatabaseReplicationPeerDto>> findDataBackendDatabaseReplicationPeersById(
        @PathVariable("id") @Min(1) @Nonnull Long dataBackendId,
        @ParameterObject Pageable pageable) {


        if (Boolean.FALSE.equals(databaseService.existsById(dataBackendId))) {
            throw new DatabaseNotFoundException(dataBackendId);
        }
        return databaseReplicationPeerDtoPagedResourcesAssembler.toModel(
            databaseReplicationPeerService.findDatabaseReplicationPeersForDataBackend(dataBackendId, pageable),
            DataBackendDatabaseReplicationPeerRepresentationModelAssembler.INSTANCE,
            WebMvcLinkBuilder.linkTo(DataBackendDatabaseReplicationPeerController.class, dataBackendId).withSelfRel()
        );
    }

    @PageableAsQueryParam
    @Operation(summary = "Get a list of databases for a data backend")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found databases for a data backend",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Data backend does not exists",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/{id:\\d+}/databases")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<DataBackendDatabaseDto>> findDatabasesForDataBackend(@PathVariable("id") @Min(1) Long dataBackendId,
                                                                                       @ParameterObject Pageable pageable) {

        if (Boolean.FALSE.equals(dataBackendService.existsById(dataBackendId))) {
            throw new DataBackendNotFoundException(dataBackendId);
        }

        return databaseDescriptionDtoPagedResourcesAssembler.toModel(
            databaseService.findDatabasesForDataBackend(dataBackendId, pageable),
            DataBackendDatabaseRepresentationModelAssembler.INSTANCE,
            WebMvcLinkBuilder.linkTo(DataBackendDatabaseController.class, dataBackendId).withSelfRel()
        );
    }
}
