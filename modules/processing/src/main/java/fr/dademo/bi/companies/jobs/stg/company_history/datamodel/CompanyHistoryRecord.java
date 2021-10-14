package fr.dademo.bi.companies.jobs.stg.company_history.datamodel;

import org.jooq.impl.CustomRecord;

import static fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistoryTable.COMPANY_HISTORY;

@SuppressWarnings("java:S110")
public class CompanyHistoryRecord extends CustomRecord<CompanyHistoryRecord> {

    public CompanyHistoryRecord() {
        super(COMPANY_HISTORY);
    }
}

