alter table users add column attempt_counter integer default 0;
alter table users add column status text default 'ACTIVE';