/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import org.apache.logging.log4j.util.Strings;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dademo
 */
@Component
public class MongoTemplateFactory {

    private final Map<String, MongoTemplate> cachedMongoTemplates = new HashMap<>();

    private final BatchDataSourcesConfiguration batchDataSourcesConfiguration;

    public MongoTemplateFactory(@Nonnull BatchDataSourcesConfiguration batchDataSourcesConfiguration) {
        this.batchDataSourcesConfiguration = batchDataSourcesConfiguration;
    }

    @Nonnull
    public MongoTemplate getTemplateForConnection(@NotEmpty String mongoDBDataSourceName) {

        return cachedMongoTemplates.computeIfAbsent(
            mongoDBDataSourceName,
            this::buildTemplateForConnection
        );
    }

    private MongoTemplate buildTemplateForConnection(@NotEmpty String mongoDBDataSourceName) {
        return mongoTemplateProvider(stgMongoClient(mongoDBDataSourceName), mongoDBDataSourceName);
    }

    private MongoClient stgMongoClient(@NotEmpty String mongoDBDataSourceName) {
        return MongoClients.create(mongoClientSettingsForClientName(mongoDBDataSourceName));
    }

    private MongoTemplate mongoTemplateProvider(@Nonnull MongoClient mongoClient,
                                                @NotEmpty String mongoDBDataSourceName) {

        return new MongoTemplate(
            mongoClient,
            batchDataSourcesConfiguration
                .getMongoDBClientConfigurationByName(mongoDBDataSourceName)
                .getDatabase()
        );
    }

    private MongoClientSettings mongoClientSettingsForClientName(@NotEmpty String configurationName) {

        final var configuration = batchDataSourcesConfiguration.getMongoDBClientConfigurationByName(configurationName);
        final var mongoClientSettingsBuilder = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(configuration.getConnectionString()))
            .applicationName(configuration.getApplicationName())
            .writeConcern(configuration.getWriteConcern())
            .readConcern(configuration.getReadConcern())
            .codecRegistry(CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
            ));

        if (Strings.isNotBlank(configuration.getUsername()) &&
            Strings.isNotBlank(configuration.getDatabase()) &&
            Strings.isNotBlank(configuration.getPassword())) {
            mongoClientSettingsBuilder.credential(MongoCredential.createCredential(configuration.getUsername(), configuration.getDatabase(), configuration.getPassword().toCharArray()));
        }

        return mongoClientSettingsBuilder.build();
    }
}
