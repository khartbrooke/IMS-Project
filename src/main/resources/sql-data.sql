INSERT INTO `ims`.`customers` (`first_name`, `surname`, `address`, `postcode`, `email`) VALUES ('jordan', 'harrison', '40 cool street', 'ls42 0ps', 'jh@gmail.com');
INSERT INTO `ims`.`customers` (`first_name`, `surname`, `address`, `postcode`, `email`) VALUES ('hazel', 'smith', '3 wood lane', 'wf10 3qr', 'hs@gmail.com');

INSERT INTO `ims`.`items` (`name`, `price`) VALUES ('Total War: Warhammer 3', '49.99');
INSERT INTO `ims`.`items` (`name`, `price`) VALUES ('Pathfinder: Wrath of the Righteous', '39.99');
INSERT INTO `ims`.`items` (`name`, `price`) VALUES ('Crusader Kings 3', '39.99');

INSERT INTO `ims`.`orders` (`fk_cust_id`) VALUES ('1');
INSERT INTO `ims`.`orders` (`fk_cust_id`) VALUES ('2');
INSERT INTO `ims`.`orders` (`fk_cust_id`) VALUES ('1');

INSERT INTO `ims`.`order_contents` (`fk_order_id`, `fk_item_id`) VALUES ('1', '1');
INSERT INTO `ims`.`order_contents` (`fk_order_id`, `fk_item_id`) VALUES ('2', '1');
INSERT INTO `ims`.`order_contents` (`fk_order_id`, `fk_item_id`) VALUES ('2', '2');
INSERT INTO `ims`.`order_contents` (`fk_order_id`, `fk_item_id`) VALUES ('3', '3');