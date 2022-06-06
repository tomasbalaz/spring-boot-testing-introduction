create sequence customer_sequence start with 1 increment by 50;
create sequence product_sequence start with 1 increment by 50;

create table customers
(
    id    bigint default nextval('customer_sequence') not null,
    email varchar(255)                                not null,
    name  varchar(255)                                not null,
    primary key (id)
);

create table products
(
    id          bigint default nextval('product_sequence') not null,
    name        varchar(255)                               not null,
    description varchar(255),
    price       numeric                                    not null,
    disabled    boolean default false,
    primary key (id)
);