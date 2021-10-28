package fr.dademo.bi.companies.beans;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BeanValues {

    // DataSources
    public static final String STG_DATASOURCE_NAME = "stg";

    public static final String STG_DSL_CONTEXT = "stgDslContext";

    public static final String DEFAULT_SPRING_APPLICATION_NAME = "JAVA_SPRING";
    // Config
    public static final String CONFIG_DATASOURCE_BASE = "datasources";
    public static final String CONFIG_DATASOURCE_JDBC = CONFIG_DATASOURCE_BASE + ".jdbc";
    public static final String CONFIG_DATASOURCE_MONGODB = CONFIG_DATASOURCE_BASE + ".mongodb";

    public static final String DATASOURCE_CONFIG = "datasource";
    public static final String MONGO_CLIENT_CONFIG = "mongoClient";
    public static final String MONGO_TEMPLATE_CONFIG = "mongoTemplate";
    // Beans
    public static final String STG_DATASOURCE_BEAN_NAME = DATASOURCE_CONFIG + "_" + STG_DATASOURCE_NAME;
    public static final String STG_MONGO_CLIENT_CONFIG_BEAN_NAME = MONGO_CLIENT_CONFIG + "_" + STG_DATASOURCE_NAME;
    public static final String STG_MONGO_TEMPLATE_CONFIG_BEAN_NAME = MONGO_TEMPLATE_CONFIG + "_" + STG_DATASOURCE_NAME;

    public static final String STG_DSL_CONTEXT_DIALECT_PROVIDER = STG_DSL_CONTEXT + "ContextProvider";

    public static final String CONFIG_ENABLED = "enabled";

}
