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