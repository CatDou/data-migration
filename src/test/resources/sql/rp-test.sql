@@ DROP DATABASE IF EXISTS {db_name};
@@ CREATE DATABASE {db_name} charset=utf8mb4;
@@ USE {db_name};

DROP PROCEDURE if exists insert1;

DELIMITER ;;
CREATE DEFINER=`chengdu`@`localhost` PROCEDURE `insert1`()
begin
declare i int;
set i = 1;
while i < 10 do
insert into testdata(foo, bar) values(concat('chengdu',i), i);
set i = i + 1;
end while;
end ;;
DELIMITER ;

@@ DROP TABLE IF EXISTS {tb_name};
@@ CREATE TABLE {tb_name} (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `foo` varchar(25) DEFAULT NULL,
  `bar` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;