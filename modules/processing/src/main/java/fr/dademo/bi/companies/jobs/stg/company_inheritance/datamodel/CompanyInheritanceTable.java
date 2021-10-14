package fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel;

import org.jooq.TableField;
import org.jooq.impl.CustomTable;
import org.jooq.impl.SQLDataType;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.schema;

@SuppressWarnings({"java:S110", "java:S116", "java:S2055"})
public class CompanyInheritanceTable extends CustomTable<CompanyInheritanceRecord> {

    public static final CompanyInheritanceTable COMPANY_INHERITANCE = new CompanyInheritanceTable();
    public static final String TABLE_NAME = "company_inheritance";
    public static final String TABLE_NAMESPACE = "stg";

    public final TableField<CompanyInheritanceRecord, String> FIELD_COMPANY_PREDECESSOR_SIREN = createField(name("company_predecessor_siren"), SQLDataType.VARCHAR(14), this);
    public final TableField<CompanyInheritanceRecord, String> FIELD_COMPANY_SUCCESSOR_SIREN = createField(name("company_successor_siren"), SQLDataType.VARCHAR(14), this);
    public final TableField<CompanyInheritanceRecord, LocalDate> FIELD_COMPANY_SUCCESSION_DATE = createField(name("company_succession_date"), SQLDataType.LOCALDATE, this);
    public final TableField<CompanyInheritanceRecord, Boolean> FIELD_COMPANY_HEADQUARTER_CHANGE = createField(name("company_headquarter_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyInheritanceRecord, Boolean> FIELD_COMPANY_ECONOMICAL_CONTINUITY = createField(name("company_economical_continuity"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyInheritanceRecord, LocalDateTime> FIELD_COMPANY_PROCESSING_DATE = createField(name("company_processing_timestamp"), SQLDataType.LOCALDATETIME, this);

    protected CompanyInheritanceTable() {
        super(name(TABLE_NAME), schema(TABLE_NAMESPACE));
    }

    @Override
    @Nonnull
    public Class<? extends CompanyInheritanceRecord> getRecordType() {
        return CompanyInheritanceRecord.class;
    }
}
