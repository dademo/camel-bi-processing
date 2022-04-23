/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers;

import fr.dademo.supervision.service.controllers.exceptions.DatabaseReplicationPeerNotFoundException;
import fr.dademo.supervision.service.controllers.hal.databasereplicationpeer.DataBackendDatabaseReplicationPeerRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.databasereplicationpeer.DataBackendDatabaseReplicationPeerStatisticsRepresentationModelAssembler;
import fr.dademo.supervision.service.services.DatabaseReplicationPeerService;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseReplicationPeerDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseReplicationPeerStatisticsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(path = "/database-replication-peer/{id:\\d+}", produces = {
    MediaType.APPLICATION_JSON_VALUE,
    MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE,
    MediaTypes.HAL_JSON_VALUE,
})
@Tag(name = "data-backend/database")
public class DataBackendDatabaseReplicationPeerController implements ProblemHandling {

    @Autowired
    private DatabaseReplicationPeerService databaseReplicationPeerService;
    @Autowired
    private PagedResourcesAssembler<DataBackendDatabaseReplicationPeerStatisticsDto> databaseReplicationPeerStatisticsDtoPagedResourcesAssembler;

    @Operation(summary = "Get a data backend database replication peer description")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found database replication peer",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseReplicationPeerDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "database replication peer not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<DataBackendDatabaseReplicationPeerDto> findDataBackendDatabaseReplicationPeerById(@PathVariable("id") @Min(1) @Nonnull Long replicationPeerId) {

        return DataBackendDatabaseReplicationPeerRepresentationModelAssembler.INSTANCE
            .toModel(
                databaseReplicationPeerService.findDatabaseReplicationPeerById(replicationPeerId)
                    .orElseThrow(() -> new DatabaseReplicationPeerNotFoundException(replicationPeerId))
            );
    }

    @Operation(summary = "Get a data backend database replication peer statistics by id within a date range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "database replication peer statistics",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseReplicationPeerStatisticsDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "database replication peer not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<DataBackendDatabaseReplicationPeerStatisticsDto> findDataBackendDatabaseReplicationPeerStatisticsById(
        @PathVariable("id") @Min(1) @Nonnull Long replicationPeerId,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(name = "from", description = "The minimum date range", required = true) Date from,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(name = "to", description = "The maximum date range", required = true) Date to) {

        return DataBackendDatabaseReplicationPeerStatisticsRepresentationModelAssembler.of(replicationPeerId, from, to)
            .toCollectionModel(databaseReplicationPeerService.findDatabaseReplicationPeerStatisticsBetween(replicationPeerId, from, to));
    }

    @Operation(summary = "Get a data backend database schema index table statistic")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Latest database replication peer statistic",
            content = {
                @Content(mediaType = MediaTypes.HAL_JSON_VALUE, schema = @Schema(implementation = DataBackendDatabaseReplicationPeerStatisticsDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "database replication peer not found",
            content = {
                @Content(mediaType = MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE, schema = @Schema(implementation = DefaultProblem.class))
            })
    })
    @GetMapping("/latest-statistic")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<DataBackendDatabaseReplicationPeerStatisticsDto> findLatestDataBackendDatabaseReplicationPeerStatisticById(
        @PathVariable("id") @Min(1) @Nonnull Long replicationPeerId) {

        return databaseReplicationPeerService.findLatestDatabaseReplicationPeerStatistics(replicationPeerId)
            .map(
                DataBackendDatabaseReplicationPeerStatisticsRepresentationModelAssembler
                    .ofTableWithDefault(replicationPeerId)::toModel
            )
            .orElseThrow(() -> new DatabaseReplicationPeerNotFoundException(replicationPeerId));
    }
}
