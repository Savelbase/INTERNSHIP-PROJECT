create table if not exists clients (
    id      text not null primary key,
    status  text not null,
    version integer default 1
);

create table if not exists credit_order (
    id                       text not null primary key,
    credit_amount            decimal not null,
    start_credit_period      timestamp not null,
    end_credit_period        timestamp not null,
    average_monthly_income   decimal not null,
    average_monthly_expenses decimal not null,
    employer_tin             text not null,
    status                   text not null,
    client_id                text constraint fk_credit_order_client references clients(id) on delete cascade,
    version                  integer default 1
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