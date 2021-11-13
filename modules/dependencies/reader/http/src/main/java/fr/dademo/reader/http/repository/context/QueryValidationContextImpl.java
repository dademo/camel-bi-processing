package fr.dademo.reader.http.repository.context;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.data.generic.stream_definitions.exception.InputStreamIdentifierValidationException;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
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

public class QueryValidationContextImpl<T extends HttpInputStreamIdentifier> extends QueryValidationContext {

    @Nonnull
    private final InputStream delegate;

    @Nonnull
    private final T httpInputStreamIdentifier;

    @Nonnull
    private final ExecutorService validatorsExecutorService;

    private final List<Future<Void>> validatorTasks;

    public QueryValidationContextImpl(@Nonnull InputStream inputStream,
                                      @Nonnull T httpInputStreamIdentifier,
                                      @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators
    ) throws IOException {

        this.httpInputStreamIdentifier = httpInputStreamIdentifier;
        this.validatorsExecutorService = Executors.newFixedThreadPool(httpStreamValidators.size());
        this.validatorTasks = new ArrayList<>(httpStreamValidators.size());
        this.delegate = applyValidators(inputStream, httpStreamValidators);
    }

    private InputStream applyValidators(InputStream inputStream,
                                        List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators
    ) throws IOException {

        InputStream finalDelegate = inputStream;

        for (var validator : httpStreamValidators) {
            finalDelegate = this.runInputValidation(finalDelegate, validator);
        }

        return finalDelegate;
    }

    @Override
    public int read() throws IOException {
        return delegate.read();
    }

    @Override
    @SneakyThrows({InterruptedException.class, ExecutionException.class})
    public void close() throws IOException {

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
    }


    @SuppressWarnings("java:S2095")
    private InputStream runInputValidation(@Nonnull InputStream inputStream,
                                           @Nonnull InputStreamIdentifierValidator<HttpInputStreamIdentifier> validator
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
            @Nonnull InputStreamIdentifierValidator<HttpInputStreamIdentifier> validator) {

        return () -> {
            validator.validate(httpInputStreamIdentifier, inputStream);
            return null;
        };
    }
}
