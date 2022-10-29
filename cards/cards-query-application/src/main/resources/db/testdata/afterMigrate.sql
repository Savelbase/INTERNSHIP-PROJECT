delete from clients where id is not null;
delete from card_products where id is not null;
delete from recipients where id is not null;
delete from agreement where id is not null;
delete from card_conditions_dictionary where id is not null;
delete from card_conditions where id is not null;

-- ADMIN
-- USERS
insert into clients(id, first_name, last_name, status)
values ('0d3a68a1-5919-4914-bc20-839fae2480ac', 'Admin', 'Admin', 'ACTIVE'),
       ('bc9efe3b-a8a0-45f4-b57e-0415f5d0c676', 'Ignat', 'Frolov', 'ACTIVE'),
       ('23543d9c-2a68-4bc5-b5c2-1fe975a37b1f', 'Petr', 'Shaliapin', 'ACTIVE'),
       ('f943bf1a-e0d5-4388-bedd-e672903c9d71', 'Sofia', 'Ivanova', 'ACTIVE'),
       ('50c7efde-abf8-4b93-be82-582188a8b400', 'Olga', 'Teternikova', 'ACTIVE'),
       ('cb96c109-feac-4451-8fa7-b349ec73a021', 'Igor', 'Serpov', 'ACTIVE');

-- CARD PRODUCTS
insert into card_products(id, card_name, type, currency)
values ('85e73ea5-c904-45d5-8787-1bda24d5db9e', 'Card name', 'DEPOSIT', 'RUS_RUB');

insert into card_orders(id, client_id, card_product_id, status, version)
values ('3643e0f3-83f9-4a70-8fbe-299562b8d0cd', '0d3a68a1-5919-4914-bc20-839fae2480ac',
        '85e73ea5-c904-45d5-8787-1bda24d5db9e', 'APPROVED', 1);

insert into accounts(id, account_number, client_id)
values ('8d3a68a1-5919-4914-bc20-839fae2480aa', 'l8+ycgO5vwROOXHQiPTZ6THbagD2wxmq2iVHW3dTwxvsnTNAOSnfDL8H39cMug75' ||
                                                'mQUMLvjuw5MyvESA/fFK67Jr2w9Q+uBfGq+hzQ8b/dXqoyh/vofcufU7+DgQGO6I' ||
                                                'pz55+qdam3oCtxOAVZM8M/tx6msNFOZ1Kr2522u2BJg=',
        '0d3a68a1-5919-4914-bc20-839fae2480ac');

insert into card_requisites(id, card_number, client_id, account_id, end_card_period, card_product_id )
values ('7d3a68a1-5919-4914-bc20-839fae2480aw', 'FfTrtQIFSyBAsg3U8BKDLGRZrC9uvHPCmAlwK+4Amg79kgx3L17l8GSTtDYBoRGL9' ||
                                                'wDsneDyPaju17RgxWdp1nlCQv7O1QQIKheyHbgJjKwcvyMr60xH6JDwokUARPNdIW' ||
                                                '/nMyh9vJOnFcxtrMf67qeiV8N/bN/8r0445jrlmp8=',
        '0d3a68a1-5919-4914-bc20-839fae2480ac',
        '8d3a68a1-5919-4914-bc20-839fae2480aa', '2022-04-29 11:34:43.145293', '85e73ea5-c904-45d5-8787-1bda24d5db9e');

insert into cards(id, client_id, card_requisites_id, card_balance, status, card_product_id, version)
values ('8d3a68a1-5919-2378-bc20-839fae2480aa', '0d3a68a1-5919-4914-bc20-839fae2480ac',
        '7d3a68a1-5919-4914-bc20-839fae2480aw', 1000, 'ACTIVE', '85e73ea5-c904-45d5-8787-1bda24d5db9e', 1);

insert into recipients(id, name, account_number)
values ('0d3a68a1-a8a0-45f4-b57e-0415f5d0c676', 'Recipient Name', '7d3a68a1-a8a0-4914-bc20-839fae2480ab');

insert into receipts(id, card_id, transaction_type, transaction_time, transaction_amount, transaction_location,
                     recipient_id, additional_info)
