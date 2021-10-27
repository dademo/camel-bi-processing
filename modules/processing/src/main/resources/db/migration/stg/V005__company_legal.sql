DROP TABLE IF EXISTS COMPANY_LEGAL_UNIT;

CREATE UNLOGGED TABLE COMPANY_LEGAL_UNIT (
    SIREN                            VARCHAR(9) NOT NULL UNIQUE PRIMARY KEY,
    DIFFUSION_STATUS                 VARCHAR(1),
    IS_PURGED                        BOOLEAN DEFAULT FALSE,
    CREATION_DATE                    DATE,
    ACRONYM                          VARCHAR(20),
    SEX                              VARCHAR(1),
    FIRST_NAME_1                     VARCHAR(20),
    FIRST_NAME_2                     VARCHAR(20),
    FIRST_NAME_3                     VARCHAR(20),
    FIRST_NAME_4                     VARCHAR(20),
    USUAL_FIRST_NAME                 VARCHAR(20),
    PSEUDONYM                        VARCHAR(100),
    ASSOCIATION_IDENTIFIER           VARCHAR(10),
    STAFF_NUMBER_RANGE               VARCHAR(2),
    STAFF_NUMBER_YEAR                INTEGER,
    LAST_PROCESSING                  TIMESTAMP WITHOUT TIME ZONE,
    PERIODS_COUNT                    INTEGER,
    COMPANY_CATEGORY                 VARCHAR(3),
    COMPANY_CATEGORY_YEAR            INTEGER,
    BEGIN_DATE                       DATE,
    ADMINISTRATIVE_STATE             VARCHAR(1),
    NAME                             VARCHAR(100),
    USUAL_NAME                       VARCHAR(100),
    DENOMINATION                     VARCHAR(120),
    USUAL_DENOMINATION_1             VARCHAR(70),
    USUAL_DENOMINATION_2             VARCHAR(70),
    USUAL_DENOMINATION_3             VARCHAR(70),
    LEGAL_CATEGORY                   VARCHAR(4),
    PRINCIPAL_ACTIVITY               VARCHAR(6),
    PRINCIPAL_ACTIVITY_NOMENCLATURE  VARCHAR(8),
    HEADQUARTERS_NIC                 VARCHAR(5),
    IS_SOCIAL_AND_SOLIDARITY_ECONOMY VARCHAR(1),
    IS_EMPLOYER                      VARCHAR(1)
);

CREATE INDEX ON COMPANY_LEGAL_UNIT(SIREN);
