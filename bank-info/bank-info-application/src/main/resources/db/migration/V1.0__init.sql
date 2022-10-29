create table if not exists department (
    id      text not null primary key,
    services  jsonb not null,
    address text not null,
    name text not null,
    status text not null,
    type text not null,
    schedule jsonb
);
create table if not exists contact (
    id text not null  primary key ,
    contact varchar(32) not null  ,
    type jsonb not null
);
