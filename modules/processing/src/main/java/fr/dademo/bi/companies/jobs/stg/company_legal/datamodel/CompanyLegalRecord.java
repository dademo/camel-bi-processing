package fr.dademo.bi.companies.jobs.stg.company_legal.datamodel;

import org.jooq.impl.CustomRecord;

import static fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegalTable.COMPANY_LEGAL;

@SuppressWarnings("java:S110")
public class CompanyLegalRecord extends CustomRecord<CompanyLegalRecord> {

    public CompanyLegalRecord() {
        super(COMPANY_LEGAL);
    }
}

