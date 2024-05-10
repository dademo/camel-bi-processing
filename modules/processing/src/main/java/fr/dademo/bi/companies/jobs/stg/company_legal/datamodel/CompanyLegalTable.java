/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal.datamodel;

import jakarta.annotation.Nonnull;
import org.jooq.TableField;
import org.jooq.impl.CustomTable;
import org.jooq.impl.SQLDataType;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.schema;

/**
 * @author dademo
 */
@SuppressWarnings({"java:S110", "java:S2160", "java:S116"})
public class CompanyLegalTable extends CustomTable<CompanyLegalRecord> {

    public static final String TABLE_NAME = "company_legal_unit";
    public static final String DEFAULT_TABLE_SCHEMA = "stg";
    public static final CompanyLegalTable DEFAULT_COMPANY_LEGAL_TABLE = new CompanyLegalTable(DEFAULT_TABLE_SCHEMA);
    private static final long serialVersionUID = -6021744332544359613L;
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_SIREN = createField(name("siren"), SQLDataType.VARCHAR(9), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_DIFFUSION_STATUS = createField(name("diffusion_status"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyLegalRecord, Boolean> FIELD_COMPANY_LEGAL_UNIT_IS_PURGED = createField(name("is_purged"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyLegalRecord, LocalDate> FIELD_COMPANY_LEGAL_UNIT_CREATION_DATE = createField(name("creation_date"), SQLDataType.LOCALDATE, this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_ACRONYM = createField(name("acronym"), SQLDataType.VARCHAR(20), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_SEX = createField(name("sex"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_1 = createField(name("first_name_1"), SQLDataType.VARCHAR(20), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_2 = createField(name("first_name_2"), SQLDataType.VARCHAR(20), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_3 = createField(name("first_name_3"), SQLDataType.VARCHAR(20), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_4 = createField(name("first_name_4"), SQLDataType.VARCHAR(20), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_USUAL_FIRST_NAME = createField(name("usual_first_name"), SQLDataType.VARCHAR(20), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_PSEUDONYM = createField(name("pseudonym"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_ASSOCIATION_IDENTIFIER = createField(name("association_identifier"), SQLDataType.VARCHAR(10), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_RANGE = createField(name("staff_number_range"), SQLDataType.VARCHAR(2), this);
    public final TableField<CompanyLegalRecord, Integer> FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_YEAR = createField(name("staff_number_year"), SQLDataType.INTEGER, this);
    public final TableField<CompanyLegalRecord, LocalDateTime> FIELD_COMPANY_LEGAL_UNIT_LAST_PROCESSING = createField(name("last_processing"), SQLDataType.LOCALDATETIME, this);
    public final TableField<CompanyLegalRecord, Integer> FIELD_COMPANY_LEGAL_UNIT_PERIODS_COUNT = createField(name("periods_count"), SQLDataType.INTEGER, this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY = createField(name("company_category"), SQLDataType.VARCHAR(3), this);
    public final TableField<CompanyLegalRecord, Integer> FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY_YEAR = createField(name("company_category_year"), SQLDataType.INTEGER, this);
    public final TableField<CompanyLegalRecord, LocalDate> FIELD_COMPANY_LEGAL_BEGIN_DATE = createField(name("begin_date"), SQLDataType.LOCALDATE, this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_ADMINISTRATIVE_STATE = createField(name("administrative_state"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_NAME = createField(name("name"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_USUAL_NAME = createField(name("usual_name"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_DENOMINATION = createField(name("denomination"), SQLDataType.VARCHAR(120), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_1 = createField(name("usual_denomination_1"), SQLDataType.VARCHAR(70), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_2 = createField(name("usual_denomination_2"), SQLDataType.VARCHAR(70), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_3 = createField(name("usual_denomination_3"), SQLDataType.VARCHAR(70), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_LEGAL_CATEGORY = createField(name("legal_category"), SQLDataType.VARCHAR(4), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY = createField(name("principal_activity"), SQLDataType.VARCHAR(6), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE = createField(name("principal_activity_nomenclature"), SQLDataType.VARCHAR(8), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_HEADQUARTERS_NIC = createField(name("headquarters_nic"), SQLDataType.VARCHAR(5), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY = createField(name("is_social_and_solidarity_economy"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyLegalRecord, String> FIELD_COMPANY_LEGAL_UNIT_IS_EMPLOYER = createField(name("is_employer"), SQLDataType.VARCHAR(1), this);

    public CompanyLegalTable(@Nonnull String schema) {
        super(name(TABLE_NAME), schema(schema));
    }

    @Override
    @Nonnull
    public Class<? extends CompanyLegalRecord> getRecordType() {
        return CompanyLegalRecord.class;
    }
}
