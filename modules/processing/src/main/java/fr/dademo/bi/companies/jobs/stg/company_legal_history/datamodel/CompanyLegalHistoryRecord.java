package fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel;

import org.jooq.impl.CustomRecord;

import static fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistoryTable.COMPANY_LEGAL_HISTORY;

@SuppressWarnings("java:S110")
public class CompanyLegalHistoryRecord extends CustomRecord<CompanyLegalHistoryRecord> {

    public CompanyLegalHistoryRecord() {
        super(COMPANY_LEGAL_HISTORY);
    }
}

