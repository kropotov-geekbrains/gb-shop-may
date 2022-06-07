insert into authority (permission)
values ('product.create'),
       ('product.read'),
       ('product.update'),
       ('product.delete');

insert into account_role(name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into role_authority(ROLE_ID, AUTHORITY_ID)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 2);


select * from account_user;