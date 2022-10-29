create table if not exists accounts (
    id             text not null primary key,
    account_number text not null unique,
    client_id      text constraint fk_accounts_client references clients(id) on delete cascade
);

create table if not exists credit_dictionary (
    id       text not null primary key,
    name     text not null unique
);

create table if not exists credits (
    id                  text not null primary key,
    account_id          text constraint fk_credits_account references accounts(id) on delete cascade,
    credit_product_id   text constraint fk_credits_credit_product references credit_dictionary(id) on delete cascade,
    agreement_number    text not null,
    credit_amount       decimal not null,
    debt                decimal not null,
    start_credit_period date not null,
    end_credit_period   date not null,
    date_to_pay         date not null,
    employer_tin        text not null,
    version             integer default 1
);

create table if not exists pay_graph (
    id           text not null primary key,
    credit_id    text not null constraint fk_pay_graph_credit references credits(id) on delete cascade,
    payment_list jsonb
);