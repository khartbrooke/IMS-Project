package com.qa.ims.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order>{
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private OrderDAO orderDAO;
	private CustomerDAO customerDAO = new CustomerDAO();
	private ItemDAO itemDAO = new ItemDAO();
	private Utils utils;

	public OrderController(OrderDAO orderDAO, Utils utils) {
		super();
		this.orderDAO = orderDAO;
		this.utils = utils;
	}
	
	/**
	 * Reads all items to the logger
	 */
	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		for (Order order : orders) {
			LOGGER.info(order);
		}
		return orders;
	}
	
	/**
	 * Creates an item by taking in user input
	 */
	@Override
	public Order create() {
		LOGGER.info("Please enter a customer ID");
		Long custId = utils.getLong();
		Customer cust = customerDAO.read(custId);
		List<Item> items = new ArrayList<>();
		LOGGER.info("Please enter an item ID");
		Long itemID = utils.getLong();
		Item item = itemDAO.read(itemID);
		items.add(item);
		LOGGER.info("Do you wish to add another item? (y/n)");
		String response = utils.getString();
		while (!response.equals("n")) {
			if (response.equals("y")) {
				LOGGER.info("Please enter an item ID");
				itemID = utils.getLong();
				item = itemDAO.read(itemID);
				items.add(item);				
			} else {
				LOGGER.info("Invallid response");
			}
			LOGGER.info("Do you wish to add another item? (y/n)");
			response = utils.getString();
		}
		Order order = orderDAO.create(new Order(cust, items));
		LOGGER.info("Order created");
		return order;
	}
	
	/**
	 * Updates an existing order by taking in user input
	 */
	@Override
	public Order update() {
		/*LOGGER.info("Please enter the id of the item you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter a name");
		String name = utils.getString();
		LOGGER.info("Please enter a price");
		Double price = utils.getDouble();
		Item item = itemDAO.update(new Item(id, name, price));
		LOGGER.info("Item Updated");*/
		return null;
	}
	
	/**
	 * Deletes an existing order by the id of the order
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id);
	}

}
