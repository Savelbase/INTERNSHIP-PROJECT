INSERT INTO roles(id, name, authorities, version)
VALUES('199790f4-1c8a-4aa7-ad9d-ec90111367ec', 'ADMIN', array['AUTHORIZATION', 'USER_EDIT', 'USER_VIEW'], 1),
      ('5467e079-c77a-4e31-b908-cd2313eee8ac', 'CLIENT', array['REGISTRATION', 'SELF_REGISTRATION'], 1),
      ('081fa992-64de-47b4-9173-d8dd910c9d3e', 'BANK_CLIENT', array['REGISTRATION'], 1),
      ('341e7a40-cb4a-4c64-a3e5-6efdc17ef364', 'REGISTERED_CLIENT', array['AUTHORIZATION', 'USER_EDIT', 'USER_VIEW'], 1);

-- ADMIN
-- password = Qwerty1!
INSERT INTO clients(id, mobile_phone, bank_client, password, registered, role_id, version)
VALUES('0d3a68a1-5919-4914-bc20-839fae2480ac', '9077777777', TRUE,
       '$2a$10$HP6mfnFyTOocyxVEFEFckuf4h9RxpwOKDoNLP/RKKwFnf/LQPzXTO', TRUE, '199790f4-1c8a-4aa7-ad9d-ec90111367ec', 1);

-- CLIENT
-- password = Qwerty1!
INSERT INTO clients(id, mobile_phone, bank_client, resident, password, passport_number, first_name, last_name,
                    security_question, security_answer, role_id, verification_code_id, version)
VALUES('67c52684-1070-42a8-826e-672463d0919a', '9012345678', TRUE, TRUE,
       '$2a$10$HP6mfnFyTOocyxVEFEFckuf4h9RxpwOKDoNLP/RKKwFnf/LQPzXTO', '0000123456', 'name', 'lastName', 'question', 'answer',
       '5467e079-c77a-4e31-b908-cd2313eee8ac', '82cd73ab-4355-466f-bd88-3d648358fd24', 1);

-- VERIFICATION_CODE
INSERT INTO verification_codes(id, client_id, verification_code, next_request_date_time, version)
VALUES('82cd73ab-4355-466f-bd88-3d648358fd24', '67c52684-1070-42a8-826e-672463d0919a', '123456', '2022-05-13 12:00:00.000000', 1);

-- RULES
INSERT INTO rules(id, rule_type, text, actual)
VALUES('ba153b7c-57e5-4bcb-b87f-bed64654e84d', 'PRIVACY_POLICY',
       'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at leo ultrices, tempor mi mollis, varius ex. ' ||
       'Etiam et magna tellus. Donec sagittis non nisl a mollis. Sed a justo sollicitudin, viverra leo vel, suscipit urna. ' ||
       'Etiam fringilla ex eu finibus fringilla. Nulla faucibus tellus in augue consequat, ac consequat nibh ultricies. ' ||
       'Duis leo urna, sodales ut nunc quis, tincidunt placerat nibh. Donec non ultrices massa, gravida sollicitudin nulla. ' ||
       'Fusce at sem maximus, facilisis sem in, ullamcorper mauris. Praesent luctus dui sapien, ' ||
       'elementum semper orci ullamcorper sit amet.', TRUE),
      ('d5d254c4-d56f-4bcb-b86a-8f6028197c9a', 'RBSS',
       'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at leo ultrices, tempor mi mollis, varius ex. ' ||
       'Etiam et magna tellus. Donec sagittis non nisl a mollis. Sed a justo sollicitudin, viverra leo vel, suscipit urna. ' ||
       'Etiam fringilla ex eu finibus fringilla. Nulla faucibus tellus in augue consequat, ac consequat nibh ultricies. ' ||
       'Duis leo urna, sodales ut nunc quis, tincidunt placerat nibh. Donec non ultrices massa, gravida sollicitudin nulla. ' ||
       'Fusce at sem maximus, facilisis sem in, ullamcorper mauris. Praesent luctus dui sapien, ' ||
       'elementum semper orci ullamcorper sit amet.', TRUE);