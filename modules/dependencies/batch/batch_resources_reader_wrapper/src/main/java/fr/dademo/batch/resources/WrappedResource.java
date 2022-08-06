/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources;

import fr.dademo.batch.resources.exceptions.MissingColumnException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import java.sql.Date;

public interface WrappedResource {

    /**
     * Get a column index from its name.
     * <p>
     * Present for optimisation purposes, we may want to get the columns index
     * and store them in an object mapper to avoid reading the headers map multiple
     * times and thus save time
     * </p>
     *
     * @param columnName the column name
     * @return the column index
     * @throws MissingColumnException if the column doesn't exist
     */
    @Min(1)
    int getColumnIndexByName(String columnName) throws MissingColumnException;

    /**
     * Get the curent row number (1-based) if available.
     *
     * @return the current row number
     */
    @Nullable
    Integer getRowNum();

    @Nullable
    Boolean getBoolean(@Min(1) int columnIndex);

    @Nullable
    Boolean getBoolean(@Nonnull String columnLabel);

    @Nullable
    Byte getByte(@Min(1) int columnIndex);

    @Nullable
    Byte getByte(@Nonnull String columnLabel);

    @Nullable
    Byte[] getBytes(@Min(1) int columnIndex);

    @Nullable
    Byte[] getBytes(@Nonnull String columnLabel);

    @Nullable
    String getString(@Min(1) int columnIndex);

    @Nullable
    String getString(@Nonnull String columnLabel);

    @Nullable
    Integer getInt(@Min(1) int columnIndex);

    @Nullable
    Integer getInt(@Nonnull String columnLabel);

    @Nullable
    Long getLong(@Min(1) int columnIndex);

    @Nullable
    Long getLong(@Nonnull String columnLabel);

    @Nullable
    Double getDouble(@Min(1) int columnIndex);

    @Nullable
    Double getDouble(@Nonnull String columnLabel);

    @Nullable
    Float getFloat(@Min(1) int columnIndex);

    @Nullable
    Float getFloat(@Nonnull String columnLabel);

    @Nullable
    Date getDate(@Min(1) int columnIndex);

    @Nullable
    Date getDate(@Nonnull String columnLabel);
}
