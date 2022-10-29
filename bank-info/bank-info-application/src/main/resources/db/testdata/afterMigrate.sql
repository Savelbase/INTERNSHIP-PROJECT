delete from department where id is not null;
delete from service where id is not null;
delete from department_service where id is not null;

insert into service (id , name , type) values
('3dab3c06-0842-11ed-861d-0242ac120002' , 'DEPOSIT' , 'PRIMARY' ),
('42167b84-0842-11ed-861d-0242ac120002' , 'DEPOSIT_WITHOUT_CARD' , 'PRIMARY' ),
('486d093a-0842-11ed-861d-0242ac120002' , 'CURRENCY_EXCHANGE' , 'PRIMARY' ),
('4c4f3794-0842-11ed-861d-0242ac120002' , 'WITHDRAW' , 'PRIMARY' ),
('509a924e-0842-11ed-861d-0242ac120002' , 'TRANSFER' , 'PRIMARY' ),
('5630ba26-0842-11ed-861d-0242ac120002' , 'PAY' , 'PRIMARY' ),
('5b00cd5c-0842-11ed-861d-0242ac120002' , 'CONSULTATION' , 'ADDITIONAL' ),
('60cb24e4-0842-11ed-861d-0242ac120002' , 'RAMP' , 'ADDITIONAL' ),
('65dd8580-0842-11ed-861d-0242ac120002' , 'EXOTIC_CURRENCY_EXCHANGE' , 'ADDITIONAL' ),
('6a13eac2-0842-11ed-861d-0242ac120002' , 'INSURANCE' , 'ADDITIONAL' );

