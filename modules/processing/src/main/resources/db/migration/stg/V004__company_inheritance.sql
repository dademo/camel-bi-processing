DROP TABLE IF EXISTS COMPANY_INHERITANCE;

CREATE TABLE COMPANY_INHERITANCE (
    ID                              SERIAL PRIMARY KEY NOT NULL,
    COMPANY_PREDECESSOR_SIREN       VARCHAR(14) NOT NULL,
    COMPANY_SUCCESSOR_SIREN         VARCHAR(14) NOT NULL,
    COMPANY_SUCCESSION_DATE         DATE NOT NULL,
    COMPANY_HEADQUARTER_CHANGE      BOOLEAN NOT NULL,
    COMPANY_ECONOMICAL_CONTINUITY   BOOLEAN NOT NULL,
    COMPANY_PROCESSING_DATE         TIMESTAMP WITHOUT TIME ZONE NOT NULL
);