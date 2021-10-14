package fr.dademo.bi.companies.jobs.stg.naf.datamodel;


import org.jooq.TableField;
import org.jooq.impl.CustomTable;
import org.jooq.impl.SQLDataType;

import javax.annotation.Nonnull;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.schema;

@SuppressWarnings({"java:S110", "java:S116", "java:S2055"})
public class NafDefinitionTable extends CustomTable<NafDefinitionRecord> {

    public static final NafDefinitionTable NAF_DEFINITION = new NafDefinitionTable();
    public static final String TABLE_NAME = "naf_codes";
    public static final String TABLE_NAMESPACE = "stg";

    public final TableField<NafDefinitionRecord, String> FIELD_NAF_CODE = createField(name("naf_code"), SQLDataType.VARCHAR(5), this);
    public final TableField<NafDefinitionRecord, String> FIELD_TITLE = createField(name("title"), SQLDataType.VARCHAR(Integer.MAX_VALUE), this);
    public final TableField<NafDefinitionRecord, String> FIELD_TITLE_65 = createField(name("title_65"), SQLDataType.VARCHAR(65), this);
    public final TableField<NafDefinitionRecord, String> FIELD_TITLE_40 = createField(name("title_40"), SQLDataType.VARCHAR(40), this);

    protected NafDefinitionTable() {
        super(name(TABLE_NAME), schema(TABLE_NAMESPACE));
    }

    @Override
    @Nonnull
    public Class<? extends NafDefinitionRecord> getRecordType() {
        return NafDefinitionRecord.class;
    }
}
