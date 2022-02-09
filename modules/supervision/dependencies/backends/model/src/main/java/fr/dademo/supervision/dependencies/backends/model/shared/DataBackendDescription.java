/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Description of a supervised data backend.
 *
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface DataBackendDescription extends DataBackendDescriptionBase {

    /**
     * Get the backend product name.
     *
     * @return the backend product name
     */
    @Nonnull
    @Size(min = 1, max = 255)
    String getBackendProductName();

    /**
     * Get the backend product version.
     *
     * @return the backend product version
     */
    @Nonnull
    @Size(min = 1, max = 50)
    String getBackendProductVersion();

    /**
     * Get the usual name of the data backend.
     *
     * @return the usual name of the data backend
     */
    @Nullable
    @Size(min = 1, max = 255)
    String getBackendName();

    /**
     * Get the data backend primary URL.
     *
     * @return the data backend primary URL
     */
    @Nullable
    @Size(min = 1, max = 1000)
    String getPrimaryUrl();

    /**
     * Get the URL of all nodes across the cluster.
     *
     * @return the URL of all nodes across the cluster
     */
    @Size(min = 1)
    @Nonnull
    Iterable<String> getNodeUrls();

    /**
     * Get the data backend kind.
     *
     * @return the data backend kind
     */
    @Nonnull
    DataBackendKind getBackendKind();


    /**
     * Get the data backend state.
     *
     * @return the data backend state
     */
    @Nonnull
    DataBackendState getBackendState();

    /**
     * Get a text message describing the backend state (in english).
     * <p>
     * Must be null if no message is available.
     *
     * @return a text message describing the backend state
     */
    @Nullable
    @Size(min = 1)
    String getBackendStateExplanation();

    /**
     * Get the number of nodes composing the data backend cluster.
     * <p>
     * If not clustered, will return <b>1</b> as it is considered as a cluster with a single node.
     * <p>
     * May return <b>null</b> if the cluster is unreachable.
     *
     * @return the number of nodes composing the data backend cluster
     */
    @Nullable
    @Min(1)
    Integer getClusterSize();

    /**
     * Get the number of primary nodes/controllers.
     *
     * @return the number of primary nodes/controllers
     */
    @Nullable
    @Min(1)
    Integer getPrimaryCount();

    /**
     * Get the number of replicas.
     *
     * @return the number of replicas
     */
    @Nullable
    @Min(0)
    Integer getReplicaCount();

    /**
     * Get the start time of the data backend.
     *
     * @return the start time of the data backend
     */
    @Nullable
    Date getStartTime();

    /**
     * Get the amount of memory used by the data backend.
     *
     * @return the amount of memory used by the data backend
     */
    @Nullable
    @Min(0)
    Long getMemoryUsageBytes();

    /**
     * Get the amount of CPU used by the data backend.
     *
     * @return the amount of CPU used by the data backend
     */
    @Nullable
    @Min(0)
    Long getCpuUsageMilliCPU();
}
