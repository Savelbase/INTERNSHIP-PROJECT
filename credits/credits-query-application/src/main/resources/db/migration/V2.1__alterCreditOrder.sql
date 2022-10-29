alter table credit_order alter column start_credit_period type date;
alter table credit_order alter column end_credit_period type date;

alter table credit_order add column credit_product_id text not null;
alter table credit_order
    add constraint fk_credit_order_credit_product foreign key (credit_product_id)
        references credit_dictionary (id) on delete cascade;