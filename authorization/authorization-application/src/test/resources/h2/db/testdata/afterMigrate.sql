INSERT INTO roles(id, name, authorities, version)
VALUES ('341e7a40-cb4a-4c64-a3e5-6efdc17ef364', 'REGISTERED_CLIENT', ARRAY['AUTHORIZATION', 'USER_EDIT'], 1);

-- USER
-- password = Qwerty1!
-- pin_code = 123456
INSERT INTO users(id, mobile_phone, password, pin_code, role_id, version)
VALUES('67c52684-1070-42a8-826e-672463d0919a', '9012345678', '$2a$10$HP6mfnFyTOocyxVEFEFckuf4h9RxpwOKDoNLP/RKKwFnf/LQPzXTO',
       '$2a$10$vlEsgkeqYyzYia3f6By.EuTlZ0loUz2qAuFrOKyB9K11WbLUPd9mG', '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', 1);
