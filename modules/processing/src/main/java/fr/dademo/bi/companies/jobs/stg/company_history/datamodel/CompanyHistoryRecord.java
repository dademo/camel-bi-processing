/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_history.datamodel;

import org.jooq.impl.CustomRecord;

import static fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistoryTable.COMPANY_HISTORY;

/**
 * @author dademo
 */
@SuppressWarnings("java:S110")
public class CompanyHistoryRecord extends CustomRecord<CompanyHistoryRecord> {

    private static final long serialVersionUID = 5054020186151448725L;

    public CompanyHistoryRecord() {
        super(COMPANY_HISTORY);
    }
}

