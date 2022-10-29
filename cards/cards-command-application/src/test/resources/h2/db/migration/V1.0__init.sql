CREATE TABLE IF NOT EXISTS clients (
    id         CHARACTER VARYING NOT NULL PRIMARY KEY,
    status     CHARACTER VARYING NOT NULL,
    first_name CHARACTER VARYING NOT NULL,
    last_name  CHARACTER VARYING NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts (
    id             CHARACTER VARYING NOT NULL PRIMARY KEY,
    account_number CHARACTER VARYING NOT NULL UNIQUE,
    client_id      CHARACTER VARYING CONSTRAINT fk_account_client REFERENCES clients(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS card_products (
    id        CHARACTER VARYING NOT NULL PRIMARY KEY,
    card_name CHARACTER VARYING NOT NULL,
    currency  CHARACTER VARYING NOT NULL,
    type      CHARACTER VARYING NOT NULL
);

CREATE TABLE IF NOT EXISTS card_requisites (
    id              CHARACTER VARYING NOT NULL PRIMARY KEY,
    card_number     CHARACTER VARYING NOT NULL,
    client_id       CHARACTER VARYING CONSTRAINT fk_card_requisites_client REFERENCES clients(id) ON DELETE CASCADE,
    account_id      CHARACTER VARYING CONSTRAINT fk_card_requisites_account REFERENCES accounts(id) ON DELETE CASCADE,
    end_card_period TIMESTAMP NOT NULL,
    card_product_id CHARACTER VARYING CONSTRAINT fk_card_requisites_card_product REFERENCES card_products(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cards (
    id                 CHARACTER VARYING NOT NULL PRIMARY KEY,
    client_id          CHARACTER VARYING  CONSTRAINT fk_card_client REFERENCES clients(id) ON DELETE CASCADE,
    card_requisites_id CHARACTER VARYING CONSTRAINT fk_card_card_requisites REFERENCES card_requisites(id) ON DELETE CASCADE,
    card_balance       DECIMAL NOT NULL,
    status             CHARACTER VARYING NOT NULL,
    version            INTEGER DEFAULT 1,
    card_product_id    CHARACTER VARYING CONSTRAINT fk_card_card_product REFERENCES card_products(id) ON DELETE CASCADE,
    daily_limit_sum    DECIMAL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS recipients (
    id             CHARACTER VARYING NOT NULL,
    name           CHARACTER VARYING NOT NULL,
    account_number CHARACTER VARYING NOT NULL
);

CREATE TABLE IF NOT EXISTS receipts (
    id                   CHARACTER VARYING NOT NULL PRIMARY KEY,
    card_id              CHARACTER VARYING CONSTRAINT fk_receipts_card REFERENCES cards(id) ON DELETE CASCADE,
    transaction_type     CHARACTER VARYING NOT NULL,
    transaction_time     TIMESTAMP NOT NULL,
    transaction_amount   DECIMAL NOT NULL,
    transaction_location CHARACTER VARYING NOT NULL,
    additional_info      CHARACTER VARYING,
    recipient_id         CHARACTER VARYING CONSTRAINT fk_receipts_recipient REFERENCES recipients(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS card_orders (
    id              CHARACTER VARYING NOT NULL PRIMARY KEY,
    client_id       CHARACTER VARYING  CONSTRAINT fk_card_order_client REFERENCES clients(id) ON DELETE CASCADE,
    card_product_id CHARACTER VARYING CONSTRAINT fk_card_order_card_product REFERENCES card_products(id) ON DELETE CASCADE,
    status          CHARACTER VARYING NOT NULL,
    version         INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS events (
    id        CHARACTER VARYING NOT NULL PRIMARY KEY,
    type      CHARACTER VARYING NOT NULL,
    entity_id CHARACTER VARYING NOT NULL,
    author_id CHARACTER VARYING CONSTRAINT fk_event_client REFERENCES clients(id) ON DELETE CASCADE,
    context   CHARACTER VARYING NOT NULL,
    date_time TIMESTAMP NOT NULL,
    parent_id CHARACTER VARYING,
    payload   OTHER,
    version   INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS card_conditions (
    id                             CHARACTER VARYING NOT NULL PRIMARY KEY,
    service                        OTHER NOT NULL,
    partner_cash_back              INTEGER NOT NULL,
    cash_back_list                 OTHER NOT NULL,
    max_cash_back_sum              OTHER NOT NULL,
    withdraw_conditions            OTHER NOT NULL,
    money_transfer_by_phone_number OTHER NOT NULL,
    card_product_id CHARACTER VARYING CONSTRAINT fk_card_conditions_card_product
                                           REFERENCES card_products(id) ON DELETE CASCADE
);
