create table if not exists users
(
    id      text not null primary key,
    status  text,
    version int default 1
);

create table if not exists media_storage
(
    id                 text not null primary key,
    url                text not null,
    user_id            text constraint fk_media_storage_user references users(id) on delete cascade,
    confirmed          boolean not null default false,
    uploaded_date_time timestamp not null,
    version            int default 1
);
