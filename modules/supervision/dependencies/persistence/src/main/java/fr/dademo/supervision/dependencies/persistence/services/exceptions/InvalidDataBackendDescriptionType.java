/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.exceptions;

import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendDescription;

import jakarta.annotation.Nonnull;

/**
 * @author dademo
 */
public class InvalidDataBackendDescriptionType extends RuntimeException {

    private static final long serialVersionUID = 3998704389580793384L;
    private static final String EXCEPTION_TEXT_TEMPLATE = "You provided an instance of <%s> while an instance of <%s> was expected based on the [backendKind] value";

    public InvalidDataBackendDescriptionType(@Nonnull Class<? extends DataBackendDescription> expectedClass,
                                             @Nonnull Object provided) {
        super(String.format(
            EXCEPTION_TEXT_TEMPLATE,
            provided.getClass().getCanonicalName(),
            expectedClass.getCanonicalName()
        ));
    }
}
