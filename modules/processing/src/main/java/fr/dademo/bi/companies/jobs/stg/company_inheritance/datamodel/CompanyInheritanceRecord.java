/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel;

import org.jooq.impl.CustomRecord;

import static fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritanceTable.COMPANY_INHERITANCE;

/**
 * @author dademo
 */
@SuppressWarnings("java:S110")
public class CompanyInheritanceRecord extends CustomRecord<CompanyInheritanceRecord> {

    private static final long serialVersionUID = -2596429405037105257L;

    public CompanyInheritanceRecord() {
        super(COMPANY_INHERITANCE);
    }
}