values ('27d7fe51-12e2-4463-b77d-a2344d387de3', '8d3a68a1-5919-2378-bc20-839fae2480aa', 'PAYMENT',
        '2022-07-10T15:14:41.044617+01:00', 1000, 'Shop1', '0d3a68a1-a8a0-45f4-b57e-0415f5d0c676', 'Покупка товаров'),
       ('c9e020a8-7cf4-4bb1-9c56-9c110bf376c6', '8d3a68a1-5919-2378-bc20-839fae2480aa', 'TRANSFER',
        '2022-07-11T16:14:33.044617+01:00', 1000, 'Bank', '0d3a68a1-a8a0-45f4-b57e-0415f5d0c676', 'Получение услуг'),
       ('51160f2a-ab2a-44cf-b13a-bd7ac6278806', '8d3a68a1-5919-2378-bc20-839fae2480aa', 'ANOTHER',
        '2022-07-12T20:24:41.044617+01:00', 1000, 'MobileBank', '0d3a68a1-a8a0-45f4-b57e-0415f5d0c676', 'Получение услуг'),
       ('bd7ac627-ab2a-44cf-b13a-8806c9e020a8', '8d3a68a1-5919-2378-bc20-839fae2480aa', 'ANOTHER',
        '2022-07-12T20:24:41.044617+01:00', -4200.5, 'A-Bank', '0d3a68a1-a8a0-45f4-b57e-0415f5d0c676', 'Получение услуг');

insert into agreement(id, agreement_type, agreement_text, actual)
values ('a35391b8-0b7e-48af-9552-fb568d921e3e', 'TERMS',
        'TERMS: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at leo ultrices, tempor mi mollis, varius ex. ' ||
        'Etiam et magna tellus. Donec sagittis non nisl a mollis. Sed a justo sollicitudin, viverra leo vel, suscipit urna. ' ||
        'Etiam fringilla ex eu finibus fringilla. Nulla faucibus tellus in augue consequat, ac consequat nibh ultricies. ' ||
        'Duis leo urna, sodales ut nunc quis, tincidunt placerat nibh. Donec non ultrices massa, gravida sollicitudin nulla. ' ||
        'Fusce at sem maximus, facilisis sem in, ullamcorper mauris. Praesent luctus dui sapien, ' ||
        'elementum semper orci ullamcorper sit amet.', true),
       ('230ac8dd-80b9-4084-a843-a6f9a21c8050', 'TARIFFS',
        'TARIFFS: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at leo ultrices, tempor mi mollis, varius ex. ' ||
        'Etiam et magna tellus. Donec sagittis non nisl a mollis. Sed a justo sollicitudin, viverra leo vel, suscipit urna. ' ||
        'Etiam fringilla ex eu finibus fringilla. Nulla faucibus tellus in augue consequat, ac consequat nibh ultricies. ' ||
        'Duis leo urna, sodales ut nunc quis, tincidunt placerat nibh. Donec non ultrices massa, gravida sollicitudin nulla. ' ||
        'Fusce at sem maximus, facilisis sem in, ullamcorper mauris. Praesent luctus dui sapien, ' ||
        'elementum semper orci ullamcorper sit amet.', true),
       ('6fe9b008-e344-4d6a-b185-f981cb2e86eb', 'RECOMMENDATIONS',
        'RECOMMENDATIONS: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at leo ultrices, tempor mi mollis, varius ex. ' ||
        'Etiam et magna tellus. Donec sagittis non nisl a mollis. Sed a justo sollicitudin, viverra leo vel, suscipit urna. ' ||
        'Etiam fringilla ex eu finibus fringilla. Nulla faucibus tellus in augue consequat, ac consequat nibh ultricies. ' ||
        'Duis leo urna, sodales ut nunc quis, tincidunt placerat nibh. Donec non ultrices massa, gravida sollicitudin nulla. ' ||
        'Fusce at sem maximus, facilisis sem in, ullamcorper mauris. Praesent luctus dui sapien, ' ||
        'elementum semper orci ullamcorper sit amet.', true);

insert into card_conditions_dictionary (id, service, partner_cash_back, cash_back_list, max_cash_back_sum,
                                        withdraw_conditions, money_transfer_by_phone_number)
values ('6ba6c8b0-0d87-11ed-861d-0242ac120002', 'Обслуживание Бесплатно первые %d месяца, далее %d RUB в месяца',
        'До %d %', '["%s% при покупках от %s RUB в месяц" , "%s% при покупках от %s RUB в месяц"]', '%s',
        '["Бесплатно в любых банкоматах по всему миру"]', 'Бесплатно до %s RUB в месяц, свыше — %s%');

insert into card_conditions(id, service, partner_cash_back, cash_back_list, max_cash_back_sum, withdraw_conditions,
                            money_transfer_by_phone_number)
values ('6ba6c8b0-0d87-11ed-861d-0242ac120002', '{"numberOfFreeMonth" : 3 , "monthlyCost" : 2990}', 33,
        '[{"percent": 3, "minimumPurchaseAmount": 150000}, {"percent": 2, "minimumPurchaseAmount": 10000}]', 15000,
        null, '{"interestOnCashWithdrawal" : 0.5 , "maximumAmountWithoutInterest" : 100000}');

update card_products set card_conditions_dictionary_id = '6ba6c8b0-0d87-11ed-861d-0242ac120002',
                         card_conditions_id = '6ba6c8b0-0d87-11ed-861d-0242ac120002'
                     where id = '85e73ea5-c904-45d5-8787-1bda24d5db9e';