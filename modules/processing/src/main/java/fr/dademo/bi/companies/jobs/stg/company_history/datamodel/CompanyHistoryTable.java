/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_history.datamodel;

import org.jooq.TableField;
import org.jooq.impl.CustomTable;
import org.jooq.impl.SQLDataType;

import javax.annotation.Nonnull;
import java.time.LocalDate;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.schema;

/**
 * @author dademo
 */
@SuppressWarnings({"java:S110", "java:S116", "java:S2055", "java:S2160"})
public class CompanyHistoryTable extends CustomTable<CompanyHistoryRecord> {

    public static final CompanyHistoryTable COMPANY_HISTORY = new CompanyHistoryTable();
    public static final String TABLE_NAME = "company_history";
    public static final String TABLE_NAMESPACE = "stg";

    private static final long serialVersionUID = -8996406197689042034L;

    public final TableField<CompanyHistoryRecord, String> FIELD_SIREN = createField(name("siren"), SQLDataType.VARCHAR(9), this);
    public final TableField<CompanyHistoryRecord, String> FIELD_NIC = createField(name("nic"), SQLDataType.VARCHAR(5), this);
    public final TableField<CompanyHistoryRecord, String> FIELD_SIRET = createField(name("siret"), SQLDataType.VARCHAR(14), this);
    public final TableField<CompanyHistoryRecord, LocalDate> FIELD_END_DATE = createField(name("end_date"), SQLDataType.LOCALDATE, this);
    public final TableField<CompanyHistoryRecord, LocalDate> FIELD_BEGIN_DATE = createField(name("begin_date"), SQLDataType.LOCALDATE, this);
    public final TableField<CompanyHistoryRecord, String> FIELD_INSTITUTION_ADMINISTRATIVE_STATE = createField(name("institution_administrative_state"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyHistoryRecord, Boolean> FIELD_INSTITUTION_ADMINISTRATIVE_STATE_CHANGE = createField(name("institution_administrative_state_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyHistoryRecord, String> FIELD_INSTITUTION_1_NAME = createField(name("institution_1_name"), SQLDataType.VARCHAR(50), this);
    public final TableField<CompanyHistoryRecord, String> FIELD_INSTITUTION_2_NAME = createField(name("institution_2_name"), SQLDataType.VARCHAR(50), this);
    public final TableField<CompanyHistoryRecord, String> FIELD_INSTITUTION_3_NAME = createField(name("institution_3_name"), SQLDataType.VARCHAR(50), this);
    public final TableField<CompanyHistoryRecord, Boolean> FIELD_INSTITUTION_NAME_CHANGE = createField(name("institution_name_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyHistoryRecord, String> FIELD_INSTITUTION_USUAL_NAME = createField(name("institution_usual_name"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyHistoryRecord, Boolean> FIELD_INSTITUTION_USUAL_NAME_CHANGE = createField(name("institution_usual_name_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyHistoryRecord, String> FIELD_INSTITUTION_PRIMARY_ACTIVITY = createField(name("institution_primary_activity"), SQLDataType.VARCHAR(6), this);
    public final TableField<CompanyHistoryRecord, String> FIELD_INSTITUTION_PRIMARY_ACTIVITY_NOMENCLATURE = createField(name("institution_primary_activity_nomenclature"), SQLDataType.VARCHAR(8), this);
    public final TableField<CompanyHistoryRecord, Boolean> FIELD_INSTITUTION_PRIMARY_ACTIVITY_CHANGE = createField(name("institution_primary_activity_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyHistoryRecord, String> FIELD_INSTITUTION_EMPLOYER_NATURE = createField(name("institution_employer_nature"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyHistoryRecord, Boolean> FIELD_INSTITUTION_EMPLOYER_NATURE_CHANGE = createField(name("institution_employer_nature_change"), SQLDataType.BOOLEAN, this);

    protected CompanyHistoryTable() {
        super(name(TABLE_NAME), schema(TABLE_NAMESPACE));
    }

    @Override
    @Nonnull
    public Class<? extends CompanyHistoryRecord> getRecordType() {
        return CompanyHistoryRecord.class;
    }
}
