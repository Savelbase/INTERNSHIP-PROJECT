ALTER TABLE department
    DROP COLUMN services;

ALTER TABLE department add column zone_id varchar(64) ;

create table if not exists service (
    id      text not null primary key,
    name text not null,
    type text not null
);

create table if not exists department_service (
    id      text not null primary key,
    department_id   text constraint fk_ds_department references department (id) on delete cascade ,
    service_id   text constraint fk_ds_service references service (id) on delete cascade
);