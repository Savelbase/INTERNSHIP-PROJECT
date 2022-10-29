create table if not exists agreement
(
    id             text not null primary key,
    agreement_type      text not null,
    agreement_text text not null,
    actual         bool default false);