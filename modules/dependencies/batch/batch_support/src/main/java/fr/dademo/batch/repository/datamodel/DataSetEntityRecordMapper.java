/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.repository.datamodel;

import fr.dademo.batch.repository.datamodel.exceptions.DataSetEntityRecordMapperInvalidStateValue;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.jooq.RecordMapper;

import java.util.Optional;

@RequiredArgsConstructor
public class DataSetEntityRecordMapper implements RecordMapper<DataSetRecord, DataSetEntity> {

    @Nonnull
    private final DataSetTable dataSetTable;

    @Override
    public @Nullable DataSetEntity map(DataSetRecord dataSetRecord) {

        return DataSetEntity.builder()
            .id(longToString(dataSetRecord.get(dataSetTable.FIELD_DATA_SET_ID)))
            .name(dataSetRecord.get(dataSetTable.FIELD_DATA_SET_NAME))
            .parent(longToString(dataSetRecord.get(dataSetTable.FIELD_DATA_SET_PARENT)))
            .source(dataSetRecord.get(dataSetTable.FIELD_DATA_SET_SOURCE))
            .sourceSub(dataSetRecord.get(dataSetTable.FIELD_DATA_SET_SOURCE_SUB))
            .state(getStateFromRecord(dataSetRecord))
            .timestamp(dataSetRecord.get(dataSetTable.FIELD_DATA_SET_TIMESTAMP))
            .build();
    }

    private DataSetEntity.DataSetState getStateFromRecord(DataSetRecord dataSetRecord) {

        try {
            return DataSetEntity.DataSetState.valueOf(dataSetRecord.get(dataSetTable.FIELD_DATA_SET_STATE));
        } catch (IllegalArgumentException ex) {
            throw new DataSetEntityRecordMapperInvalidStateValue(dataSetRecord.get(dataSetTable.FIELD_DATA_SET_STATE));
        }
    }

    @SuppressWarnings("java:S1612")
    @Nullable
    private String longToString(@Nullable Long value) {
        return Optional.ofNullable(value)
            .map(v -> Long.toString(v))
            .orElse(null);
    }
}
