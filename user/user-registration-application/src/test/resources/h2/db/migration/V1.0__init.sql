CREATE TABLE IF NOT EXISTS roles (
    id          CHARACTER VARYING NOT NULL PRIMARY KEY,
    name        CHARACTER VARYING NOT NULL,
    authorities ARRAY,
    version     INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS clients (
    id                   CHARACTER VARYING NOT NULL PRIMARY KEY,
    mobile_phone         VARCHAR (10) NOT NULL UNIQUE,
    passport_number      VARCHAR(20),
    resident             BOOLEAN DEFAULT FALSE,
    first_name           VARCHAR(30),
    last_name            VARCHAR(30),
    middle_name          VARCHAR(30),
    bank_client          BOOLEAN DEFAULT FALSE,
    password             CHARACTER VARYING,
    security_question    VARCHAR(50),
    security_answer      VARCHAR(50),
    registered           BOOLEAN DEFAULT FALSE,
    role_id              CHARACTER VARYING CONSTRAINT fk_clients_role REFERENCES roles(id) ON DELETE CASCADE,
    verification_code_id CHARACTER VARYING,
    accession_date_time  TIMESTAMP,
    version              INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS verification_codes (
    id                      CHARACTER VARYING NOT NULL PRIMARY KEY,
    client_id               CHARACTER VARYING CONSTRAINT fk_verification_clients REFERENCES clients(id) ON DELETE CASCADE,
    verification_code       CHARACTER VARYING,
    expiry_date_time        TIMESTAMP,
    attempt_counter         INTEGER DEFAULT 0,
    next_request_date_time  TIMESTAMP NOT NULL,
    exceeded_max_limit      BOOLEAN DEFAULT FALSE,
    verified                BOOLEAN DEFAULT FALSE,
    version                 INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS events (
    id        CHARACTER VARYING NOT NULL PRIMARY KEY,
    type      CHARACTER VARYING NOT NULL,
    entity_id CHARACTER VARYING NOT NULL,
    author_id CHARACTER VARYING CONSTRAINT fk_event_clients REFERENCES clients(id) ON DELETE CASCADE,
    context   CHARACTER VARYING NOT NULL,
    date_time TIMESTAMP NOT NULL,
    parent_id CHARACTER VARYING,
    payload   OTHER,
    version   INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS rules (
    id        CHARACTER VARYING NOT NULL PRIMARY KEY,
    rule_type CHARACTER VARYING NOT NULL,
    text      CHARACTER VARYING NOT NULL,
    actual    BOOLEAN DEFAULT FALSE
);
