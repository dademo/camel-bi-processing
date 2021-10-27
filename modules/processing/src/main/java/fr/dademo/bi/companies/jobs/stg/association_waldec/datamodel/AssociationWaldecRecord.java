package fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel;

import org.jooq.impl.CustomRecord;

import static fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldecTable.ASSOCIATION_WALDEC;

@SuppressWarnings("java:S110")
public class AssociationWaldecRecord extends CustomRecord<AssociationWaldecRecord> {

    public AssociationWaldecRecord() {
        super(ASSOCIATION_WALDEC);
    }
}

