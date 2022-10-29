create table if not exists card_conditions (
    id                               text not null primary key,
    service                          jsonb ,
    partner_cash_back                int ,
    cash_back_list                   jsonb ,
    max_cash_back_sum                numeric ,
    withdraw_conditions              jsonb ,
    money_transfer_by_phone_number   jsonb
);
create table if not exists card_conditions_dictionary (
        id                              text not null primary key,
        service                         text not null,
        partner_cash_back               text not null,
        cash_back_list                  jsonb not null,
        max_cash_back_sum               text not null,
        withdraw_conditions             jsonb not null,
        money_transfer_by_phone_number  text not null
    );

alter table card_products add column card_conditions_dictionary_id text
    constraint fk_card_product_conditions_dictionary references card_conditions_dictionary(id)
        on delete cascade;
alter table card_products add column card_conditions_id text
    constraint fk_card_product_card_conditions references card_conditions(id)
        on delete cascade;
