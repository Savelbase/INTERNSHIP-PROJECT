create table if not exists deleted_users (
    id                  text not null primary key,
    mobile_phone        varchar(10) not null unique,
    first_name          varchar(30) not null,
    last_name           varchar(30) not null,
    middle_name         varchar(30),
    accession_date_time timestamp not null,
    passport_number     varchar(20) not null,
    resident            bool not null,
    role_id             text constraint fk_clients_role references roles(id) on delete cascade,
    status              text not null,
    notifications       jsonb not null,
    email               varchar(30),
    password            text not null,
    pin_code            text,
    security_question   text not null,
    security_answer     text not null
);