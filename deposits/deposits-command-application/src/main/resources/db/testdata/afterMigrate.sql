delete from clients where id is not null;
delete from accounts where id is not null;
delete from deposit_products where id is not null;

-- ADMIN
-- USERS
insert into clients(id, first_name, last_name, status)
values ('0d3a68a1-5919-4914-bc20-839fae2480ac', 'Admin', 'Admin', 'ACTIVE'),
    ('bc9efe3b-a8a0-45f4-b57e-0415f5d0c676', 'Ignat', 'Frolov', 'ACTIVE'),
    ('23543d9c-2a68-4bc5-b5c2-1fe975a37b1f', 'Petr', 'Shaliapin', 'ACTIVE'),
    ('f943bf1a-e0d5-4388-bedd-e672903c9d71', 'Sofia', 'Ivanova', 'ACTIVE'),
    ('50c7efde-abf8-4b93-be82-582188a8b400', 'Olga', 'Teternikova', 'ACTIVE'),
    ('cb96c109-feac-4451-8fa7-b349ec73a021', 'Igor', 'Serpov', 'ACTIVE');

-- ACCOUNTS
insert into accounts(id, account_number, client_id)
values ('2e3ab41a-58a4-4b1f-8c2d-37b9d525bbeb','9b97c3f2-7a36-4624-8841-782137382544','0d3a68a1-5919-4914-bc20-839fae2480ac'),
       ('1beaacf3-883e-478c-93cc-9a28c531c52f','224123f0-ca58-4ed7-838a-726f4998b8f6','bc9efe3b-a8a0-45f4-b57e-0415f5d0c676'),
       ('1f96776e-0c82-4c1a-872b-8930df4a208f','c609657d-2d60-414a-90b8-3048fc985b2e','23543d9c-2a68-4bc5-b5c2-1fe975a37b1f');


insert into deposit_products(id, deposit_name, deposit_rate, currency, term_type, renewable_type,
                             refundable_type, expendable_type, capitalization_type, deposit_target_type)
values('efcd9efc-01ae-4b05-87c5-af7f97cf4db6', 'Накопительный', 8.3, 'RUS_RUB', 'TIME_DEPOSIT', 'RENEWABLE', 'REFUNDABLE', 'NON_EXPENDABLE', 'CAPITALIZATION', 'NON_TARGET' ),
      ('e3214984-811e-4030-b3a9-17749bea1c99', 'ЦифровойКошелёк', 0.55, 'RUS_RUB', 'ON_DEMAND', 'AUTO_RENEWABLE', 'REFUNDABLE', 'EXPENDABLE', 'CAPITALIZATION', 'NON_TARGET');

insert into deposits(id, client_id, account_id, deposit_product_id, deposit_amount, start_deposit_period,
                     end_deposit_period, revocable, version)
values ('5b43f0d0-20a2-491e-b90c-fff6433b5731', '0d3a68a1-5919-4914-bc20-839fae2480ac', '2e3ab41a-58a4-4b1f-8c2d-37b9d525bbeb', 'efcd9efc-01ae-4b05-87c5-af7f97cf4db6',
        1000000, '2022-07-07T15:14:41.044617+01:00', '2024-07-07T15:14:41.044617+01:00', true, 1),
       ('782beea0-de59-4b63-8ae1-66deea81ba95', 'bc9efe3b-a8a0-45f4-b57e-0415f5d0c676', '1beaacf3-883e-478c-93cc-9a28c531c52f', 'e3214984-811e-4030-b3a9-17749bea1c99',
        350000, '2022-07-07T15:14:41.044617+01:00', '2022-06-07T15:14:41.044617+01:00', true, 1);
