alter table clients rename client_status to status;
alter table clients alter column status set default 'ACTIVE';

alter table clients add column version integer default 1;

alter table verification_codes alter column appointment type text;