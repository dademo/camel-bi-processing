/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel;

import jakarta.annotation.Nonnull;
import org.jooq.TableField;
import org.jooq.impl.CustomTable;
import org.jooq.impl.SQLDataType;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.schema;

/**
 * @author dademo
 */
@SuppressWarnings({"java:S110", "java:S2160", "java:S116"})
public class CompanyInheritanceTable extends CustomTable<CompanyInheritanceRecord> {

    public static final String TABLE_NAME = "company_inheritance";
    public static final String DEFAULT_TABLE_SCHEMA = "stg";
    public static final CompanyInheritanceTable DEFAULT_COMPANY_INHERITANCE_TABLE = new CompanyInheritanceTable(DEFAULT_TABLE_SCHEMA);

    @Serial
    private static final long serialVersionUID = -6949326022038402452L;

    public final TableField<CompanyInheritanceRecord, String> FIELD_COMPANY_BUILDING_PREDECESSOR_SIRET = createField(name("company_predecessor_siret"), SQLDataType.VARCHAR(14), this);
    public final TableField<CompanyInheritanceRecord, String> FIELD_COMPANY_BUILDING_SUCCESSOR_SIRET = createField(name("company_successor_siret"), SQLDataType.VARCHAR(14), this);
    public final TableField<CompanyInheritanceRecord, LocalDate> FIELD_COMPANY_SUCCESSION_DATE = createField(name("company_succession_date"), SQLDataType.LOCALDATE, this);
    public final TableField<CompanyInheritanceRecord, Boolean> FIELD_COMPANY_HEADQUARTER_CHANGE = createField(name("company_headquarter_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyInheritanceRecord, Boolean> FIELD_COMPANY_ECONOMICAL_CONTINUITY = createField(name("company_economical_continuity"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyInheritanceRecord, LocalDateTime> FIELD_COMPANY_PROCESSING_DATE = createField(name("company_processing_timestamp"), SQLDataType.LOCALDATETIME, this);

    public CompanyInheritanceTable(String schema) {
        super(name(TABLE_NAME), schema(schema));
    }

    @Override
    @Nonnull
    public Class<? extends CompanyInheritanceRecord> getRecordType() {
        return CompanyInheritanceRecord.class;
    }
}
