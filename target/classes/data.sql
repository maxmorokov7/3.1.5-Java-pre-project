DROP TABLE IF EXISTS user, role, user_role;


CREATE TABLE user
(
    user_id  int auto_increment
        primary key,
    name     varchar(100) null,
    password varchar(255) null,
    surname  varchar(255) null
);

CREATE TABLE role
(
    role_id int auto_increment
        primary key,
    name    varchar(255) null
);

CREATE TABLE user_role
(
    user_user_id int not null,
    role_role_id int not null,
    constraint user_id
        foreign key (user_user_id) references user (user_id),
    constraint role_id
        foreign key (role_role_id) references role (role_id)
);

INSERT INTO User (name, surname, password, user_id) VALUES ('user', 'surnameU', '$2a$10$stD/OrooG.6DTqj0avOK6eJtTn8P6DALFxkIwEL7PkCaHOugPJLTe', 1);
INSERT INTO User (name, surname, password, user_id) VALUES ('admin', 'surnameA', '$2a$10$FF2mrqYAaRcZkTEToCFdQ.7j/uyzmFGmbCO8/HMS.oDdDMw2Mv6OO', 2);

INSERT INTO Role (name, role_id) VALUES ('USER', 1);
INSERT INTO Role (name, role_id) VALUES ('ADMIN', 2);

INSERT INTO User_role (user_user_id, role_role_id) VALUES (2, 2);
INSERT INTO User_role (user_user_id, role_role_id) VALUES (2, 1);
INSERT INTO User_role (user_user_id, role_role_id) VALUES (1, 1);