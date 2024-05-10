/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.exceptions;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @author dademo
 */
@RequiredArgsConstructor
public class DatabaseNotFoundException extends ThrowableProblem {

    private static final long serialVersionUID = -7739331639729548L;

    @Nonnull
    private final Long id;

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
        return MessageFormat.format("Database with id {0} not found", id);
    }

    @Override
    public Map<String, Object> getParameters() {

        return Map.of(
            "id", id
        );
    }
}
