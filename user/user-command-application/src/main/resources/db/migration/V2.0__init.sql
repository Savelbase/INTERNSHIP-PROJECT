create table if not exists verification_codes (
    id                      text not null primary key,
    client_id               text constraint fk_verification_clients references clients(id) on delete cascade,
    verification_code       text,
    expiry_date_time        timestamp,
    attempt_counter         integer,
    next_request_date_time  timestamp not null,
    exceeded_max_limit      bool default false,
    verified                bool default false,
    version                 integer default 1
    );
