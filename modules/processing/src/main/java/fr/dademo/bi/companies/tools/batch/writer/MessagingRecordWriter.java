package fr.dademo.bi.companies.tools.batch.writer;

import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jeasy.batch.core.writer.RecordWriter;

public interface MessagingRecordWriter<T> extends RecordWriter<T> {

    Emitter<T> getEmitter();
}
