create table if not exists clients (
    id         text not null primary key,
    status     text not null,
    first_name text not null,
    last_name  text not null
);

create table if not exists accounts (
    id             text not null primary key,
    account_number text not null unique,
    client_id      text not null
);

create table if not exists deposit_products (
    id                  text not null primary key,
    deposit_name        text not null,
    deposit_rate        decimal not null,
    currency            text not null,
    term_type           text not null,
    renewable_type      text not null,
    refundable_type     text not null,
    expendable_type     text not null,
    capitalization_type text not null,
    deposit_target_type text not null
);

create table if not exists deposits (
    id                   text not null primary key,
    client_id            text not null,
    account_id           text constraint fk_deposits_account references accounts(id) on delete cascade,
    deposit_product_id   text constraint fk_deposits_deposit_product references deposit_products(id) on delete cascade,
    deposit_amount       decimal not null,
    start_deposit_period timestamp not null,
    end_deposit_period   timestamp not null,
    revocable            bool default true,
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