insert into department (id , address , city , name , schedule , status , type , coordinates , zone_id , schedule_type) values
        ('67e1988f-3c97-46ec-93f2-85659a465d6d',
         'Большой Патриарший пер., д.6, строение 1' ,'Москва', '19',
         '[{"to": [18, 0], "day": "MONDAY", "from": [8, 0]},
         {"to": [18, 0], "day": "TUESDAY", "from": [8, 0]},
         {"to": [18, 0], "day": "WEDNESDAY", "from": [8, 0]},
         {"to": [18, 0], "day": "THURSDAY", "from": [8, 0]},
         {"to": [18, 0], "day": "FRIDAY", "from": [8, 0]},
         {"to": [16, 0], "day": "SATURDAY", "from": [9, 0]},
         {"to": [0, 0], "day": "SUNDAY", "from": [0, 0]}]'  ,
         'OPEN' , 'DEPARTMENT' , '55.76274595194946, 37.59216363286942', 'Europe/Moscow' , 'WEEKEND'),
        ('73a52614-03ce-11ed-b939-0242ac120002' ,
         'Новинский бул., 28/35. 1' ,'Москва', '5' ,
         '[{"to": [23, 59 , 59], "day": "MONDAY", "from": [0, 0]},
         {"to": [23, 59 , 59], "day": "TUESDAY", "from": [0, 0]},
         {"to": [23, 59 , 59], "day": "WEDNESDAY", "from": [0, 0]},
         {"to": [23, 59 , 59], "day": "THURSDAY", "from": [0, 0]},
         {"to": [23, 59 , 59], "day": "FRIDAY", "from": [0, 0]},
         {"to": [23, 59 , 59], "day": "SATURDAY", "from": [0, 0]},
         {"to": [23, 59 , 59], "day": "SUNDAY", "from": [0, 0]}]' ,
         'OPEN' , 'TERMINAL' , '55.75750846394198, 37.58592267890689' , 'Europe/Moscow' , 'DAY_AND_NIGHT') ,
        ('1da1c458-d081-42b1-86e7-4c19be28a6bf' ,
         'ул, Новый Арбат ул., д. 8' ,'Москва', '5' ,
         '[{"to": [22, 0], "day": "MONDAY", "from": [8, 0]},
         {"to": [22, 0], "day": "TUESDAY", "from": [8, 0]},
         {"to": [22, 0], "day": "WEDNESDAY", "from": [8, 0]},
         {"to": [22, 0], "day": "THURSDAY", "from": [8, 0]},
         {"to": [22, 0], "day": "FRIDAY", "from": [8, 0]},
         {"to": [19, 0], "day": "SATURDAY", "from": [9, 0]},
         {"to": [16, 0], "day": "SUNDAY", "from": [9, 0]}]'
         , 'OPEN' , 'ATM' ,'55.753111449634815, 37.595288019174','Europe/Moscow' , 'WEEKEND') ,
        ('12a1c458-d081-42b1-86e7-4c19be28a6bf' ,
         'Тверская ул., 18, корпус 1' ,'Москва', '5' ,
         '[{"to": [22, 0], "day": "MONDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "TUESDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "WEDNESDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "THURSDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "FRIDAY", "from": [8, 0]},
           {"to": [19, 0], "day": "SATURDAY", "from": [9, 0]},
           {"to": [16, 0], "day": "SUNDAY", "from": [9, 0]}]'
            , 'OPEN' , 'DEPARTMENT' , '55.766204564347746, 37.60398005762791','Europe/Moscow' , 'WEEKEND') ,
        ('fb784afa-03cc-11ed-b939-0242ac120002' ,
         'пр-т Мира, 182' ,'Москва', '15' ,
         '[{"to": [17, 0], "day": "MONDAY", "from": [10, 0]},
           {"to": [17, 0], "day": "TUESDAY", "from": [14, 0]},
           {"to": [17, 0], "day": "WEDNESDAY", "from": [14, 0]},
           {"to": [18, 0], "day": "THURSDAY", "from": [8, 0]},
           {"to": [18, 0], "day": "FRIDAY", "from": [8, 0]},
           {"to": [0, 0], "day": "SATURDAY", "from": [0, 0]},
           {"to": [0, 0], "day": "SUNDAY", "from": [0, 0]}]'
            , 'OPEN' , 'DEPARTMENT' , '55.82862734378789, 37.64816531996076','Europe/Moscow',null )  ,
        ('64782426-03cd-11ed-b939-0242ac120002' ,
         'ул. Хачатуряна, 20' ,'Москва', '32' ,
         '[{"to": [22, 0], "day": "MONDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "TUESDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "WEDNESDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "THURSDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "FRIDAY", "from": [8, 0]},
           {"to": [19, 0], "day": "SATURDAY", "from": [9, 0]},
           {"to": [16, 0], "day": "SUNDAY", "from": [9, 0]}]'
            , 'OPEN' , 'DEPARTMENT' , '55.828048837157624, 37.647821997215964','Europe/Moscow' , 'WEEKEND') ,
        ('9038ffe0-03cd-11ed-b939-0242ac120002' ,
         'Ленинградское ш., 25' ,'Москва', '21' ,
         '[{"to": [22, 0], "day": "MONDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "TUESDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "WEDNESDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "THURSDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "FRIDAY", "from": [8, 0]},
           {"to": [19, 0], "day": "SATURDAY", "from": [9, 0]},
           {"to": [16, 0], "day": "SUNDAY", "from": [9, 0]}]'
            , 'OPEN' , 'DEPARTMENT' , '55.83659568789193, 37.487739017007975','Europe/Moscow' , 'WEEKEND') ,
        ('d1d115be-03cd-11ed-b939-0242ac120002' ,
         'б-р Яна Райниса, 2 к1' ,'Москва', '5' ,
         '[{"to": [0, 0], "day": "MONDAY", "from": [0, 0]},
           {"to": [18, 0], "day": "TUESDAY", "from": [10, 0]},
           {"to": [18, 0], "day": "WEDNESDAY", "from": [11, 0]},
           {"to": [18, 0], "day": "THURSDAY", "from": [11, 0]},
           {"to": [0, 0], "day": "FRIDAY", "from": [0, 0]},
           {"to": [0, 0], "day": "SATURDAY", "from": [0, 0]},
           {"to": [0, 0], "day": "SUNDAY", "from": [0, 0]}]'
            , 'OPEN' , 'DEPARTMENT' , '55.85972415857351, 37.43486731430947','Europe/Moscow' , null),
        ('ef24e693-0b88-4b4b-92c1-4aedec2ab805' ,
         'Славянский б-р, д. 3' ,'Москва', '26' ,
         '[{"to": [22, 0], "day": "MONDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "TUESDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "WEDNESDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "THURSDAY", "from": [8, 0]},
           {"to": [22, 0], "day": "FRIDAY", "from": [8, 0]},
           {"to": [19, 0], "day": "SATURDAY", "from": [9, 0]},
           {"to": [16, 0], "day": "SUNDAY", "from": [8, 0]}]'
            , 'OPEN' , 'DEPARTMENT' , '55.734287064894716, 37.47282027456616','Europe/Moscow' , 'WEEKEND');

