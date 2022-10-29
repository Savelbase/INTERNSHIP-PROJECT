delete from roles where id is not null;
delete from rules where id is not null;

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
-- USERS
insert into clients(id, mobile_phone, passport_number, resident, first_name, last_name, middle_name, bank_client,
                    password, security_question, security_answer, registered, role_id, accession_date_time, version)
values ('0d3a68a1-5919-4914-bc20-839fae2480ac', '9077777777', '7777000000', true, 'Admin', 'Admin', 'Admin', true,
        '$2a$10$aREYTt82JAyjy99eiYU1MuLGUMszpfmvtguvwOQTACKHzts7VSl7C', 'Question', 'Answer', true,
        '199790f4-1c8a-4aa7-ad9d-ec90111367ec', '2022-04-29 11:34:43.145293', 1),
       ('bc9efe3b-a8a0-45f4-b57e-0415f5d0c676', '9211111111', '9360362514', true, 'Ignat', 'Frolov', 'Petrovich', true,
        '$2a$10$HP6mfnFyTOocyxVEFEFckuf4h9RxpwOKDoNLP/RKKwFnf/LQPzXTO', 'fish_or_chips', 'fish', true,
        '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', '2022-05-03 12:15:30.144785', 1),
       ('23543d9c-2a68-4bc5-b5c2-1fe975a37b1f', '9633333333', '9548698512', true, 'Petr', 'Shaliapin', 'Nikolaevich', true,
        '$2a$10$HnSn5wsqVP6k0j5nbpgtIORYP4CvXcNTk8RaK1IKyV8vI9WJTN5VO', 'my_first_girlfriend', 'daria_d', true,
        '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', '2022-05-15 13:16:43.156293', 1),
       ('f943bf1a-e0d5-4388-bedd-e672903c9d71', '9096666666', '9910349761', true, 'Sofia', 'Ivanova', 'Martynova', true,
        '$2a$10$gEl4YFT/yZnFzPVKGCaFEOwoPxoFmaHgFlz20AHzQ6gDNGV9W3yra', 'to_be_or_not_to_be', 'be_bee', true,
        '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', '2022-04-17 01:25:43.236283', 1),
       ('50c7efde-abf8-4b93-be82-582188a8b400', '9999999999', '9755022667', true, 'Olga', 'Teternikova', 'Maksimova', true,
        '$2a$10$6Rs8j9thErA7CJX5u1XnxeasSffcmlP0zwrdxJyEKZxdEfH8FcGRq', 'the_best_animal_on_the_world', 'my_kitty', true,
        '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', '2022-04-30 17:48:59.482365', 1),
       ('cb96c109-feac-4451-8fa7-b349ec73a021', '9278888888', '9217111112', true, 'Igor', 'Serpov', 'Andreevitch', true,
        '$2a$10$xrCBpOcZnoCW4Koeh4RH7OVqMJ35gLvtBa38zGaEvJ/srQiCar.4S', 'where_do_i_prefer_to_have_lanch', 'the_pizza_house', true,
        '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', '2022-06-01 19:25:01.145293', 1);

-- RULES
insert into rules(id, rule_type, text, actual)
values ('ba153b7c-57e5-4bcb-b87f-bed64654e84d', 'PRIVACY_POLICY',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at leo ultrices, tempor mi mollis, varius ex. ' ||
    'Etiam et magna tellus. Donec sagittis non nisl a mollis. Sed a justo sollicitudin, viverra leo vel, suscipit urna. ' ||
    'Etiam fringilla ex eu finibus fringilla. Nulla faucibus tellus in augue consequat, ac consequat nibh ultricies. ' ||
    'Duis leo urna, sodales ut nunc quis, tincidunt placerat nibh. Donec non ultrices massa, gravida sollicitudin nulla. ' ||
    'Fusce at sem maximus, facilisis sem in, ullamcorper mauris. Praesent luctus dui sapien, ' ||
    'elementum semper orci ullamcorper sit amet.', true),
    ('d5d254c4-d56f-4bcb-b86a-8f6028197c9a', 'RBSS',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at leo ultrices, tempor mi mollis, varius ex. ' ||
    'Etiam et magna tellus. Donec sagittis non nisl a mollis. Sed a justo sollicitudin, viverra leo vel, suscipit urna. ' ||
    'Etiam fringilla ex eu finibus fringilla. Nulla faucibus tellus in augue consequat, ac consequat nibh ultricies. ' ||
    'Duis leo urna, sodales ut nunc quis, tincidunt placerat nibh. Donec non ultrices massa, gravida sollicitudin nulla. ' ||
    'Fusce at sem maximus, facilisis sem in, ullamcorper mauris. Praesent luctus dui sapien, ' ||
    'elementum semper orci ullamcorper sit amet.', true);