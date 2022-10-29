create table if not exists roles (
    id          text not null primary key,
    name        text not null,
    authorities text[],
    version     integer default 1
);

create table if not exists users (
    id                  text not null primary key,
    mobile_phone        varchar(10) not null unique,
    password            text not null,
    role_id             text constraint fk_users_role references roles(id) on delete cascade,
    version             integer default 1
);

create table if not exists refresh_tokens (
    id               text not null primary key,
    hash             text not null,
    expiry_date_time timestamp not null,
    user_id          text constraint fk_refresh_tokens_user references users(id) on delete cascade
);

create table if not exists events (
    id        text not null primary key,
    type      text not null,
    entity_id text not null,
    author_id text constraint fk_events_user REFERENCES users (id) on delete cascade,
    context   text not null,
    date_time timestamp not null,
    parent_id text,
    payload   jsonb,
    version   integer default 1
);

create index cx_event_date_time on events (date_time);
cluster events using cx_event_date_time;