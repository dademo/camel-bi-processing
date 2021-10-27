package fr.dademo.bi.companies.tools.batch.writer.exceptions;

import fr.dademo.bi.companies.tools.batch.RecordWriterType;

import java.util.function.Supplier;

public final class WriterNotImplementedException extends RuntimeException {

    public WriterNotImplementedException(RecordWriterType recordWriterType) {
        super(String.format("Writer not implemented for `%s` writer type", recordWriterType.toString()));
    }

    public static Supplier<WriterNotImplementedException> of(RecordWriterType recordWriterType) {
        return () -> new WriterNotImplementedException(recordWriterType);
    }
}
