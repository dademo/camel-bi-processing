package fr.dademo.bi.companies.jobs.stg.association.datamodel;

import org.jooq.impl.CustomRecord;

import static fr.dademo.bi.companies.jobs.stg.association.datamodel.AssociationTable.ASSOCIATION;

@SuppressWarnings("java:S110")
public class AssociationRecord extends CustomRecord<AssociationRecord> {

    public AssociationRecord() {
        super(ASSOCIATION);
    }
}

