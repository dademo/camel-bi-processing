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

package fr.dademo.supervision.backends.model.shared;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.net.URL;

/**
 * Description of a supervised data backend.
 *
 * @author dademo
 */
public interface DataBackendDescription {

    /**
     * Get the backend product name.
     *
     * @return the backend product name
     */
    @Nonnull
    String getBackendProductName();

    /**
     * Get the backend product version.
     *
     * @return the backend product version
     */
    @Nonnull
    String getBackendProductVersion();

    /**
     * Get the usual name of the data backend.
     *
     * @return the usual name of the data backend
     */
    @Nonnull
    String getBackendName();

    /**
     * Get the data backend primary URL.
     *
     * @return the data backend primary URL
     */
    @Nonnull
    URL getPrimaryUrl();

    /**
     * Get the URL of all nodes across the cluster.
     *
     * @return the URL of all nodes across the cluster
     */
    @Size(min = 1)
    @Nonnull
    Iterable<URL> getNodeUrls();

    /**
     * Get the data backend kind.
     *
     * @return the data backend kind
     */
    @Nonnull
    DataBackendKind getKind();


    /**
     * Get the data backend state.
     *
     * @return the data backend state
     */
    @Nonnull
    DataBackendState getDataBackendState();

    /**
     * Get a text message describing the backend state (in english).
     * <p>
     * Must be null if no message is available.
     *
     * @return a text message describing the backend state
     */
    @Nullable
    @Size(min = 1)
    String getDataBackendStateExplanation();

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
     * The data size.
     *
     * @return the data size
     */
    @Nullable
    @Min(0)
    Long getSizeBytes();

    /**
     * Get the effective data size (space really used on the underlying file system).
     *
     * @return the effective data size
     */
    @Nullable
    @Min(0)
    Long getEffectiveSizeBytes();

    /**
     * Get the available size for the data backend.
     *
     * @return the available size for the data backend
     */
    @Nullable
    @Min(0)
    Long getAvailableSizeBytes();
}
