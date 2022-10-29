create table if not exists recipients (
    id             text primary key not null,
    name           text not null,
    account_number text not null
);

alter table receipts add column additional_info text;
alter table receipts add column recipient_id text
    constraint fk_receipts_recipient references recipients(id) on delete cascade;