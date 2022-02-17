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

public class OrderDAO implements Dao<Order> {

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

		List<Item> items = new ArrayList<>();

		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet itemResults = statement.executeQuery(
						"SELECT oc.fk_order_id, oc.fk_item_id, i.name, i.price "
						+ "FROM order_contents oc "
						+ "JOIN items i ON i.id = oc.fk_item_id");) {
			while (itemResults.next()) {
				if (id == itemResults.getLong("fk_order_id")) {
					Long itemId = itemResults.getLong("fk_item_id");
					String itemName = itemResults.getString("name");
					Double price = itemResults.getDouble("price");
					Item item = new Item(itemId, itemName, price);
					items.add(item);
				}
			}
		}
		return new Order(id, cust, items);
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
				ResultSet resultSet = statement.executeQuery("SELECT o.id, o.fk_cust_id, c.first_name, c.surname, c.address, c.postcode, c.email "
						+ "FROM orders o "
						+ "JOIN customers c ON c.id = o.fk_cust_id");) {
			List<Order> orders = new ArrayList<>();
			while (resultSet.next()) {
			    orders.add(modelFromResultSet(resultSet));
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
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(
						"SELECT o.id, o.fk_cust_id, c.first_name, c.surname, c.address, c.postcode, c.email "
						+ "FROM orders o "
						+ "JOIN customers c ON c.id = o.fk_cust_id "
						+ "ORDER BY o.id DESC LIMIT 1");) {
			resultSet.next();
			result = modelFromResultSet(resultSet);			
			return result;
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public Long readLatestOrderId() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return resultSet.getLong("id");
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
						.prepareStatement("INSERT INTO orders(fk_cust_id) VALUES (?)");) {
			Customer cust = order.getCust();
			statement.setLong(1, cust.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		List<Item> items = order.getItems();
		for (int i = 0; i < items.size(); i++) {
			try (Connection connection = DBUtils.getInstance().getConnection();
					PreparedStatement statement = connection
							.prepareStatement("INSERT INTO order_contents(fk_order_id, fk_item_id) VALUES (?, ?)");) {
				Long id = readLatestOrderId();
				Long itemId = items.get(i).getId();
				statement.setLong(1, id);
				statement.setLong(2, itemId);
				statement.executeUpdate();
				if (i + 1 == items.size()) {
					return readLatest();
				}
			} catch (Exception e) {
				LOGGER.debug(e);
				LOGGER.error(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public Order read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT o.id, o.fk_cust_id, c.first_name, c.surname, c.address, c.postcode, c.email "
						+ "FROM orders o "
						+ "JOIN customers c ON c.id = o.fk_cust_id "
						+ "WHERE o.id = ?");) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Updates an order in the database
	 * 
	 * @param order - takes in an order object, the id field will be used to
	 *                 update that order in the database
	 * @return
	 */
	@Override
	public Order update(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("DELETE FROM order_contents WHERE fk_order_id = ?");) {
			statement.setLong(1, order.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		List<Item> items = order.getItems();
		for (int i = 0; i < items.size(); i++) {			
		    try (Connection connection = DBUtils.getInstance().getConnection();
			    	PreparedStatement statement = connection
						    .prepareStatement("INSERT INTO order_contents(fk_order_id, fk_item_id) VALUES (?, ?)");) {
			    statement.setLong(1, order.getId());
			    Item item = items.get(i);
			    statement.setLong(2, item.getId());
			    statement.executeUpdate();
			    if (i+1 == items.size()) {
			    	return read(order.getId());
			    }			    
		    } catch (Exception e) {
			    LOGGER.debug(e);
			    LOGGER.error(e.getMessage());
		    }
		}
		return null;
	}
	
	/**
	 * Deletes an order in the database
	 * 
	 * @param id - id of the order
	 */
	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement1 = connection.prepareStatement("DELETE FROM order_contents WHERE fk_order_id = ?");
				PreparedStatement statement2 = connection.prepareStatement("DELETE FROM orders WHERE id = ?");) {
			statement1.setLong(1, id);
			statement2.setLong(1, id);
			statement1.executeUpdate();
			return statement2.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}
	
	public double cost(long id) {
		Order order = read(id);
		List<Item> items = order.getItems();
		double result = 0;
		for (Item item : items) {
			result = result + item.getPrice();
		}
		return result;
	}

}
