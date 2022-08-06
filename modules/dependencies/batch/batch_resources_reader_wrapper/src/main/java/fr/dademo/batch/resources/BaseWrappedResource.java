/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources;

import fr.dademo.batch.resources.exceptions.MissingColumnException;
import lombok.AccessLevel;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;

public abstract class BaseWrappedResource implements WrappedResource {

    @Nullable
    @Setter(AccessLevel.PROTECTED)
    private Map<String, Integer> columnsIndexMapping;

    @Override
    @Min(1)
    public int getColumnIndexByName(String columnName) {

        return Optional.ofNullable(columnsIndexMapping)
            .map(
                _columnsIndexMapping -> Optional.ofNullable(_columnsIndexMapping.get(columnName))
                    .orElseThrow(() -> new MissingColumnException(columnName))
            )
            .orElseThrow(() -> new UnsupportedOperationException(
                String.format("Can't get column name with resources of type %s", getClass().getName())
            ));
    }

    @Nullable
    @Override
    public Integer getRowNum() {
        return null;
    }

    @Nullable
    @Override
    public Boolean getBoolean(int columnIndex) {
        throw missingImplementationException("getBoolean(int)");
    }

    @Nullable
    @Override
    public Boolean getBoolean(@Nonnull String columnLabel) {
        throw missingImplementationException("getBoolean(String)");
    }

    @Nullable
    @Override
    public Byte getByte(int columnIndex) {
        throw missingImplementationException("getByte(int)");
    }

    @Nullable
    @Override
    public Byte getByte(@Nonnull String columnLabel) {
        throw missingImplementationException("getByte(String)");
    }

    @Nullable
    @Override
    public Byte[] getBytes(int columnIndex) {
        throw missingImplementationException("getBytes(int)");
    }

    @Nullable
    @Override
    public Byte[] getBytes(@Nonnull String columnLabel) {
        throw missingImplementationException("getBytes(String)");
    }

    @Nullable
    @Override
    public String getString(int columnIndex) {
        throw missingImplementationException("getString(int)");
    }

    @Nullable
    @Override
    public String getString(@Nonnull String columnLabel) {
        throw missingImplementationException("getString(String)");
    }

    @Nullable
    @Override
    public Integer getInt(int columnIndex) {
        throw missingImplementationException("getInt(int)");
    }

    @Nullable
    @Override
    public Integer getInt(@Nonnull String columnLabel) {
        throw missingImplementationException("getInt(String)");
    }

    @Nullable
    @Override
    public Long getLong(int columnIndex) {
        throw missingImplementationException("getLong(int)");
    }

    @Nullable
    @Override
    public Long getLong(@Nonnull String columnLabel) {
        throw missingImplementationException("getLong(String)");
    }

    @Nullable
    @Override
    public Double getDouble(int columnIndex) {
        throw missingImplementationException("getDouble(int)");
    }

    @Nullable
    @Override
    public Double getDouble(@Nonnull String columnLabel) {
        throw missingImplementationException("getDouble(String)");
    }

    @Nullable
    @Override
    public Float getFloat(int columnIndex) {
        throw missingImplementationException("getFloat(int)");
    }

    @Nullable
    @Override
    public Float getFloat(@Nonnull String columnLabel) {
        throw missingImplementationException("getFloat(String)");
    }

    @Nullable
    @Override
    public Date getDate(int columnIndex) {
        throw missingImplementationException("getDate(int)");
    }

    @Nullable
    @Override
    public Date getDate(@Nonnull String columnLabel) {
        throw missingImplementationException("getDate(String)");
    }

    private UnsupportedOperationException missingImplementationException(String functionName) {

        return new UnsupportedOperationException(String.format(
            "Function [%s] not available with resources of type %s",
            functionName,
            getClass().getName()
        ));
    }
}
