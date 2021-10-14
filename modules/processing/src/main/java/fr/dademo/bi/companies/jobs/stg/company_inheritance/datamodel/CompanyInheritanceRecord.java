package fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel;

import org.jooq.impl.CustomRecord;

import static fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritanceTable.COMPANY_INHERITANCE;

@SuppressWarnings("java:S110")
public class CompanyInheritanceRecord extends CustomRecord<CompanyInheritanceRecord> {

    public CompanyInheritanceRecord() {
        super(COMPANY_INHERITANCE);
    }
}

