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

    //// Constants ////
    // Common extensions //
    public static final String TRANSACTION_MANAGER_EXTENSION = "TransactionManager";
    public static final String DIALECT_PROVIDER_EXTENSION = "dialectProvider";
    public static final String TASK_EXECUTOR_EXTENSION = "TaskExecutor";
    public static final String DSL_CONTEXT_EXTENSION = "DslContext";
    // Technologies //
    public static final String FLYWAY_CONFIG = "flyway";
    public static final String DATA_SOURCE_CONFIG = "datasource";
    public static final String MONGO_CLIENT_CONFIG = "mongoClient";
    public static final String MONGO_TEMPLATE_CONFIG = "mongoTemplate";
    public static final String AMQP_TEMPLATE_CONFIG = "amqpTemplate";
    // Job data sources //
    public static final String CONFIG_JOB_INPUT_DATA_SOURCE = "input-data-source";
    public static final String CONFIG_JOB_OUTPUT_DATA_SOURCE = "output-data-source";
    // Types //
    public static final String CONFIG_NO_ACTION_TYPE = "no_action";
    public static final String CONFIG_JDBC_TYPE = "jdbc";
    public static final String CONFIG_MONGODB_TYPE = "mongodb";
    public static final String CONFIG_AMQP_TYPE = "amqp";

    //// DataSources factory ////
    public static final String BATCH_DATA_SOURCE_NAME = "batch";
    public static final String BATCH_DATA_SOURCE_NAME_CAPITALIZED = "Batch";
    public static final String BATCH_DATA_SOURCE_TRANSACTION_MANAGER_NAME = BATCH_DATA_SOURCE_NAME_CAPITALIZED + TRANSACTION_MANAGER_EXTENSION;
    public static final String BATCH_DATA_SOURCE_TRANSACTION_MANAGER_BEAN_NAME = DATA_SOURCE_CONFIG + BATCH_DATA_SOURCE_TRANSACTION_MANAGER_NAME;

    public static final String DEFAULT_SPRING_APPLICATION_NAME = "JAVA_SPRING";
    public static final String STG_DATA_SOURCE_NAME = "stg";
    public static final String CONFIG_JOBS_BASE = "batch.jobs";
    public static final String CONFIG_FLYWAY_BASE = "flyway";
    public static final String CONFIG_FLYWAY_MIGRATIONS = CONFIG_FLYWAY_BASE + ".migrations";
    public static final String CONFIG_DATA_SOURCE_BASE = "datasources";
    public static final String CONFIG_DATA_SOURCE_JDBC = CONFIG_DATA_SOURCE_BASE + ".jdbc";
    public static final String CONFIG_DATA_SOURCE_MONGODB = CONFIG_DATA_SOURCE_BASE + ".mongodb";
    // Config //
    public static final String CONFIG_JOBS_REPOSITORY_BASE = "batch.repository";
    public static final String CONFIG_JOBS_REPOSITORY_ENABLED = CONFIG_JOBS_REPOSITORY_BASE + ".enabled";
    public static final String CONFIG_DATA_SOURCE_AMQP_TEMPLATE = CONFIG_DATA_SOURCE_BASE + ".rabbitmq";
    public static final String CONFIG_WRITER_TYPE = "type";


    //// Beans ////
    // Database connectors //
    public static final String BATCH_DATA_SOURCE_BEAN_NAME = DATA_SOURCE_CONFIG + BATCH_DATA_SOURCE_NAME_CAPITALIZED;
    public static final String BATCH_DSL_CONTEXT_BEAN_NAME = DATA_SOURCE_CONFIG + BATCH_DATA_SOURCE_NAME_CAPITALIZED + DSL_CONTEXT_EXTENSION;

    // Values //
    public static final int DEFAULT_THREAD_POOL_SIZE = 2;
    public static final String TASK_EXECUTOR_BEAN_NAME = BATCH_DATA_SOURCE_NAME + TASK_EXECUTOR_EXTENSION;
    public static final String DATA_SET_MIGRATIONS_FOLDER_NAME = "batch_core";
    public static final String JOB_PARAMETER_STARTED_AT = "startedAt";
    public static final String JOB_PARAMETER_FORCE = "force";
}
