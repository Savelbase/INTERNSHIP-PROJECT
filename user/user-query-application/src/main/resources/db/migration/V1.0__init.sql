create table roles
(
    id          text not null primary key,
    name        text not null,
    authorities text [],
    version     integer default 1
);

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
    version           integer default 1
);

CREATE TABLE client
(

    id                  text    NOT NULL
        PRIMARY KEY,
    first_name          VARCHAR(30),
    last_name           VARCHAR(30),
    middle_name         VARCHAR(30),
    resident            boolean NOT NULL,
    passport_number     text    NOT NULL,
    accession_date_time timestamp,
    client_status       text,
    mobile_phone        text    NOT NULL,
    role_id             text
        constraint fk_clients_role references roles (id) on delete cascade,
    version             integer default 1
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

ALTER TABLE user_profile
    ADD CONSTRAINT fk_client_user FOREIGN KEY (id) REFERENCES client (id) on delete cascade;