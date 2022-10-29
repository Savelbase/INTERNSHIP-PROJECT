create table if not exists card_conditions (
    id                               text not null primary key,
    service                          jsonb not null,
    partner_cash_back                int not null,
    cash_back_list                   jsonb not null,
    max_cash_back_sum                jsonb not null,
    withdraw_conditions              jsonb not null,
    money_transfer_by_phone_number   jsonb not null
);

alter table card_conditions add column card_product_id text
    constraint fk_card_conditions_card_product references card_products(id) on delete cascade;
