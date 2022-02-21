INSERT INTO `ims`.`customers` (`first_name`, `surname`, `address`, `postcode`, `email`) VALUES ('Jordan', 'Harrison', '64 Zoo Lane', 'ZP11 4LS', 'jordanharrison@gmail.com');
INSERT INTO `ims`.`customers` (`first_name`, `surname`, `address`, `postcode`, `email`) VALUES ('Hazel', 'Smith', '3 Wood Road', 'WF10 3QR', 'hazelsmith@gmail.com');

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

