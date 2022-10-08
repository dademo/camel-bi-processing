/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company.datamodel;

import org.jooq.TableField;
import org.jooq.impl.CustomTable;
import org.jooq.impl.SQLDataType;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.schema;

/**
 * @author dademo
 */
@SuppressWarnings({"java:S110", "java:S2160", "java:S116"})
public class CompanyTable extends CustomTable<CompanyRecord> {

    public static final String TABLE_NAME = "company";
    public static final String DEFAULT_TABLE_SCHEMA = "stg";
    public static final CompanyTable DEFAULT_COMPANY_TABLE = new CompanyTable(DEFAULT_TABLE_SCHEMA);
    private static final long serialVersionUID = -7046999060163075757L;
    public final TableField<CompanyRecord, String> FIELD_SIREN = createField(name("siren"), SQLDataType.VARCHAR(9), this);
    public final TableField<CompanyRecord, String> FIELD_NIC = createField(name("nic"), SQLDataType.VARCHAR(5), this);
    public final TableField<CompanyRecord, String> FIELD_SIRET = createField(name("siret"), SQLDataType.VARCHAR(14), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_DIFFUSION_STATUT = createField(name("company_diffusion_statut"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyRecord, LocalDate> FIELD_COMPANY_CREATION_DATE = createField(name("company_creation_date"), SQLDataType.LOCALDATE, this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_STAFF_NUMBER_RANGE = createField(name("company_staff_number_range"), SQLDataType.VARCHAR(2), this);
    public final TableField<CompanyRecord, Integer> FIELD_COMPANY_STAFF_NUMBER_YEAR = createField(name("company_staff_number_year"), SQLDataType.INTEGER, this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_PRINCIPAL_REGISTERED_ACTIVITY = createField(name("company_principal_registered_activity"), SQLDataType.VARCHAR(6), this);
    public final TableField<CompanyRecord, LocalDateTime> FIELD_COMPANY_LAST_PROCESSING_DATE = createField(name("company_last_processing_date"), SQLDataType.LOCALDATETIME, this);
    public final TableField<CompanyRecord, Boolean> FIELD_COMPANY_IS_HEADQUARTERS = createField(name("company_is_headquarters"), SQLDataType.BOOLEAN, this);
    public final TableField<CompanyRecord, Integer> FIELD_COMPANY_PERIOD_COUNT = createField(name("company_period_count"), SQLDataType.INTEGER, this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_COMPLEMENT = createField(name("company_address_complement"), SQLDataType.VARCHAR(38), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_STREET_NUMBER = createField(name("company_address_street_number"), SQLDataType.VARCHAR(4), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION = createField(name("company_address_street_number_repetition"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_STREET_TYPE = createField(name("company_address_street_type"), SQLDataType.VARCHAR(4), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_STREET_NAME = createField(name("company_address_street_name"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_POSTAL_CODE = createField(name("company_address_postal_code"), SQLDataType.VARCHAR(5), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_CITY = createField(name("company_address_city"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_FOREIGN_ADDRESS_CITY = createField(name("company_foreign_address_city"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION = createField(name("company_address_special_distribution"), SQLDataType.VARCHAR(26), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_CITY_CODE = createField(name("company_address_city_code"), SQLDataType.VARCHAR(5), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_CEDEX_CODE = createField(name("company_address_cedex_code"), SQLDataType.VARCHAR(9), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_CEDEX_NAME = createField(name("company_address_cedex_name"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE = createField(name("company_foreign_address_country_code"), SQLDataType.VARCHAR(5), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME = createField(name("company_foreign_address_country_name"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_COMPLEMENT_2 = createField(name("company_address_complement_2"), SQLDataType.VARCHAR(38), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_STREET_NUMBER_2 = createField(name("company_address_street_number_2"), SQLDataType.VARCHAR(4), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION_2 = createField(name("company_address_street_number_repetition_2"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_STREET_TYPE_2 = createField(name("company_address_street_type_2"), SQLDataType.VARCHAR(4), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_STREET_NAME_2 = createField(name("company_address_street_name_2"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_POSTAL_CODE_2 = createField(name("company_address_postal_code_2"), SQLDataType.VARCHAR(5), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_CITY_2 = createField(name("company_address_city_2"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_FOREIGN_ADDRESS_CITY_2 = createField(name("company_foreign_address_city_2"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION_2 = createField(name("company_address_special_distribution_2"), SQLDataType.VARCHAR(26), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_CITY_CODE_2 = createField(name("company_address_city_code_2"), SQLDataType.VARCHAR(5), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_CEDEX_CODE_2 = createField(name("company_address_cedex_code_2"), SQLDataType.VARCHAR(9), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADDRESS_CEDEX_NAME_2 = createField(name("company_address_cedex_name_2"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE_2 = createField(name("company_foreign_address_country_code_2"), SQLDataType.VARCHAR(5), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME_2 = createField(name("company_foreign_address_country_name_2"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyRecord, LocalDate> FIELD_BEGIN_DATE = createField(name("begin_date"), SQLDataType.LOCALDATE, this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ADMINISTATIVE_STATE = createField(name("company_administative_state"), SQLDataType.VARCHAR(1), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_NAME_1 = createField(name("company_name_1"), SQLDataType.VARCHAR(50), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_NAME_2 = createField(name("company_name_2"), SQLDataType.VARCHAR(50), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_NAME_3 = createField(name("company_name_3"), SQLDataType.VARCHAR(50), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_USUAL_NAME = createField(name("company_usual_name"), SQLDataType.VARCHAR(100), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_ACTIVITY = createField(name("company_activity"), SQLDataType.VARCHAR(6), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_PRINCIPAL_ACTIVITY_NAME = createField(name("company_principal_activity_name"), SQLDataType.VARCHAR(8), this);
    public final TableField<CompanyRecord, String> FIELD_COMPANY_IS_EMPLOYER = createField(name("company_is_employer"), SQLDataType.VARCHAR(1), this);

    public CompanyTable(@Nonnull String schema) {
        super(name(TABLE_NAME), schema(schema));
    }

    @Override
    @Nonnull
    public Class<? extends CompanyRecord> getRecordType() {
        return CompanyRecord.class;
    }
}
