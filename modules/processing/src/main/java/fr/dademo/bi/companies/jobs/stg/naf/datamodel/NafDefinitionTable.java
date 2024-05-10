/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.naf.datamodel;

import jakarta.annotation.Nonnull;
import org.jooq.TableField;
import org.jooq.impl.CustomTable;
import org.jooq.impl.SQLDataType;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.schema;

/**
 * @author dademo
 */
@SuppressWarnings({"java:S110", "java:S2160", "java:S116"})
public class NafDefinitionTable extends CustomTable<NafDefinitionRecord> {

    public static final String TABLE_NAME = "naf_codes";
    public static final String DEFAULT_TABLE_SCHEMA = "stg";
    public static final NafDefinitionTable DEFAULT_NAF_DEFINITION_TABLE = new NafDefinitionTable(DEFAULT_TABLE_SCHEMA);
    private static final long serialVersionUID = 2335337632818852834L;
    public final TableField<NafDefinitionRecord, String> FIELD_NAF_CODE = createField(name("naf_code"), SQLDataType.VARCHAR(5), this);
    public final TableField<NafDefinitionRecord, String> FIELD_TITLE = createField(name("title"), SQLDataType.VARCHAR(Integer.MAX_VALUE), this);
    public final TableField<NafDefinitionRecord, String> FIELD_TITLE_65 = createField(name("title_65"), SQLDataType.VARCHAR(65), this);
    public final TableField<NafDefinitionRecord, String> FIELD_TITLE_40 = createField(name("title_40"), SQLDataType.VARCHAR(40), this);

    public NafDefinitionTable(@Nonnull String schema) {
        super(name(TABLE_NAME), schema(schema));
    }

    @Override
    @Nonnull
    public Class<? extends NafDefinitionRecord> getRecordType() {
        return NafDefinitionRecord.class;
    }
}
