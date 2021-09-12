#!/bin/bash

set -e

export DB_BI="${DB_BI:-bi}"
export USR_BI_USR_ADM="${USR_BI_USR_ADM:-bi}"
export USR_BI_PWD_ADM="${USR_BI_PWD_ADM:-bi}"
export USR_BI_USR_RW="${USR_BI_USR_RW:-bi_rw}"
export BI_PWD_PWD_RW="${BI_PWD_PWD_RW:-bi_rw}"
export USR_BI_USR_RO="${USR_BI_USR_RO:-bi_ro}"
export BI_PWD_PWD_RO="${USR_BI_USR_ADM:-bi_ro}"

BI_SCHEMAS=(stg dwh dm_companies)

psql -v ON_ERROR_STOP=1 --username "${POSTGRES_USER}" --dbname "${POSTGRES_DB}" <<-EOSQL
    -- Working DB
    CREATE database "${DB_BI}";

    -- Required users
    CREATE USER "${USR_BI_USR_ADM}" WITH LOGIN ENCRYPTED PASSWORD '${USR_BI_PWD_ADM}';
    CREATE USER "${USR_BI_USR_RW}" WITH LOGIN ENCRYPTED PASSWORD '${BI_PWD_PWD_RW}';
    CREATE USER "${USR_BI_USR_RO}" WITH LOGIN ENCRYPTED PASSWORD '${BI_PWD_PWD_RO}';

    -- Grants
    ALTER DATABASE "${DB_BI}" OWNER TO "${USR_BI_USR_ADM}";
    GRANT ALL PRIVILEGES ON DATABASE "${DB_BI}" TO "${USR_BI_USR_ADM}" WITH GRANT OPTION;
EOSQL

for DB_SCHEMA in "${BI_SCHEMAS[@]}"; do
  psql -v ON_ERROR_STOP=1 --username "${USR_BI_USR_ADM}" --dbname "${DB_BI}" <<-EOSQL
    -- Schemas
    CREATE SCHEMA "${DB_SCHEMA}";

    -- Required users
    GRANT ALL PRIVILEGES ON SCHEMA "${DB_SCHEMA}" TO "${USR_BI_USR_RW}";
EOSQL
done
