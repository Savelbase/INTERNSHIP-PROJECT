create table if not exists roles (
    id          text not null primary key,
    name        text not null,
    authorities text[],
    version     integer default 1
);

create table if not exists clients (
    id                   text not null primary key,
    mobile_phone         varchar(10) not null unique,
    passport_number      varchar(20),
    resident             bool default false,
    first_name           varchar(30),
    last_name            varchar(30),
    middle_name          varchar(30),
    bank_client          bool default false,
    password             text,
    security_question    varchar(50),
    security_answer      varchar(50),
    registered           bool default false,
    role_id              text constraint fk_clients_role references roles(id) on delete cascade,
    verification_code_id text,
    accession_date_time  timestamp,
    version              integer default 1
);

create table if not exists events (
    id        text not null primary key,
    type      text not null,
    entity_id text not null,
    author_id text constraint fk_event_clients references clients(id) on delete cascade,
    context   text not null,
    date_time timestamp not null,
    parent_id text,
    payload   jsonb,
    version   integer default 1
);

create index cx_event_date_time on events (date_time);
cluster events using cx_event_date_time;