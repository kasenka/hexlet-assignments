-- BEGIN
drop table if exists products;

CREATE table products(
    id int primary key auto_increment,
    title varchar(255) not null,
    price int not null
);
-- END
