alter table card_requisites drop column card_name;
alter table card_requisites drop column currency;
alter table cards drop column type;
alter table cards add column version integer default 1;

create table if not exists card_products (
    id        text not null primary key,
    card_name text not null,
    currency  text not null,
    type      text not null
);

alter table card_requisites add column card_product_id text
    constraint fk_card_requisites_card_product references card_products(id) on delete cascade;

alter table cards add column card_product_id text
    constraint fk_cards_card_product references card_products(id) on delete cascade;

create table if not exists card_orders (
    id              text not null primary key,
    client_id       text  constraint fk_card_order_client references clients(id) on delete cascade,
    card_product_id text constraint fk_card_order_card_product references card_products(id) on delete cascade,
    status          text not null,
    version         integer default 1
);