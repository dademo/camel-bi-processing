/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.generic.stream_definitions.repository;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.data.generic.stream_definitions.exception.InputStreamIdentifierValidationException;
import lombok.SneakyThrows;
import org.apache.commons.io.input.TeeInputStream;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author dademo
 */
public abstract class BaseValidationContext<T extends InputStreamIdentifier<?>> extends InputStream {

    @Nonnull
    private final InputStream delegate;

    @Nonnull
    private final T inputStreamIdentifier;

    @Nonnull
    private final ExecutorService validatorsExecutorService;

    private final List<Future<Void>> validatorTasks;

    protected BaseValidationContext(@Nonnull InputStream inputStream,
                                    @Nonnull T inputStreamIdentifier,
                                    @Nonnull List<? extends InputStreamIdentifierValidator<T>> streamValidators
    ) throws IOException {

        this.inputStreamIdentifier = inputStreamIdentifier;
        this.validatorsExecutorService = Executors.newFixedThreadPool(
            streamValidators.size(),
            new ThreadFactoryBuilder()
                .setNameFormat(getStreamValidatorThreadNameTemplate(inputStreamIdentifier))
                .build()
        );
        this.validatorTasks = new ArrayList<>(streamValidators.size());
        this.delegate = applyValidators(inputStream, streamValidators);
    }

    private InputStream applyValidators(InputStream inputStream,
                                        List<? extends InputStreamIdentifierValidator<T>> streamValidators
    ) throws IOException {

        InputStream finalDelegate = inputStream;

        for (var validator : streamValidators) {
            finalDelegate = this.runInputValidation(finalDelegate, validator);
        }

        return finalDelegate;
    }

    @Override
    public int read() throws IOException {
        return delegate.read();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This function also waits for all validators to end.
     *
     * @throws IOException the underlying {@link InputStream} have thrown an exception.
     */
    @Override
    @SneakyThrows({InterruptedException.class, ExecutionException.class})
    public void close() throws IOException {

        try {
            // We close the delegate
            delegate.close();
            // ... and we complete all validators
            for (var validatorTask : validatorTasks) {
                try {
                    validatorTask.get();
                } catch (ExecutionException e) {
                    if (e.getCause() instanceof InputStreamIdentifierValidationException) {
                        throw (InputStreamIdentifierValidationException) e.getCause();
                    } else {
                        throw e;
                    }
                }
            }
        } finally {
            // We will shutdown the thread pool to avoid zombies threads
            validatorsExecutorService.shutdown();
        }
    }


    @SuppressWarnings("java:S2095")
    private InputStream runInputValidation(@Nonnull InputStream inputStream,
                                           @Nonnull InputStreamIdentifierValidator<T> validator
    ) throws IOException {

        final var pipedOutputStream = new PipedOutputStream();
        // A simple InputStream to cache provided stream
        final var streamToValidate = new PipedInputStream(pipedOutputStream);
        // We close the underlying inputStream as we do not keep track of them
        final var teeInputStream = new TeeInputStream(inputStream, pipedOutputStream, true);

        this.validatorTasks.add(
            this.validatorsExecutorService.submit(
                validatorCallableOf(
                    streamToValidate,
                    validator
                )
            ));

        return teeInputStream;
    }

    private Callable<Void> validatorCallableOf(
        @Nonnull InputStream inputStream,
        @Nonnull InputStreamIdentifierValidator<T> validator) {

        return () -> {
            validator.validate(inputStreamIdentifier, inputStream);
            return null;
        };
    }

    private String getStreamValidatorThreadNameTemplate(@Nonnull T inputStreamIdentifier) {

        var className = inputStreamIdentifier.getClass().getSimpleName();
        if (className.isEmpty()) {
            className = inputStreamIdentifier.getClass().getName();
        }

        return String.format(
            "stream-validation-%s-%s-%%d",
            className,
            inputStreamIdentifier.getUniqueIdentifier()
        );
    }
}