insert into department_service (id , service_id , department_id ) values
('7533c584-0843-11ed-861d-0242ac120002' , '3dab3c06-0842-11ed-861d-0242ac120002' , '67e1988f-3c97-46ec-93f2-85659a465d6d'),
('7adb5376-0843-11ed-861d-0242ac120002' , '42167b84-0842-11ed-861d-0242ac120002' , '67e1988f-3c97-46ec-93f2-85659a465d6d'),
('7f3137ec-0843-11ed-861d-0242ac120002' , '486d093a-0842-11ed-861d-0242ac120002' , '67e1988f-3c97-46ec-93f2-85659a465d6d'),
('8360fc62-0843-11ed-861d-0242ac120002' , '4c4f3794-0842-11ed-861d-0242ac120002' , '67e1988f-3c97-46ec-93f2-85659a465d6d'),
('8af3f768-0843-11ed-861d-0242ac120002' , '509a924e-0842-11ed-861d-0242ac120002' , '67e1988f-3c97-46ec-93f2-85659a465d6d'),
('8fa9ba18-0843-11ed-861d-0242ac120002' , '5630ba26-0842-11ed-861d-0242ac120002' , '67e1988f-3c97-46ec-93f2-85659a465d6d'),
('9780f710-0843-11ed-861d-0242ac120002' , '5b00cd5c-0842-11ed-861d-0242ac120002' , '67e1988f-3c97-46ec-93f2-85659a465d6d'),
('9cf22e8a-0843-11ed-861d-0242ac120002' , '60cb24e4-0842-11ed-861d-0242ac120002' , '67e1988f-3c97-46ec-93f2-85659a465d6d'),
('a219439e-0843-11ed-861d-0242ac120002' , '65dd8580-0842-11ed-861d-0242ac120002' , '67e1988f-3c97-46ec-93f2-85659a465d6d'),
('a672e7e2-0843-11ed-861d-0242ac120002' , '6a13eac2-0842-11ed-861d-0242ac120002' , '67e1988f-3c97-46ec-93f2-85659a465d6d'),

('aaf331be-0843-11ed-861d-0242ac120002' , '3dab3c06-0842-11ed-861d-0242ac120002' , 'ef24e693-0b88-4b4b-92c1-4aedec2ab805'),
('ae68644a-0843-11ed-861d-0242ac120002' , '42167b84-0842-11ed-861d-0242ac120002' , 'ef24e693-0b88-4b4b-92c1-4aedec2ab805'),
('b2b2b5f0-0843-11ed-861d-0242ac120002' , '486d093a-0842-11ed-861d-0242ac120002' , 'ef24e693-0b88-4b4b-92c1-4aedec2ab805'),
('b6d97cc2-0843-11ed-861d-0242ac120002' , '4c4f3794-0842-11ed-861d-0242ac120002' , 'ef24e693-0b88-4b4b-92c1-4aedec2ab805'),
('bd99a10e-0843-11ed-861d-0242ac120002' , '509a924e-0842-11ed-861d-0242ac120002' , 'ef24e693-0b88-4b4b-92c1-4aedec2ab805'),
('caf1b29c-0843-11ed-861d-0242ac120002' , '5630ba26-0842-11ed-861d-0242ac120002' , 'ef24e693-0b88-4b4b-92c1-4aedec2ab805'),
('cf6ec468-0843-11ed-861d-0242ac120002' , '5b00cd5c-0842-11ed-861d-0242ac120002' , 'ef24e693-0b88-4b4b-92c1-4aedec2ab805'),
('d64bcd08-0843-11ed-861d-0242ac120002' , '60cb24e4-0842-11ed-861d-0242ac120002' , 'ef24e693-0b88-4b4b-92c1-4aedec2ab805'),

