/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions.data_gouv_fr.serializer.exception;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author dademo
 */
public class LocalDateTimeParseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4575735445407889946L;

    public LocalDateTimeParseException(String value) {
        super("Unable to parse value [%s] as %s instance".formatted(value, LocalDateTime.class));
    }
}
