CREATE DATABASE IF NOT EXISTS onlinestore;
GRANT ALL PRIVILEGES ON onlinestore.* TO os@localhost IDENTIFIED BY 'os';

USE onlinestore;

CREATE TABLE IF NOT EXISTS discount (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  item_id INT(4) UNSIGNED NOT NULL,
  discount INT(4) UNSIGNED NOT NULL
) engine=InnoDB;