('f1c2a954-0865-11ed-861d-0242ac120002' , '3dab3c06-0842-11ed-861d-0242ac120002' , '12a1c458-d081-42b1-86e7-4c19be28a6bf'),
('04dce87e-0866-11ed-861d-0242ac120002' , '486d093a-0842-11ed-861d-0242ac120002' , '12a1c458-d081-42b1-86e7-4c19be28a6bf'),
('08bc1dc0-0866-11ed-861d-0242ac120002' , '509a924e-0842-11ed-861d-0242ac120002' , '12a1c458-d081-42b1-86e7-4c19be28a6bf'),
('0bc9aece-0866-11ed-861d-0242ac120002' , '5630ba26-0842-11ed-861d-0242ac120002' , '12a1c458-d081-42b1-86e7-4c19be28a6bf'),
('0f26aab8-0866-11ed-861d-0242ac120002' , '5b00cd5c-0842-11ed-861d-0242ac120002' , '12a1c458-d081-42b1-86e7-4c19be28a6bf'),
('120bf5d0-0866-11ed-861d-0242ac120002' , '60cb24e4-0842-11ed-861d-0242ac120002' , '12a1c458-d081-42b1-86e7-4c19be28a6bf'),
('15f5b6ae-0866-11ed-861d-0242ac120002' , '6a13eac2-0842-11ed-861d-0242ac120002' , '12a1c458-d081-42b1-86e7-4c19be28a6bf'),

('487abc6e-0866-11ed-861d-0242ac120002' , '3dab3c06-0842-11ed-861d-0242ac120002' , 'fb784afa-03cc-11ed-b939-0242ac120002'),
('4b9d580c-0866-11ed-861d-0242ac120002' , '42167b84-0842-11ed-861d-0242ac120002' , 'fb784afa-03cc-11ed-b939-0242ac120002'),
('4e4fc15c-0866-11ed-861d-0242ac120002' , '486d093a-0842-11ed-861d-0242ac120002' , 'fb784afa-03cc-11ed-b939-0242ac120002'),
('51240410-0866-11ed-861d-0242ac120002' , '4c4f3794-0842-11ed-861d-0242ac120002' , 'fb784afa-03cc-11ed-b939-0242ac120002'),
('542b024e-0866-11ed-861d-0242ac120002' , '509a924e-0842-11ed-861d-0242ac120002' , 'fb784afa-03cc-11ed-b939-0242ac120002'),
('5735c686-0866-11ed-861d-0242ac120002' , '5630ba26-0842-11ed-861d-0242ac120002' , 'fb784afa-03cc-11ed-b939-0242ac120002'),
('5a465a5c-0866-11ed-861d-0242ac120002' , '5b00cd5c-0842-11ed-861d-0242ac120002' , 'fb784afa-03cc-11ed-b939-0242ac120002'),
('5d3ae3e0-0866-11ed-861d-0242ac120002' , '6a13eac2-0842-11ed-861d-0242ac120002' , 'fb784afa-03cc-11ed-b939-0242ac120002'),

('77be38fc-0866-11ed-861d-0242ac120002' , '3dab3c06-0842-11ed-861d-0242ac120002' , '64782426-03cd-11ed-b939-0242ac120002'),
('7e6b357e-0866-11ed-861d-0242ac120002' , '486d093a-0842-11ed-861d-0242ac120002' , '64782426-03cd-11ed-b939-0242ac120002'),
('88700cc0-0866-11ed-861d-0242ac120002' , '5630ba26-0842-11ed-861d-0242ac120002' , '64782426-03cd-11ed-b939-0242ac120002'),
('8b4bafe4-0866-11ed-861d-0242ac120002' , '5b00cd5c-0842-11ed-861d-0242ac120002' , '64782426-03cd-11ed-b939-0242ac120002'),
('90c47a3c-0866-11ed-861d-0242ac120002' , '65dd8580-0842-11ed-861d-0242ac120002' , '64782426-03cd-11ed-b939-0242ac120002'),
('942d9802-0866-11ed-861d-0242ac120002' , '6a13eac2-0842-11ed-861d-0242ac120002' , '64782426-03cd-11ed-b939-0242ac120002'),

