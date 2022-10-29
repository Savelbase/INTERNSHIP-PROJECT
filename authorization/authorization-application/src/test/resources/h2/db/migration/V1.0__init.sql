CREATE TABLE IF NOT EXISTS roles (
    id          CHARACTER VARYING NOT NULL PRIMARY KEY,
    name        CHARACTER VARYING NOT NULL,
    authorities ARRAY,
    version     INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS users (
    id                   CHARACTER VARYING NOT NULL PRIMARY KEY,
    mobile_phone         VARCHAR (10) NOT NULL UNIQUE,
    password             CHARACTER VARYING,
    pin_code             CHARACTER VARYING,
    attempt_counter      INTEGER DEFAULT 0,
    status               CHARACTER VARYING DEFAULT 'ACTIVE',
    role_id              CHARACTER VARYING CONSTRAINT fk_users_role REFERENCES roles(id) ON DELETE CASCADE,
    version              INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS refresh_tokens (
    id               CHARACTER VARYING NOT NULL PRIMARY KEY,
    hash             CHARACTER VARYING NOT NULL,
    expiry_date_time TIMESTAMP NOT NULL,
    user_id          CHARACTER VARYING CONSTRAINT fk_refresh_tokens_user REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS events (
    id        CHARACTER VARYING NOT NULL PRIMARY KEY,
    type      CHARACTER VARYING NOT NULL,
    entity_id CHARACTER VARYING NOT NULL,
    author_id CHARACTER VARYING CONSTRAINT fk_event_users REFERENCES users(id) ON DELETE CASCADE,
    context   CHARACTER VARYING NOT NULL,
    date_time TIMESTAMP NOT NULL,
    parent_id CHARACTER VARYING,
    payload   OTHER,
    version   INTEGER DEFAULT 1
);
