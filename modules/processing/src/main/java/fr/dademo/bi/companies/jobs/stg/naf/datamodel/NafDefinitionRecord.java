package fr.dademo.bi.companies.jobs.stg.naf.datamodel;


import org.jooq.impl.CustomRecord;

import static fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionTable.NAF_DEFINITION;

@SuppressWarnings("java:S110")
public class NafDefinitionRecord extends CustomRecord<NafDefinitionRecord> {

    public NafDefinitionRecord() {
        super(NAF_DEFINITION);
    }
}
