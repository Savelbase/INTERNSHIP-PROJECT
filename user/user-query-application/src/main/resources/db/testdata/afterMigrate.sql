delete from roles where id is not null;

insert into roles(id, name, authorities, version)
values('199790f4-1c8a-4aa7-ad9d-ec90111367ec', 'ADMIN', array['AUTHORIZATION', 'USER_EDIT', 'USER_VIEW',
       'UPLOAD_IMAGE', 'VIEW_IMAGE', 'CREDIT_EDIT', 'CREDIT_VIEW', 'EDIT_CREDIT_ORDER_STATUS', 'DELETE_USER',
       'CARD_EDIT', 'CARD_VIEW', 'APPROVE_BANK_CLIENT', 'EDIT_CARD_ORDER_STATUS'], 1),
      ('5467e079-c77a-4e31-b908-cd2313eee8ac', 'CLIENT', array['REGISTRATION', 'SELF_REGISTRATION'], 1),
      ('081fa992-64de-47b4-9173-d8dd910c9d3e', 'BANK_CLIENT', array['REGISTRATION'], 1),
      ('341e7a40-cb4a-4c64-a3e5-6efdc17ef364', 'REGISTERED_CLIENT', array['AUTHORIZATION', 'USER_EDIT', 'USER_VIEW',
       'UPLOAD_IMAGE', 'VIEW_IMAGE', 'CREDIT_EDIT', 'CREDIT_VIEW', 'CARD_EDIT', 'CARD_VIEW'], 1);

-- ADMIN
-- password = Qwerty1!
-- pin_code = 777777
-- USERS
insert into client(id, first_name, last_name, middle_name, resident, passport_number, accession_date_time, mobile_phone,
                   role_id, bank_client)
values ('0d3a68a1-5919-4914-bc20-839fae2480ac', 'Admin', 'Admin', 'Admin', true, '7777000000',
        '2022-04-29 11:34:43.145293', '9077777777', '199790f4-1c8a-4aa7-ad9d-ec90111367ec', true),
       ('bc9efe3b-a8a0-45f4-b57e-0415f5d0c676', 'Ignat', 'Frolov', 'Petrovich', true, '9360362514',
        '2022-05-03 12:15:30.144785', '9211111111', '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', true),
       ('23543d9c-2a68-4bc5-b5c2-1fe975a37b1f', 'Petr', 'Shaliapin', 'Nikolaevich', true, '9548698512',
        '2022-05-15 13:16:43.156293', '9633333333', '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', true),
       ('f943bf1a-e0d5-4388-bedd-e672903c9d71', 'Sofia', 'Ivanova', 'Martynova', true, '9910349761',
        '2022-04-17 01:25:43.236283', '9096666666', '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', true),
       ('50c7efde-abf8-4b93-be82-582188a8b400', 'Olga', 'Teternikova', 'Maksimova', true, '9755022667',
        '2022-04-30 17:48:59.482365', '9999999999', '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', true),
       ('cb96c109-feac-4451-8fa7-b349ec73a021', 'Igor', 'Serpov', 'Andreevitch', true, '9217111112',
        '2022-06-01 19:25:01.145293', '9278888888', '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', true);

insert into user_profile(id, password, security_question, security_answer,notifications, pin_code, version)
values ('0d3a68a1-5919-4914-bc20-839fae2480ac', '$2a$10$HP6mfnFyTOocyxVEFEFckuf4h9RxpwOKDoNLP/RKKwFnf/LQPzXTO',
        'security_question', 'security_answer', '[{"type": "SMS", "state": false}, {"type": "PUSH", "state": false}, {"type": "EMAIL", "state": false}]',
        '$2a$10$fz4Ime6UTtfZM5b30olxzOy77FTnCqarwdLrpIz.J77bk9PHZb.Uq', 1),
       ('bc9efe3b-a8a0-45f4-b57e-0415f5d0c676', '$2a$10$aREYTt82JAyjy99eiYU1MuLGUMszpfmvtguvwOQTACKHzts7VSl7C',
        'fish_or_chips', 'fish', '[{"type": "SMS", "state": false}, {"type": "PUSH", "state": false}, {"type": "EMAIL", "state": false}]',
        '$2a$10$a2GaO5yx5OfvakA21gvcauOFhZyLidlnsc4I4DWmefE.gmBITqjX2', 1),
       ('23543d9c-2a68-4bc5-b5c2-1fe975a37b1f', '$2a$10$HnSn5wsqVP6k0j5nbpgtIORYP4CvXcNTk8RaK1IKyV8vI9WJTN5VO',
        'my_first_girlfriend', 'daria_d', '[{"type": "SMS", "state": false}, {"type": "PUSH", "state": false}, {"type": "EMAIL", "state": false}]',
        '$2a$10$BAW9zHEYuTE6fVup8NvqducoR.jjrC83w/vsuOBx2MvGu0LI22Joe', 1),
       ('f943bf1a-e0d5-4388-bedd-e672903c9d71', '$2a$10$gEl4YFT/yZnFzPVKGCaFEOwoPxoFmaHgFlz20AHzQ6gDNGV9W3yra',
        'to_be_or_not_to_be', 'be_bee', '[{"type": "SMS", "state": false}, {"type": "PUSH", "state": false}, {"type": "EMAIL", "state": false}]',
        '$2a$10$WbSfHW74ag9rv5vemgYImOqc5Wkd9x/lBvkpsBB2BhmJYWaTLX/82', 1),
       ('50c7efde-abf8-4b93-be82-582188a8b400', '$2a$10$6Rs8j9thErA7CJX5u1XnxeasSffcmlP0zwrdxJyEKZxdEfH8FcGRq',
        'the_best_animal_on_the_world', 'my_kitty', '[{"type": "SMS", "state": false}, {"type": "PUSH", "state": false}, {"type": "EMAIL", "state": false}]',
        '$2a$10$Tf1ywZZRJmNYO/YhVDmmGutMjzJA9A/8jbC9cMlq3PX2QFIjAlkz2', 1),
       ('cb96c109-feac-4451-8fa7-b349ec73a021', '$2a$10$xrCBpOcZnoCW4Koeh4RH7OVqMJ35gLvtBa38zGaEvJ/srQiCar.4S',
        'where_do_i_prefer_to_have_lanch', 'the_pizza_house', '[{"type": "SMS", "state": false}, {"type": "PUSH", "state": false}, {"type": "EMAIL", "state": false}]',
        '$2a$10$ctpNNuGnj43UByI1MT7rMOmLvz.QYwNPo6aganwIMJhzEyrqRFAi6', 1);