('daf37af4-0866-11ed-861d-0242ac120002' , '3dab3c06-0842-11ed-861d-0242ac120002' , '9038ffe0-03cd-11ed-b939-0242ac120002'),
('de1414d2-0866-11ed-861d-0242ac120002' , '42167b84-0842-11ed-861d-0242ac120002' , '9038ffe0-03cd-11ed-b939-0242ac120002'),
('e177d046-0866-11ed-861d-0242ac120002' , '486d093a-0842-11ed-861d-0242ac120002' , '9038ffe0-03cd-11ed-b939-0242ac120002'),
('e3e197f4-0866-11ed-861d-0242ac120002' , '4c4f3794-0842-11ed-861d-0242ac120002' , '9038ffe0-03cd-11ed-b939-0242ac120002'),
('e6749fac-0866-11ed-861d-0242ac120002' , '509a924e-0842-11ed-861d-0242ac120002' , '9038ffe0-03cd-11ed-b939-0242ac120002'),
('e926fdf8-0866-11ed-861d-0242ac120002' , '5630ba26-0842-11ed-861d-0242ac120002' , '9038ffe0-03cd-11ed-b939-0242ac120002'),
('eba89686-0866-11ed-861d-0242ac120002' , '5b00cd5c-0842-11ed-861d-0242ac120002' , '9038ffe0-03cd-11ed-b939-0242ac120002'),
('ee8e6952-0866-11ed-861d-0242ac120002' , '60cb24e4-0842-11ed-861d-0242ac120002' , '9038ffe0-03cd-11ed-b939-0242ac120002'),
('f4c9d572-0866-11ed-861d-0242ac120002' , '65dd8580-0842-11ed-861d-0242ac120002' , '9038ffe0-03cd-11ed-b939-0242ac120002'),
('f7fd3270-0866-11ed-861d-0242ac120002' , '6a13eac2-0842-11ed-861d-0242ac120002' , '9038ffe0-03cd-11ed-b939-0242ac120002'),

('3a1f44cc-0867-11ed-861d-0242ac120002' , '3dab3c06-0842-11ed-861d-0242ac120002' , 'd1d115be-03cd-11ed-b939-0242ac120002'),
('3db61c82-0867-11ed-861d-0242ac120002' , '42167b84-0842-11ed-861d-0242ac120002' , 'd1d115be-03cd-11ed-b939-0242ac120002'),
('4083740a-0867-11ed-861d-0242ac120002' , '486d093a-0842-11ed-861d-0242ac120002' , 'd1d115be-03cd-11ed-b939-0242ac120002'),
('43a911c6-0867-11ed-861d-0242ac120002' , '4c4f3794-0842-11ed-861d-0242ac120002' , 'd1d115be-03cd-11ed-b939-0242ac120002'),
('4660a29e-0867-11ed-861d-0242ac120002' , '509a924e-0842-11ed-861d-0242ac120002' , 'd1d115be-03cd-11ed-b939-0242ac120002'),
('49b952c4-0867-11ed-861d-0242ac120002' , '5630ba26-0842-11ed-861d-0242ac120002' , 'd1d115be-03cd-11ed-b939-0242ac120002'),
('4cfe052e-0867-11ed-861d-0242ac120002' , '5b00cd5c-0842-11ed-861d-0242ac120002' , 'd1d115be-03cd-11ed-b939-0242ac120002'),
('51efe552-0867-11ed-861d-0242ac120002' , '60cb24e4-0842-11ed-861d-0242ac120002' , 'd1d115be-03cd-11ed-b939-0242ac120002'),
('5511291c-0867-11ed-861d-0242ac120002' , '65dd8580-0842-11ed-861d-0242ac120002' , 'd1d115be-03cd-11ed-b939-0242ac120002'),
('594e951e-0867-11ed-861d-0242ac120002' , '6a13eac2-0842-11ed-861d-0242ac120002' , 'd1d115be-03cd-11ed-b939-0242ac120002'),

('e7bd9dae-0867-11ed-861d-0242ac120002' , '486d093a-0842-11ed-861d-0242ac120002' , '73a52614-03ce-11ed-b939-0242ac120002'),
('ed11ee04-0867-11ed-861d-0242ac120002' , '509a924e-0842-11ed-861d-0242ac120002' , '73a52614-03ce-11ed-b939-0242ac120002'),
('f0eda7e8-0867-11ed-861d-0242ac120002' , '5630ba26-0842-11ed-861d-0242ac120002' , '73a52614-03ce-11ed-b939-0242ac120002'),

('5a41a0c8-0868-11ed-861d-0242ac120002' , '4c4f3794-0842-11ed-861d-0242ac120002' , '1da1c458-d081-42b1-86e7-4c19be28a6bf'),
('5d5e6976-0868-11ed-861d-0242ac120002' , '509a924e-0842-11ed-861d-0242ac120002' , '1da1c458-d081-42b1-86e7-4c19be28a6bf');


delete from contact where id is not null ;
insert into contact (id , contact , type) values
        ('d5a1ddd2-5f6b-4edf-ad33-8c6cd7db3d8b' , '3700', '"RUSSIAN"') ,
        ('0a004c11-c7ec-4d7c-806b-c55a7cfa45c1' , '+71326547891', '"INTERNATIONAL"')