
INSERT INTO users (id, name, email, password) VALUES
(1, 'Admin',  'admin@mail.ru', ''),
(2, 'Ivan',   'ivan@mail.ru', ''),
(3, 'Petr',   'petr@mail.ru', ''),
(4, 'Irina',  'irina@ya.ru', ''),
(5, 'John',  'john@gmail.com', ''),
(6, 'Julia',  'julia@gmail.com', '');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_ADMIN',  1),
('ROLE_USER',   2),
('ROLE_USER',   3),
('ROLE_USER',   4),
('ROLE_USER',   5),
('ROLE_USER',   6);

INSERT INTO restaurants (id, name) VALUES
(1, 'Interim'),
(2, 'Cera 23'),
(3, 'La Mi Venta Restaurant'),
(4, 'El Celler de Can Roca'),
(5, 'Restaurante Romesco');

INSERT INTO dishes (id, name, date, price, rest_id) VALUES
(1, 'Jamón de Bellota 100% Ibérico', '2019-01-03', 23.90, 1),
(2, 'Una de Veneno', '2019-01-03', 13.50, 1),
(3, 'Tabla de Quesos Puro de Oveja', '2019-01-03', 9.50, 1),
(4, 'Lomo de Bellota Ibérico', '2019-01-04', 13.90, 1),
(5, 'Migas Manchegas', '2019-01-04', 9.50, 1),
(6, 'Chorizo de Pincho al Infierno', '2019-01-04', 17.50, 1),
(7, 'Anchoas Especiales del Cantábrico', now(), 17.50, 1),
(8, 'Croquetas Caseras de Jamón de Bellota', now(), 14.70, 1),
(9, 'Pisto Típico Manchego', now(), 18.50, 1),
(10, 'Langostinos en tempura con salsa de mango al mirín', '2019-01-03', 9.90, 2),
(11, 'Burratina de búfala, rúcula ', '2019-01-03', 13.90, 2),
(12, 'Ceviche de corvina, cebolla morada de Figueres', '2019-01-03', 14.90, 2),
(13, 'Risotto de ceps y trufa', '2019-01-04', 21.50, 2),
(14, 'Raviolis con foie en salsa cremosa de almendras y membrillo', '2019-01-04', 15.90, 2),
(15, 'Pasta fresca con shiitake, chistorra y gambas a la naranja ', '2019-01-04', 17.30, 2),
(16, 'Nuestra cheesecake ', now(), 14.90, 2),
(17, 'La torrija del veí', now(), 11.90, 2),
(18, 'Mousse de mojito de mora', now(), 7.80, 2),
(19, 'Lacón gallego confitado en ajada con cachelos, anacardo y carbonara de berros ', '2019-01-03', 17.80, 3),
(20, 'Carpaccio de presa ibérica con tomate, almendras y mayonesa de kimchi ', '2019-01-03', 13.80, 3),
(21, 'Tartar de atún con frutos rojos y crujiente de wanton ', '2019-01-03',17.40, 3),
(22, 'Degustación de los tres tartares de la casa', '2019-01-04', 11.30, 3),
(23, 'Steak tartar trufado ', '2019-01-04', 15.40, 3),
(24, 'Tartar de tomate cor de bou con alga wakame, anacardos y espuma de mango ', '2019-01-04', 10.80, 3),
(25, 'Tataki de solomillo de buey, ensalada de algas, setas enoki y aguacate ', now(), 12.50, 3),
(26, 'Cecina de León con piñones, foie y dulce de membrillo ', now(), 16.80, 3),
(27, 'Crema del día ', now(), 16.40, 3),
(29, 'Corvina en papillote con langostino, mejillón y salsa Thai', '2019-01-03', 12.40, 4),
(30, 'Cordero cocinado a baja temperatura con cuscús de tomate semiseco ', '2019-01-03', 11.40, 4),
(31, 'Guisito de verduras al curry con leche de coco ', '2019-01-03', 14.60, 4),
(32, 'Arroz negro y marisco con salsa de azafrán y bonito deshidratado ', '2019-01-04', 18.40, 4),
(33, 'Dados de atún al sésamo con puré dde boniato y miel de romero ', '2019-01-04', 22.80, 4),
(34, 'Meloso de ternera con parmentier de trufa ', '2019-01-04', 23.40, 4),
(35, 'Pluma ibérica con puré suave de ajo y cebollitas de Módena ', now(), 16.90, 4),
(36, 'Suprema de Merluza a la Plancha con Verduritas Tiernas Salteadas en Ajoaceite ', now(), 9.10, 4),
(37, 'Lomo Noble de Bacalao en Tempura con Parmentier Trufada y Toques de Salicornia', now(), 6.00, 4),
(38, 'Chipirones a la Plancha sobre Cebolla Caramelizada y Alioli Negro', '2019-01-03', 10.40, 5),
(39, ' Corvina al Horno en Reducción de Caldo Corto de Jamón, Compota de Tomate en Reducción ', '2019-01-03', 14.50, 5),
(40, 'Hamburguesa de Vaca con Trufa en Pan de Cebolla con Patatas Fritas ', '2019-01-03', 12.30, 5),
(41, 'Secreto ó Presa de Bellota 100% Ibérico a la Parrilla de Carbón. ', '2019-01-04', 19.10, 5),
(42, 'Entrecote de Lomo de Vaca a la Parrilla de Carbón', '2019-01-04', 20.70, 5),
(43, 'Sorbete de Gin Tonic con Fresas Silvestres en Almíbar', '2019-01-04', 11.40, 5),
(44, 'Sopa de fruta de la Pasión con Mango, Toques de Albahaca y Helado de Yogur ', now(), 13.60, 5),
(45, 'Brownie Templado de Chocolate Blanco y Helado de Vainilla ', now(), 16.40, 5),
(46, 'Chipirones a la Plancha sobre Cebolla Caramelizada y Alioli Negro ', now(), 17.80, 5);

INSERT INTO vote (id, user_id, restaurant_id, date) VALUES
(1, 2, 1, '2019-01-03' ),
(2, 3, 5, '2019-01-03'),
(3, 4, 3, '2019-01-03'),
(4, 5, 5, '2019-01-03'),
(5, 6, 4, '2019-01-03'),
(6, 2, 1, '2019-01-04'),
(7, 3, 3, '2019-01-04'),
(8, 4, 3, '2019-01-04'),
(9, 5, 5, '2019-01-04'),
(10, 6, 4, '2019-01-04'),
(11, 2, 1, now()),
(12, 3, 1, now()),
(13, 4, 3, now()),
(14, 5, 5, now()),
(15, 6, 4, now());



