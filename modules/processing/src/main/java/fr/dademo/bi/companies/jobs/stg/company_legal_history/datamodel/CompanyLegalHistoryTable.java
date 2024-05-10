/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel;

import jakarta.annotation.Nonnull;
import org.jooq.TableField;
import org.jooq.impl.CustomTable;
import org.jooq.impl.SQLDataType;

import java.time.LocalDate;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.schema;

/**
 * @author dademo
 */
@SuppressWarnings({"java:S110", "java:S2160", "java:S116"})
public class CompanyLegalHistoryTable extends CustomTable<CompanyLegalHistoryRecord> {

    public static final String TABLE_NAME = "company_legal_unit_history";
    public static final String DEFAULT_TABLE_SCHEMA = "stg";
    public static final CompanyLegalHistoryTable DEFAULT_COMPANY_LEGAL_HISTORY_TABLE = new CompanyLegalHistoryTable(DEFAULT_TABLE_SCHEMA);
    private static final long serialVersionUID = 4298493080112132399L;
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_SIREN = createField(name("siren"), SQLDataType.VARCHAR(9), this);
    public final TableField<CompanyLegalHistoryRecord, LocalDate> FIELD_COMPANY_LEGAL_HISTORY_END_DATE = createField(name("end_date"), SQLDataType.LOCALDATE, this);
    public final TableField<CompanyLegalHistoryRecord, LocalDate> FIELD_COMPANY_LEGAL_HISTORY_BEGIN_DATE = createField(name("begin_date"), SQLDataType.LOCALDATE, this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE = createField(name("legal_unit_administrative_state"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyLegalHistoryRecord, Boolean> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE_CHANGE = createField(name("legal_unit_administrative_state_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME = createField(name("legal_unit_legal_unit_name"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyLegalHistoryRecord, Boolean> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME_CHANGE = createField(name("legal_unit_legal_unit_name_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME = createField(name("legal_unit_usual_name"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyLegalHistoryRecord, Boolean> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME_CHANGE = createField(name("legal_unit_usual_name_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION = createField(name("legal_unit_denomination"), SQLDataType.VARCHAR(120), this);
    public final TableField<CompanyLegalHistoryRecord, Boolean> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION_CHANGE = createField(name("legal_unit_denomination_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_1 = createField(name("legal_unit_usual_denomination_1"), SQLDataType.VARCHAR(70), this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_2 = createField(name("legal_unit_usual_denomination_2"), SQLDataType.VARCHAR(70), this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_3 = createField(name("legal_unit_usual_denomination_3"), SQLDataType.VARCHAR(70), this);
    public final TableField<CompanyLegalHistoryRecord, Boolean> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_CHANGE = createField(name("legal_unit_usual_denomination_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY = createField(name("legal_unit_legal_category"), SQLDataType.VARCHAR(16), this);
    public final TableField<CompanyLegalHistoryRecord, Boolean> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY_CHANGE = createField(name("legal_unit_legal_category_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY = createField(name("legal_unit_principal_activity"), SQLDataType.VARCHAR(6), this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE = createField(name("legal_unit_principal_activity_nomenclature"), SQLDataType.VARCHAR(8), this);
    public final TableField<CompanyLegalHistoryRecord, Boolean> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE_CHANGE = createField(name("legal_unit_principal_activity_nomenclature_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC = createField(name("legal_unit_headquarter_nic"), SQLDataType.VARCHAR(5), this);
    public final TableField<CompanyLegalHistoryRecord, Boolean> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC_CHANGE = createField(name("legal_unit_headquarter_nic_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY = createField(name("legal_unit_is_social_and_solidarity_economy"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyLegalHistoryRecord, Boolean> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY_CHANGE = createField(name("legal_unit_is_social_and_solidarity_economy_change"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyLegalHistoryRecord, String> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER = createField(name("legal_unit_is_employer"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyLegalHistoryRecord, Boolean> FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER_CHANGE = createField(name("legal_unit_is_employer_change"), SQLDataType.BOOLEAN, this);

    public CompanyLegalHistoryTable(@Nonnull String schema) {
        super(name(TABLE_NAME), schema(schema));
    }

    @Override
    @Nonnull
    public Class<? extends CompanyLegalHistoryRecord> getRecordType() {
        return CompanyLegalHistoryRecord.class;
    }
}
