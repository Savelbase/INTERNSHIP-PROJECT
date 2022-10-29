create table if not exists accounts (
    id             text not null primary key,
    account_number text not null unique,
    client_id      text constraint fk_accounts_client references clients(id) on delete cascade
);

create table if not exists credit_dictionary (
    id                text not null primary key,
    name              text not null unique,
    percent           float not null check (percent >= 0 and percent <= 100),
    currency          text not null,
    min_credit_amount decimal not null,
    max_credit_amount decimal not null,
    min_month_period  integer not null,
    max_month_period  integer not null,
    agreement_text    text not null,
    early_repayment   bool default true,
    guarantors        bool default true,
    income_statement  bool default true
);

create table if not exists credits (
    id                  text not null primary key,
    account_id          text constraint fk_credits_account references accounts(id) on delete cascade,
    credit_product_id   text constraint fk_credits_credit_product references credit_dictionary(id) on delete cascade,
    pay_graph_id        text not null,
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
    credit_id    text not null,
    payment_list jsonb
);