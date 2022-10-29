delete from clients where id is not null;
delete from credit_dictionary where id is not null;
delete from pay_graph where id is not null;

-- ADMIN
-- USERS
insert into clients(id, status)
values ('0d3a68a1-5919-4914-bc20-839fae2480ac', 'ACTIVE'),
       ('bc9efe3b-a8a0-45f4-b57e-0415f5d0c676', 'ACTIVE'),
       ('23543d9c-2a68-4bc5-b5c2-1fe975a37b1f', 'ACTIVE'),
       ('f943bf1a-e0d5-4388-bedd-e672903c9d71', 'ACTIVE'),
       ('50c7efde-abf8-4b93-be82-582188a8b400', 'ACTIVE'),
       ('cb96c109-feac-4451-8fa7-b349ec73a021', 'ACTIVE');

-- ACCOUNTS
insert into accounts(id, account_number, client_id)
values ('4135c96d-0008-4295-b668-b8d481132c6d','9b97c3f2-7a36-4624-8841-782137382544','0d3a68a1-5919-4914-bc20-839fae2480ac'),
       ('b7124197-5d5f-4ac0-aade-ae148f7bbc2e','224123f0-ca58-4ed7-838a-726f4998b8f6','bc9efe3b-a8a0-45f4-b57e-0415f5d0c676'),
       ('430d5f32-b128-4412-bc40-d4fbe762b580','c609657d-2d60-414a-90b8-3048fc985b2e','23543d9c-2a68-4bc5-b5c2-1fe975a37b1f'),
       ('5c434ee6-46df-4f88-8b0c-03e2191af71d','81d97bb4-fd39-475f-b03a-b1aa58d2c651','f943bf1a-e0d5-4388-bedd-e672903c9d71'),
       ('cffeba8d-14a2-4f6b-b15e-1043a3145576','85c0f99e-d29d-42dc-bb82-dd4581743463','50c7efde-abf8-4b93-be82-582188a8b400'),
       ('8b061026-7a0f-4e11-b6d9-532f7c1b6de5','a1aa400f-2ea0-4210-904c-94f80ba2c419','cb96c109-feac-4451-8fa7-b349ec73a021');

-- CREDIT DICTIONARY
insert into credit_dictionary(id, name, percent, currency, min_credit_amount, max_credit_amount, min_month_period, max_month_period,
                              early_repayment, guarantors, income_statement, agreement_text)
values ('596f2231-3abf-4f48-9226-c93a687af3b6', 'Consumer Credit', 10, 'RUS_RUB', 5000, 100000, 6, 60, true, true, true,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean dignissim porttitor velit et malesuada. ' ||
        'Maecenas risus ligula, imperdiet id euismod id, rutrum vel nibh. Cras posuere tincidunt nunc ut molestie. ' ||
        'Duis eu rutrum erat. Suspendisse commodo mauris at nulla scelerisque blandit. Mauris faucibus sit amet dui ac mattis. ' ||
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi rutrum augue eu justo pharetra mattis. ' ||
        'Mauris placerat fringilla arcu, placerat hendrerit metus auctor id. Pellentesque sodales, quam sit amet semper convallis, ' ||
        'mi leo pharetra est, sed porttitor sapien nunc quis mi. ' ||
        'Credit name = %s. ' ||
        'Credit amount range and currency = %s %s - %s %s. ' ||
        'Credit percent = %s ' ||
        'Credit month periods range = %s - %s.');