create database MealsDB;
use MealsDB;

CREATE TABLE users (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `datetime` datetime,
  `description` varchar(45) DEFAULT NULL,
  `calories` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
insert into users (datetime, description, calories)
values('2020-01-30 10:00', 'завтрак', 500 ), ('2020-01-30 13:00', 'обед', 1000 ),
('2020-01-30 20:00', 'ужин', 500 ),('2020-01-31 00:00', 'завтрак', 100 ),
('2020-01-31 10:00', 'завтрак', 1000 ),('2020-01-31 13:00', 'завтрак', 500 ),
('2020-01-31 20:00', 'завтрак', 410 );