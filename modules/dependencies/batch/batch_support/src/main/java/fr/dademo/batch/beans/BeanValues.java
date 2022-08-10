/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author dademo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BeanValues {

    // Constants
    public static final String FLYWAY_CONFIG = "flyway";
    public static final String DATA_SOURCE_CONFIG = "datasource";
    public static final String MONGO_CLIENT_CONFIG = "mongoClient";
    public static final String MONGO_TEMPLATE_CONFIG = "mongoTemplate";
    public static final String AMQP_TEMPLATE_CONFIG = "amqpTemplate";
    // Types
    public static final String CONFIG_NO_ACTION_TYPE = "NO_ACTION";
    public static final String CONFIG_JDBC_TYPE = "JDBC";
    public static final String CONFIG_MONGODB_TYPE = "MONGODB";
    public static final String CONFIG_AMQP_TYPE = "AMQP";

    // DataSources factory
    public static final String BATCH_DATA_SOURCE_NAME = "batch";
    public static final String BATCH_DATA_SOURCE_TRANSACTION_MANAGER_NAME = BATCH_DATA_SOURCE_NAME + "-transaction_manager";
    public static final String BATCH_DATA_SOURCE_TRANSACTION_MANAGER_BEAN_NAME = DATA_SOURCE_CONFIG + "_" + BATCH_DATA_SOURCE_TRANSACTION_MANAGER_NAME;

    public static final String DEFAULT_SPRING_APPLICATION_NAME = "JAVA_SPRING";
    public static final String DIALECT_PROVIDER_EXTENSION = "_dialect_provider";
    public static final String DSL_CONTEXT_EXTENSION = "_dsl_context";
    public static final String STG_DATA_SOURCE_NAME = "stg";
    public static final String CONFIG_JOBS_BASE = "batch.jobs";
    public static final String CONFIG_FLYWAY_BASE = "flyway";
    public static final String CONFIG_FLYWAY_MIGRATIONS = CONFIG_FLYWAY_BASE + ".migrations";
    public static final String CONFIG_DATA_SOURCE_BASE = "datasources";
    public static final String CONFIG_DATA_SOURCE_JDBC = CONFIG_DATA_SOURCE_BASE + ".jdbc";
    public static final String CONFIG_DATA_SOURCE_MONGODB = CONFIG_DATA_SOURCE_BASE + ".mongodb";
    // Config
    public static final String CONFIG_JOBS_REPOSITORY_BASE = "batch.repository";
    public static final String CONFIG_JOBS_REPOSITORY_ENABLED = CONFIG_JOBS_REPOSITORY_BASE + ".enabled";
    public static final String CONFIG_DATA_SOURCE_AMQP_TEMPLATE = CONFIG_DATA_SOURCE_BASE + ".rabbitmq";
    public static final String CONFIG_WRITER_TYPE = "writer-type";

    //// Beans ////
    // Database connectors
    public static final String BATCH_DATA_SOURCE_BEAN_NAME = DATA_SOURCE_CONFIG + "_" + BATCH_DATA_SOURCE_NAME;
    // Config constants
    public static final String CONFIG_ENABLED = ".enabled";
    // Dialect providers
    public static final String BATCH_DATA_SOURCE_DIALECT_PROVIDER_BEAN_NAME = BATCH_DATA_SOURCE_BEAN_NAME + DIALECT_PROVIDER_EXTENSION;
    public static final String STG_DATA_SOURCE_BEAN_NAME = DATA_SOURCE_CONFIG + "_" + STG_DATA_SOURCE_NAME;
    public static final String STG_DATA_SOURCE_DIALECT_PROVIDER_BEAN_NAME = STG_DATA_SOURCE_BEAN_NAME + DIALECT_PROVIDER_EXTENSION;
    // SQL contexts
    public static final String STG_DATA_SOURCE_DSL_CONTEXT_BEAN_NAME = STG_DATA_SOURCE_BEAN_NAME + DSL_CONTEXT_EXTENSION;
    // MongoDB contexts
    public static final String STG_MONGO_CLIENT_CONFIG_BEAN_NAME = MONGO_CLIENT_CONFIG + "_" + STG_DATA_SOURCE_NAME;
    public static final String STG_MONGO_TEMPLATE_CONFIG_BEAN_NAME = MONGO_TEMPLATE_CONFIG + "_" + STG_DATA_SOURCE_NAME;
    // AMQP contexts
    public static final String STG_AMQP_TEMPLATE_CONFIG_BEAN_NAME = AMQP_TEMPLATE_CONFIG + "_" + STG_DATA_SOURCE_NAME;
    // Flyway
    public static final String FLYWAY_BATCH_BEAN_NAME = FLYWAY_CONFIG + "_" + BATCH_DATA_SOURCE_NAME;
    public static final String FLYWAY_STG_BEAN_NAME = FLYWAY_CONFIG + "_" + STG_DATA_SOURCE_NAME;

    //// Values ////
    public static final int DEFAULT_THREAD_POOL_SIZE = 2;
    public static final String TASK_EXECUTOR_BEAN_NAME = "batch_task_executor";
}
