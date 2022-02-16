package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order>{
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		
		Long custId = resultSet.getLong("fk_cust_id");
		String firstName = resultSet.getString("first_name");
		String surname = resultSet.getString("surname");
		String address = resultSet.getString("address");
		String postcode = resultSet.getString("postcode");
		String email = resultSet.getString("email");
		Customer cust = new Customer(custId, firstName, surname, address, postcode, email);
		
		List<Item> items = null;
		
		return new Order(id, cust, items);
	}
	
	public Item createItemsList(ResultSet itemResults) throws SQLException {
		Long itemId = itemResults.getLong("fk_item_id");
		String itemName = itemResults.getString("name");
		Double price = itemResults.getDouble("price");
		return new Item(itemId, itemName, price);
	}
	
	/**
	 * Reads all orders from the database
	 * 
	 * @return A list of orders
	 */
	@Override
	public List<Order> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT o.id, o.fk_cust_id, c.first_name, c.surname, c.address, c.postcode, c.email\r\n"
						+ "FROM orders o \r\n"
						+ "JOIN customers c ON c.id = o.fk_cust_id");
				ResultSet itemResults = statement.executeQuery("SELECT oc.fk_order_id, oc.fk_item_id, i.name, i.price\r\n"
						+ "FROM order_contents oc\r\n"
						+ "JOIN items i ON i.id = oc.fk_item_id\r\n"
						+ "ORDER BY oc.fk_order_id ASC");) {
			List<Order> orders = new ArrayList<>();
			List<Item> items = new ArrayList<>();
			while (resultSet.next()) {
				orders.add(modelFromResultSet(resultSet));
			}
			
			long id = 1L;
			while (itemResults.next()) {
				if (itemResults.getLong("fk_order_id") != id) {
					orders.get((int) (id - 1)).setItems(items);
				}
				items.add(createItemsList(itemResults));
				id = itemResults.getLong("fk_order_id");
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	public Order readLatest() {
		Order result;
		List<Item> items = new ArrayList<>();
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT o.id, o.fk_cust_id, c.first_name, c.surname, c.address, c.postcode, c.email\r\n"
						+ "FROM orders o \r\n"
						+ "JOIN customers c ON c.id = o.fk_cust_id\r\n"
						+ "ORDER BY o.id DESC;");
				ResultSet itemResults = statement.executeQuery("SELECT oc.fk_order_id, oc.fk_item_id, i.name, i.price\r\n"
						+ "FROM order_contents oc\r\n"
						+ "JOIN items i ON i.id = oc.fk_item_id\r\n"
						+ "ORDER BY oc.fk_order_id DESC");) {
			resultSet.next();
			result = modelFromResultSet(resultSet);
			long id = itemResults.getLong("fk_order_id");			
			while (id == itemResults.getLong("fk_order_id")) {
				items.add(createItemsList(itemResults));
			}
			result.setItems(items);
			return result;
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Creates an order in the database
	 * 
	 * @param order - takes in an order object. id will be ignored
	 */
	@Override
	public Order create(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO items(name, price) VALUES (?, ?)");) {
			statement.setString(1, item.getName());
			statement.setDouble(2, item.getPrice());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

}
