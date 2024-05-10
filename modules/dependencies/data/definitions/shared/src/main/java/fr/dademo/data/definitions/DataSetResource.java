/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
public interface DataSetResource {

    @Nullable
    String getId();

    @Nullable
    String getExternalId();

    @Nonnull
    String getName();

    @Nullable
    String getParentId();

    @Nullable
    String getSource();

    @Nullable
    String getSourceSub();

    @Nullable
    String getDescription();

    @Nonnull
    String getTitle();

    @Nonnull
    String getUrl();

    @Nullable
    DataSetResourceChecksum getChecksum();

    @Nonnull
    LocalDateTime getCreatedAt();

    @Nullable
    LocalDateTime getPublished();

    @Nullable
    LocalDateTime getLastModified();

    @Nullable
    Integer getFileSize();

    @Nonnull
    String getFileType();

    @Nonnull
    String getFormat();

    @Nullable
    String getMime();
}
