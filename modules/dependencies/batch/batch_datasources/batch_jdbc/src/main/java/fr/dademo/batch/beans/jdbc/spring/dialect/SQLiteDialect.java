/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.jdbc.spring.dialect;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.dialect.AnsiDialect;

/**
 * @author dademo
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SQLiteDialect extends AnsiDialect {

    public static final SQLiteDialect INSTANCE = new SQLiteDialect();
}
