/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.repository.datamodel;

import org.jooq.impl.CustomRecord;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@SuppressWarnings("java:S110")
public class DataSetRecord extends CustomRecord<DataSetRecord> {

    private static final long serialVersionUID = -7439817703000207620L;

    @SuppressWarnings("unused") // Required by Jooq
    public DataSetRecord() {
        super(new DataSetTable());
    }

    @SuppressWarnings("unused")
    public DataSetRecord(@Nonnull DataSetTable table) {
        super(table);
    }
}

