package fr.dademo.bi.companies.jobs.stg.company.datamodel;

import org.jooq.impl.CustomRecord;

import static fr.dademo.bi.companies.jobs.stg.company.datamodel.CompanyTable.COMPANY;

@SuppressWarnings("java:S110")
public class CompanyRecord extends CustomRecord<CompanyRecord> {

    public CompanyRecord() {
        super(COMPANY);
    }
}

