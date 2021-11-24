/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel;

import org.jooq.impl.CustomRecord;

import static fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistoryTable.COMPANY_LEGAL_HISTORY;

/**
 * @author dademo
 */
@SuppressWarnings("java:S110")
public class CompanyLegalHistoryRecord extends CustomRecord<CompanyLegalHistoryRecord> {

    private static final long serialVersionUID = 2863514533660705481L;

    public CompanyLegalHistoryRecord() {
        super(COMPANY_LEGAL_HISTORY);
    }
}

