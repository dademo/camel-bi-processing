/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.beans.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.tools.tools.object_mapper.ObjectMapperCustomizer;
import jakarta.annotation.Nonnull;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import java.util.stream.Stream;

/**
 * @author dademo
 */
public class ProblemObjectMapperCustomizer implements ObjectMapperCustomizer {

    @Override
    public void customize(@Nonnull ObjectMapper mapper) {

        Stream.of(
            new ProblemModule(),
            new ConstraintViolationProblemModule()
        ).forEach(mapper::registerModule);
    }
}
