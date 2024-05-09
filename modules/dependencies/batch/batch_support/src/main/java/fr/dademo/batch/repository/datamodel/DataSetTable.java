/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.repository.datamodel;

import lombok.AllArgsConstructor;
import org.jooq.*;
import org.jooq.impl.CustomTable;
import org.jooq.impl.SQLDataType;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.jooq.impl.DSL.*;

/**
 * @author dademo
 */
@SuppressWarnings({"java:S110", "java:S2160", "java:S116"})
public class DataSetTable extends CustomTable<DataSetRecord> {

    public static final String TABLE_NAME = "dataset";
    public static final String DEFAULT_TABLE_SCHEMA = "public";
    public static final DataSetTable DEFAULT_DATA_SET_TABLE = new DataSetTable(DEFAULT_TABLE_SCHEMA);
    private static final long serialVersionUID = -5796048724824317298L;
    public final TableField<DataSetRecord, Long> FIELD_DATA_SET_ID = createField(name("id"), SQLDataType.BIGINT, this);
    public final TableField<DataSetRecord, String> FIELD_DATA_SET_NAME = createField(name("name"), SQLDataType.VARCHAR(255), this);
    public final TableField<DataSetRecord, Long> FIELD_DATA_SET_PARENT = createField(name("parent"), SQLDataType.BIGINT, this);
    public final TableField<DataSetRecord, String> FIELD_DATA_SET_SOURCE = createField(name("source"), SQLDataType.VARCHAR(4), this);
    public final TableField<DataSetRecord, String> FIELD_DATA_SET_SOURCE_SUB = createField(name("source_sub"), SQLDataType.VARCHAR(255), this);
    public final TableField<DataSetRecord, String> FIELD_DATA_SET_STATE = createField(name("state"), SQLDataType.VARCHAR(255), this);
    public final TableField<DataSetRecord, LocalDateTime> FIELD_DATA_SET_TIMESTAMP = createField(name("timestamp"), SQLDataType.LOCALDATETIME, this);

    public DataSetTable() {
        super(name(TABLE_NAME));
    }

    public DataSetTable(String schema) {
        super(name(TABLE_NAME), schema(schema));
    }

    @Override
    public UniqueKey<DataSetRecord> getPrimaryKey() {
        return new DataSetTablePrimaryKey(this);
    }

    @Override
    @Nonnull
    public Class<? extends DataSetRecord> getRecordType() {
        return DataSetRecord.class;
    }

    @SuppressWarnings("java:S1452")
    public List<TableField<DataSetRecord, ?>> getFieldsListWithoutId() {

        return Arrays.asList(
            FIELD_DATA_SET_NAME,
            FIELD_DATA_SET_PARENT,
            FIELD_DATA_SET_SOURCE,
            FIELD_DATA_SET_SOURCE_SUB,
            FIELD_DATA_SET_STATE,
            FIELD_DATA_SET_TIMESTAMP
        );
    }

    @SuppressWarnings("java:S1452")
    public List<TableField<DataSetRecord, ?>> getFieldsList() {

        return Stream.concat(
            Stream.of(FIELD_DATA_SET_ID),
            getFieldsListWithoutId().stream()
        ).toList();
    }

    @AllArgsConstructor
    private static class DataSetTablePrimaryKey implements UniqueKey<DataSetRecord> {

        private static final long serialVersionUID = -3461312454111219047L;
        private static final String NAME = "id";

        private final DataSetTable tableRef;

        @Override
        public @Nonnull List<ForeignKey<?, DataSetRecord>> getReferences() {
            return Collections.emptyList();
        }

        @Override
        public boolean isPrimary() {
            return true;
        }

        @Override
        public @Nonnull Table<DataSetRecord> getTable() {
            return tableRef;
        }

        @Override
        public @Nonnull List<TableField<DataSetRecord, ?>> getFields() {
            return Collections.singletonList(tableRef.FIELD_DATA_SET_ID);
        }

        @SuppressWarnings("unchecked")
        @Override
        public @Nonnull TableField<DataSetRecord, ?>[] getFieldsArray() {
            return getFields().toArray(TableField[]::new);
        }

        @SuppressWarnings("all")    // Won't fix as this part is not required
        @Override
        public @Nonnull Constraint constraint() {
            return null;
        }

        @Override
        public boolean enforced() {
            return false;
        }

        @Override
        public boolean nullable() {
            return false;
        }

        @Override
        public @Nonnull String getName() {
            return NAME;
        }

        @Override
        public @Nonnull Name getQualifiedName() {
            return name(NAME);
        }

        @Override
        public @Nonnull Name getUnqualifiedName() {
            return name(NAME);
        }

        @Override
        public @Nonnull String getComment() {
            return DataSetTable.class.getName();
        }

        @Override
        public @Nonnull Comment getCommentPart() {
            return comment(DataSetTable.class.getName());
        }

        @Override
        public @Nonnull Name $name() {
            return name(NAME);
        }
    }
}
