package fr.dademo.bi.companies.tools.batch.writer;

import com.mongodb.client.MongoClient;
import org.jeasy.batch.core.writer.RecordWriter;

public interface MongoDBRecordWriter<T> extends RecordWriter<T> {

    MongoClient getMongoClient();
}
