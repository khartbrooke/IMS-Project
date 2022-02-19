package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAOTest {
	
	private final OrderDAO DAO = new OrderDAO();

	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@Test
	public void testCreate() {
		final Customer cust = new Customer(1L, "Jordan", "Harrison", "64 Zoo Lane", "ZP11 4LS", "jordanharrison@gmail.com");
		final Item item = new Item(2L, "Pathfinder: Wrath of the Righteous", 39.99);
		final List<Item> items = new ArrayList<>();
		items.add(item);
		final Order created = new Order(4L, cust, items);
		assertEquals(created, DAO.create(created));
	}

	@Test
	public void testReadAll() {
		List<Order> expected = new ArrayList<>();
		Customer jordan = new Customer(1L, "Jordan", "Harrison", "64 Zoo Lane", "ZP11 4LS", "jordanharrison@gmail.com");
		Customer hazel = new Customer(2L, "Hazel", "Smith", "3 Wood Road", "WF10 3QR", "hazelsmith@gmail.com");
		List<Item> order1 = new ArrayList<>();
		List<Item> order2 = new ArrayList<>();
		List<Item> order3 = new ArrayList<>();
		Item warhammer = new Item(1L, "Total War: Warhammer 3", 49.99);
		Item pathfinder = new Item(2L, "Pathfinder: Wrath of the Righteous", 39.99);
		Item ck3 = new Item(3L, "Crusader Kings 3", 39.99);
		
		order1.add(warhammer);
		order2.add(warhammer);
		order2.add(pathfinder);
		order3.add(ck3);
		expected.add(new Order(1L, jordan, order1));
		expected.add(new Order(2L, hazel, order2));
		expected.add(new Order(3L, jordan, order3));
		assertEquals(expected, DAO.readAll());
	}

	@Test
	public void testReadLatest() {
		Customer jordan = new Customer(1L, "Jordan", "Harrison", "64 Zoo Lane", "ZP11 4LS", "jordanharrison@gmail.com");
		List<Item> order3 = new ArrayList<>();
		Item ck3 = new Item(3L, "Crusader Kings 3", 39.99);
		order3.add(ck3);
		assertEquals(new Order(3L, jordan, order3), DAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		Customer jordan = new Customer(1L, "Jordan", "Harrison", "64 Zoo Lane", "ZP11 4LS", "jordanharrison@gmail.com");
		List<Item> order1 = new ArrayList<>();
		Item warhammer = new Item(1L, "Total War: Warhammer 3", 49.99);
		order1.add(warhammer);
		assertEquals(new Order(1L, jordan, order1), DAO.read(ID));
	}

	@Test
	public void testUpdate() {
		final Customer cust = new Customer(1L, "Jordan", "Harrison", "64 Zoo Lane", "ZP11 4LS", "jordanharrison@gmail.com");
		final Item item = new Item(2L, "Pathfinder: Wrath of the Righteous", 39.99);
		final List<Item> items = new ArrayList<>();
		items.add(item);
		final Order updated = new Order(1L, cust, items);
		assertEquals(updated, DAO.update(updated));

	}

	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}

}
