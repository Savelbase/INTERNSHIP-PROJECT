create table if not exists clients (
    id         text not null primary key,
    status     text not null,
    first_name text not null,
    last_name  text not null
);

create table if not exists accounts (
    id             text not null primary key,
    account_number text not null unique,
    client_id      text constraint fk_accounts_client references clients(id) on delete cascade
);

create table if not exists card_requisites (
    id              text not null primary key,
    card_number     text not null,
    card_name       text not null,
    client_id       text constraint fk_card_requisites_client references clients(id) on delete cascade,
    account_id      text constraint fk_card_requisites_account references accounts(id) on delete cascade,
    end_card_period timestamp not null,
    currency        text not null
);

create table if not exists cards (
    id                 text not null primary key,
    client_id          text  constraint fk_cards_client references clients(id) on delete cascade,
    card_requisites_id text constraint fk_cards_card_requisite references card_requisites(id) on delete cascade,
    card_balance       decimal not null,
    status             text not null,
    type               text not null
);

create table if not exists receipts (
    id                   text not null primary key,
    card_id              text constraint fk_receipts_card references cards(id) on delete cascade,
    transaction_type     text not null,
    transaction_time     timestamp not null,
    transaction_amount   decimal not null,
    transaction_location text not null
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