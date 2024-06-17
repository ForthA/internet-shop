insert into roles(id, role) values(1, 'USER');
insert into roles(id, role) values(2, 'ADMIN');

insert into Category(id, father_id, title) VALUES (1, null, 'Смартфоны');
insert into Category(id, father_id, title) VALUES (2, 1, 'Apple');
insert into Category(id, father_id, title) VALUES (3, 1, 'Samsung');
insert into Category(id, father_id, title) VALUES (4, 1, 'Сопутствующие товары');
insert into Category(id, father_id, title) VALUES (5, 4,'Наушники');
insert into Category(id, father_id, title) VALUES (6, 4,'Чехлы');
insert into Category(id, father_id, title) VALUES (7, null,'Аудиотехника');
insert into Category(id, father_id, title) VALUES (8, 7 , 'Портативные колонки');


insert into Product(title, description, image_url, amount, price, discount, category_id) VALUES ('Смартфон Apple iPhone 14', 'pass', 'pass', 1, 50000, 30, 2);
insert into Product(title, description, image_url, amount, price, discount, category_id) VALUES ('Смартфон Apple iPhone 13', 'pass', 'pass', 6, 35000, 20, 2);
insert into Product(title, description, image_url, amount, price, discount, category_id) VALUES ('Смартфон Samsung Galaxy S8', 'pass', 'pass', 0, 40000, 10, 3);
insert into Product(title, description, image_url, amount, price, discount, category_id) VALUES ('Наушники Apple AirPods Pro', 'pass', 'pass', 3, 10000, 5, 5);
insert into Product(title, description, image_url, amount, price, discount, category_id) VALUES ('Чеход для Huawei P50', 'pass', 'pass', 50, 5000, 0, 6);
insert into Product(title, description, image_url, amount, price, discount, category_id) VALUES ('Смартфон Huawei P50', 'pass', 'pass', 3, 50000, 50, 4);
insert into Product(title, description, image_url, amount, price, discount, category_id) VALUES ('Умная колонка Яндекс Станция', 'pass', 'pass', 0, 50000, 3, 8);