package fr.dademo.bi.companies.tools.batch.writer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.annotation.Nullable;

@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor(staticName = "of")
public final class DefaultRecordWriterProvider<T> extends RecordWriterProvider<T> {

    private static final DefaultRecordWriterProvider<Object> DEFAULT_INSTANCE = DefaultRecordWriterProvider.builder().build();

    @Nullable
    private final JdbcRecordWriter<T> jdbcRecordWriter;
    @Nullable
    private final MongoDBRecordWriter<T> mongoDBRecordWriter;
    @Nullable
    private final MessagingRecordWriter<T> messagingRecordWriter;

    @SuppressWarnings("java:S1452")
    public static DefaultRecordWriterProvider<Object> defaultInstance() {
        return DEFAULT_INSTANCE;
    }

    @Nullable
    @Override
    protected JdbcRecordWriter<T> getJdbcRecordWriter() {
        return jdbcRecordWriter;
    }

    @Nullable
    @Override
    protected MongoDBRecordWriter<T> getMongoDBRecordWriter() {
        return mongoDBRecordWriter;
    }

    @Nullable
    @Override
    protected MessagingRecordWriter<T> getMessagingRecordWriter() {
        return messagingRecordWriter;
    }
}
