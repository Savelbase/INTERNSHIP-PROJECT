delete from clients where id is not null;

-- BANK CLIENTS
insert into clients(id, mobile_phone, passport_number, resident, first_name, last_name, middle_name,
                    security_question, security_answer, bank_client, version)
values ('c924ed32-683d-4c64-9e22-e7e2edea4f10', '2145244839', '8646015900', true,
        'Геннадий', 'Пахомов', 'Кириллович', 'Любимые цветы моей мамы?', 'Ромашки', true, 1),
       ('20bac7b4-def5-428e-b72a-980330917c13', '6805375879', '1845668480', true,
        'Нисон', 'Борисов', 'Юрьевич', 'Сколько лет моему питомцу? ', '3', true, 1),
       ('89c634b3-3170-48af-bd35-af4a306ce923', '6328767164', '3318213202', true,
        'Эмма', 'Маслова', 'Антоновна', 'Любимое блюдо?', 'Солянка', true, 1),
       ('d7a68805-5138-4d14-8e2a-79dbffd0006e', '4835462059', '9584674275', true,
        'Сабина', 'Фадеева', 'Дмитрьевна', 'Отчество моего первого учителя?', 'Викторовна', true, 1),
       ('8a89aa5c-54c0-4db5-8717-3a41d9403863', '0613499116', '0499920950', true,
        'Лунара', 'Семёнова', 'Игоревна', 'Мое любимое произведение?', 'Евгений Онегин', true, 1),
       ('08de67c4-f05a-4a23-8285-4e92f66e3894', '6621093043', 'MC7912935', false,
        'Katyusha', 'Orlova', 'Petrovna', 'In what city or town did your parents meet?', 'Minsk', true, 1),
       ('c08198a5-5276-42bf-8728-4098593cb82e', '0247481473', 'HB6324422', false,
        'Dalton', 'Velasquez', '', 'What was the make and model of your first car?', 'Toyota Camry', true, 1),
       ('a2ef41ef-b823-4675-8e74-731617ce33be', '7823044984', 'AB2873724', false,
        'Brandon', 'Walters', '', 'What was the first concert you attended?', 'Little Rock', true, 1),
       ('a5c06c6a-583c-4bd5-a344-541fe32de0d9', '9684332580', 'A1234567', false,
        'Mathew', 'Hendricks', '', 'What is your oldest sibling’s middle name?', 'Claire', true, 1),
       ('0d6cdd33-79ee-4514-9804-866ae1198def', '5349483468', 'ME789456', false,
        'Ashley', 'Allen', '', 'What city were you born in?', 'Madrid', true, 1);