insert into users (name, username, password)
values ('John Doe', 'jd@gmail.com', '$2a$10$RIbF3ixYIkZHk01.ZhSNgOXb2pq2owH9Gzp1IRHIsG5FN8LP/PXF.'),
       ('Mike Smith', 'ms@gmail.com', '$2a$12$nd00bD8QT7rrDjbem/gyeOk4G2xaIlAQP4ALVCrNHCNNSL5c9hMjy');

insert into tasks (title, description, status, expiration_date)
values ('Buy cheese', null, 'TODO', '2023-10-13 12:00:00'),
       ('Do homework', 'Math, Physics, Literature', 'IN_PROGRESS', '2023-10-15 11:00:00'),
       ('Clean Rooms', null, 'DONE', null),
       ('Call Mike', 'Ask about meeting', 'TODO', '2023-10-25 11:00:00');

insert into users_roles (user_id, role)
values (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');

insert into users_tasks (user_id, task_id)
values (1, 4),
       (2, 1),
       (2, 2),
       (2, 3);