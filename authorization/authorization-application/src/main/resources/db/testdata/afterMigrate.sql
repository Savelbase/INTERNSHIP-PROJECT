delete from roles where id is not null;

insert into roles(id, name, authorities, version)
values('199790f4-1c8a-4aa7-ad9d-ec90111367ec', 'ADMIN', array['AUTHORIZATION', 'USER_EDIT', 'USER_VIEW',
       'UPLOAD_IMAGE', 'VIEW_IMAGE', 'CREDIT_EDIT', 'CREDIT_VIEW', 'EDIT_CREDIT_ORDER_STATUS', 'DELETE_USER',
       'CARD_EDIT', 'CARD_VIEW', 'APPROVE_BANK_CLIENT', 'EDIT_CARD_ORDER_STATUS', 'DEPOSIT_VIEW'], 1),
      ('5467e079-c77a-4e31-b908-cd2313eee8ac', 'CLIENT', array['REGISTRATION', 'SELF_REGISTRATION'], 1),
      ('081fa992-64de-47b4-9173-d8dd910c9d3e', 'BANK_CLIENT', array['REGISTRATION'], 1),
      ('341e7a40-cb4a-4c64-a3e5-6efdc17ef364', 'REGISTERED_CLIENT', array['AUTHORIZATION', 'USER_EDIT', 'USER_VIEW',
       'UPLOAD_IMAGE', 'VIEW_IMAGE', 'CREDIT_EDIT', 'CREDIT_VIEW', 'CARD_EDIT', 'CARD_VIEW', 'DEPOSIT_VIEW'], 1);

-- ADMIN
-- password = Qwerty1!
-- pin_code = 777777
-- USERS
insert into users(id, mobile_phone, password, pin_code, role_id, version)
values('0d3a68a1-5919-4914-bc20-839fae2480ac', '9077777777', '$2a$10$HP6mfnFyTOocyxVEFEFckuf4h9RxpwOKDoNLP/RKKwFnf/LQPzXTO',
       '$2a$10$fz4Ime6UTtfZM5b30olxzOy77FTnCqarwdLrpIz.J77bk9PHZb.Uq', '199790f4-1c8a-4aa7-ad9d-ec90111367ec', 1),
      ('bc9efe3b-a8a0-45f4-b57e-0415f5d0c676', '9211111111', '$2a$10$aREYTt82JAyjy99eiYU1MuLGUMszpfmvtguvwOQTACKHzts7VSl7C',
       '$2a$10$a2GaO5yx5OfvakA21gvcauOFhZyLidlnsc4I4DWmefE.gmBITqjX2', '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', 1),
      ('23543d9c-2a68-4bc5-b5c2-1fe975a37b1f', '9633333333', '$2a$10$HnSn5wsqVP6k0j5nbpgtIORYP4CvXcNTk8RaK1IKyV8vI9WJTN5VO',
       '$2a$10$BAW9zHEYuTE6fVup8NvqducoR.jjrC83w/vsuOBx2MvGu0LI22Joe', '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', 1),
      ('f943bf1a-e0d5-4388-bedd-e672903c9d71', '9096666666', '$2a$10$gEl4YFT/yZnFzPVKGCaFEOwoPxoFmaHgFlz20AHzQ6gDNGV9W3yra',
       '$2a$10$WbSfHW74ag9rv5vemgYImOqc5Wkd9x/lBvkpsBB2BhmJYWaTLX/82', '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', 1),
      ('50c7efde-abf8-4b93-be82-582188a8b400', '9999999999', '$2a$10$6Rs8j9thErA7CJX5u1XnxeasSffcmlP0zwrdxJyEKZxdEfH8FcGRq',
       '$2a$10$Tf1ywZZRJmNYO/YhVDmmGutMjzJA9A/8jbC9cMlq3PX2QFIjAlkz2', '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', 1),
      ('cb96c109-feac-4451-8fa7-b349ec73a021', '9278888888', '$2a$10$xrCBpOcZnoCW4Koeh4RH7OVqMJ35gLvtBa38zGaEvJ/srQiCar.4S',
       '$2a$10$ctpNNuGnj43UByI1MT7rMOmLvz.QYwNPo6aganwIMJhzEyrqRFAi6', '341e7a40-cb4a-4c64-a3e5-6efdc17ef364', 1);