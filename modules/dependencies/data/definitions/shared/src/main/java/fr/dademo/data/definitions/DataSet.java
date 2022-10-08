/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
public interface DataSet {

    @Nullable
    String getId();

    @Nonnull
    String getTitle();

    @Nonnull
    String getUri();

    @Nonnull
    String getDescription();

    @Nonnull
    LocalDateTime getCreatedAt();

    @Nullable
    LocalDateTime getDeleted();

    @Nullable
    LocalDateTime getArchived();

    @Nonnull
    LocalDateTime getLastModified();

    @Nonnull
    LocalDateTime getLastUpdate();

    @Nullable
    List<String> getTags();

    @SuppressWarnings("java:S1452")
    @Nonnull
    List<? extends DataSetResource> getResources();
}
