alter table credit_order alter column start_credit_period type date;
alter table credit_order alter column end_credit_period type date;
alter table credit_order add column credit_product_name text not null;