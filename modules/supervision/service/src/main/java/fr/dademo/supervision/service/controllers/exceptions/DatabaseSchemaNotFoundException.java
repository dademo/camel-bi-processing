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

package fr.dademo.supervision.service.controllers.exceptions;

import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @author dademo
 */
public class DatabaseSchemaNotFoundException extends ThrowableProblem {

    private static final long serialVersionUID = 2207706126538012379L;

    private final Long id;

    public DatabaseSchemaNotFoundException(@Nonnull Long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return "Not found";
    }

    @Override
    public StatusType getStatus() {
        return Status.NOT_FOUND;
    }

    @Override
    public String getDetail() {
        return MessageFormat.format("Database schema with id {0} not found", id);
    }

    @Override
    public Map<String, Object> getParameters() {

        return Map.of(
            "id", id
        );
    }
}
