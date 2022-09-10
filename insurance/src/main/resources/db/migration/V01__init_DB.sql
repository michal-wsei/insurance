create table authority
(
    ID   serial primary key,
    name varchar(32) unique
);

insert into authority(name)
values ('AGENT'),
       ('CLIENT');

create table user_account
(
    ID                  serial primary key,
    name                varchar,
    email               varchar unique,
    username            varchar(16) unique,
    password            varchar(255),
    account_expired     boolean,
    account_locked      boolean,
    credentials_expired boolean,
    enabled             boolean
);

create table user_authority
(
    ID           serial primary key,
    USER_ID      int references user_account (ID),
    AUTHORITY_ID int references authority (ID)
);

create table insurance
(
    ID            serial primary key,
    name          varchar(255) not null,
    description   text         not null,
    created_date  timestamp    not null,
    modified_date timestamp
);

alter table user_account drop column username;

alter table insurance add column owner_id int not null;
alter table insurance add foreign key (owner_id) references user_account(id);

create table enrollments
(
    ID            serial primary key,
    insurance_id     int       not null,
    user_id       int       not null,
    foreign key (insurance_id) references insurance (id),
    foreign key (user_id) references user_account (id)
);

create table variant
(
                        ID serial primary key,
                        insurance_id integer references insurance(ID),
                        order_index integer,
                        title varchar(255),
                        content text
);