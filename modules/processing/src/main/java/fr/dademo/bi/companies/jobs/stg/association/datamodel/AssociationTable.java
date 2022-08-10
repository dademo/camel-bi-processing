/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association.datamodel;

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
public class AssociationTable extends CustomTable<AssociationRecord> {

    // TODO handle schemas declaration
    public static final AssociationTable ASSOCIATION = new AssociationTable();
    public static final String TABLE_NAME = "association";
    public static final String TABLE_NAMESPACE = "stg";

    private static final long serialVersionUID = 2388048002240918898L;

    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_ID = createField(name("id"), SQLDataType.VARCHAR(14), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_ID_EX = createField(name("id_ex"), SQLDataType.VARCHAR(10), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_SIRET = createField(name("siret"), SQLDataType.VARCHAR(14), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_GESTION = createField(name("gestion"), SQLDataType.VARCHAR(4), this);
    public final TableField<AssociationRecord, LocalDate> FIELD_ASSOCIATION_CREATION_DATE = createField(name("creation_date"), SQLDataType.LOCALDATE, this);
    public final TableField<AssociationRecord, LocalDate> FIELD_ASSOCIATION_PUBLICATION_DATE = createField(name("publication_date"), SQLDataType.LOCALDATE, this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_NATURE = createField(name("nature"), SQLDataType.VARCHAR(1), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_GROUPEMENT = createField(name("groupement"), SQLDataType.VARCHAR(1), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_TITLE = createField(name("title"), SQLDataType.VARCHAR(250), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_OBJECT = createField(name("object"), SQLDataType.VARCHAR(Integer.MAX_VALUE), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_SOCIAL_OBJECT_1 = createField(name("social_object_1"), SQLDataType.VARCHAR(6), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_SOCIAL_OBJECT_2 = createField(name("social_object_2"), SQLDataType.VARCHAR(6), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_ADDRESS_1 = createField(name("address_1"), SQLDataType.VARCHAR(60), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_ADDRESS_2 = createField(name("address_2"), SQLDataType.VARCHAR(60), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_ADDRESS_3 = createField(name("address_3"), SQLDataType.VARCHAR(60), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_ADDRESS_POSTAL_CODE = createField(name("address_postal_code"), SQLDataType.VARCHAR(5), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_ADDRESS_INSEE_CODE = createField(name("address_insee_code"), SQLDataType.VARCHAR(5), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_ADDRESS_CITY_LIBELLE = createField(name("address_city_libelle"), SQLDataType.VARCHAR(45), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_LEADER_CIVILITY = createField(name("leader_civility"), SQLDataType.VARCHAR(2), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_WEBSITE = createField(name("website"), SQLDataType.VARCHAR(64), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_OBSERVATION = createField(name("observation"), SQLDataType.VARCHAR(128), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_POSITION = createField(name("position"), SQLDataType.VARCHAR(1), this);
    public final TableField<AssociationRecord, String> FIELD_ASSOCIATION_RUP_CODE = createField(name("rup_code"), SQLDataType.VARCHAR(11), this);
    public final TableField<AssociationRecord, LocalDate> FIELD_ASSOCIATION_LAST_UPDATED = createField(name("last_updated"), SQLDataType.LOCALDATE, this);

    protected AssociationTable() {
        super(name(TABLE_NAME), schema(TABLE_NAMESPACE));
    }

    @Override
    @Nonnull
    public Class<? extends AssociationRecord> getRecordType() {
        return AssociationRecord.class;
    }
}
