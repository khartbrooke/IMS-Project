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

SELECT o.`id`, c.`first_name`, c.`surname`, i.`name`
FROM `orders` o 
JOIN `customers` c ON c.`id` = o.`fk_cust_id`
JOIN `order_contents` oc ON oc.`fk_order_id` = o.`id`
JOIN `items` i ON i.`id` = oc.`fk_item_id`;

SELECT o.`id`, c.`first_name`, c.`surname`, GROUP_CONCAT(i.`name`) AS contents
FROM `orders` o 
JOIN `customers` c ON c.`id` = o.`fk_cust_id`
JOIN `order_contents` oc ON oc.`fk_order_id` = o.`id`
JOIN `items` i ON i.`id` = oc.`fk_item_id`
GROUP BY o.`id`;

SELECT o.`id`, o.`fk_cust_id`, c.`first_name`, c.`surname`, c.`address`, c.`postcode`, c.`email`
FROM `orders` o 
JOIN `customers` c ON c.`id` = o.`fk_cust_id`;

SELECT oc.fk_order_id, oc.fk_item_id, i.name, i.price
FROM order_contents oc
JOIN items i ON i.id = oc.fk_item_id; 