CREATE TABLE user_profile
(
    id                text    NOT NULL
        PRIMARY KEY,
    sms_notification  boolean NOT NULL,
    push_notification boolean NOT NULL,
    email             VARCHAR(30),
    password          text    NOT NULL,
    security_question text    NOT NULL,
    security_answer   text    NOT NULL,
    version           bigint
);
create table roles
(
    id          text not null primary key,
    name        text not null,
    authorities text [],
    version     integer default 1
);
CREATE TABLE clients
(
    id                  text
        PRIMARY KEY,
    first_name          VARCHAR(30) NOT NULL,
    last_name           VARCHAR(30) NOT NULL,
    middle_name         VARCHAR(30),
    resident            boolean     NOT NULL,
    passport_number     text        NOT NULL,
    accession_date_time date,
    client_status       text,
    mobile_phone        text        NOT NULL,
    role_id             text
        constraint fk_clients_role references roles (id) on delete cascade
);
CREATE TABLE passport_data
(
    id              text NOT NULL
        PRIMARY KEY,
    issuance_date   date NOT NULL,
    expiry_date     date NOT NULL,
    nationality     text NOT NULL,
    birth_date      date NOT NULL,
    passport_number text NOT NULL
);

CREATE TABLE events
(
    id        text PRIMARY KEY,
    type      text      NOT NULL,
    entity_id text,
    author_id text
        CONSTRAINT fk_events_users REFERENCES clients (id) on delete cascade,
    context   text,
    parent_id text,
    time      timestamp NOT NULL,
    version   integer,
    payload   jsonb
);

ALTER TABLE user_profile
    ADD CONSTRAINT fk_address_user FOREIGN KEY (id) REFERENCES clients (id) on delete cascade;

