alter table client rename client_status to status;
alter table client alter column status set default 'ACTIVE';

alter table user_profile add column pin_code text;