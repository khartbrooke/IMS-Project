DROP TABLE IF EXISTS `order_contents`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `items`;

CREATE TABLE IF NOT EXISTS `customers` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,    
    `address` VARCHAR(200) DEFAULT NULL,
    `postcode` VARCHAR(12) DEFAULT NULL,
    `email` VARCHAR(200) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `items` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(200) DEFAULT NULL,
    `price` DOUBLE DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `orders` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `fk_cust_id` int(11) NOT NULL,
    FOREIGN KEY (`fk_cust_id`) REFERENCES `customers`(`id`),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `order_contents` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `fk_order_id` int NOT NULL,
    `fk_item_id` int NOT NULL,
    FOREIGN KEY (`fk_order_id`) REFERENCES `orders`(`id`),
    FOREIGN KEY (`fk_item_id`) REFERENCES `items`(`id`),
    PRIMARY KEY (`id`)
);