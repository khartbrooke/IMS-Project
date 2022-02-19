package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
	
	/*@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}*/
	
	@Mock
	private Utils utils;

	@Mock
	private OrderDAO dao;
	
	@Mock
	private CustomerDAO customerDAO;
	
	@Mock
	private ItemDAO itemDAO;

	@InjectMocks
	private OrderController controller;

	@Test
	public void testCreate() {
		final Customer CUST = new Customer(1L, "Jordan", "Harrison", "64 Zoo Lane", "ZP11 4LS", "jordanharrison@gmail.com");
		final Item item1 = new Item(2L, "Pathfinder: Wrath of the Righteous", 39.99);
		final Item item2 = new Item(3L, "Crusader Kings 3", 39.99);
		final List<Item> ITEMS = new ArrayList<>();
		ITEMS.add(item1);
		ITEMS.add(item2);
		final Order created = new Order(CUST, ITEMS);

		Mockito.when(utils.getLong()).thenReturn(CUST.getId(), item1.getId(), item2.getId());
		Mockito.when(customerDAO.read(CUST.getId())).thenReturn(CUST);
		Mockito.when(itemDAO.read(item1.getId())).thenReturn(item1);
		Mockito.when(itemDAO.read(item2.getId())).thenReturn(item2);
		Mockito.when(utils.getString()).thenReturn("y", "invalid", "n");
		Mockito.when(dao.create(created)).thenReturn(created);

		assertEquals(created, controller.create());
 

		Mockito.verify(utils, Mockito.times(3)).getLong();
		Mockito.verify(customerDAO, Mockito.times(1)).read(CUST.getId());
		Mockito.verify(itemDAO, Mockito.times(1)).read(item1.getId());
		Mockito.verify(itemDAO, Mockito.times(1)).read(item2.getId());
		Mockito.verify(utils, Mockito.times(3)).getString();
		Mockito.verify(dao, Mockito.times(1)).create(created);
	}

	@Test
	public void testReadAll() {
		List<Order> orders = new ArrayList<>();
		Customer jordan = new Customer(1L, "Jordan", "Harrison", "64 Zoo Lane", "ZP11 4LS", "jordanharrison@gmail.com");
		List<Item> items = new ArrayList<>();
		items.add(new Item(1L, "Total War: Warhammer 3", 49.99));
		
		orders.add(new Order(1L, jordan, items));

		Mockito.when(dao.readAll()).thenReturn(orders);

		assertEquals(orders, controller.readAll());

		Mockito.verify(dao, Mockito.times(1)).readAll();
	}

	@Test
	public void testUpdate() {
		Customer jordan = new Customer(1L, "Jordan", "Harrison", "64 Zoo Lane", "ZP11 4LS", "jordanharrison@gmail.com");
		List<Item> itemsOld = new ArrayList<>();
		List<Item> itemsNew = new ArrayList<>();
		Item warhammer = new Item(1L, "Total War: Warhammer 3", 49.99);
		Item pathfinder = new Item(2L, "Pathfinder: Wrath of the Righteous", 49.99);
		itemsOld.add(warhammer);
		itemsNew.add(pathfinder);
		
		Order old = new Order(1L, jordan, itemsOld);
		Order updated = new Order(1L, jordan, itemsNew);

		Mockito.when(this.utils.getLong()).thenReturn(1L, 2L, 1L);
		Mockito.when(this.dao.read(updated.getId())).thenReturn(old);		
		Mockito.when(this.utils.getString()).thenReturn("add", "remove", "no");
		Mockito.when(this.itemDAO.read(2L)).thenReturn(pathfinder);
		Mockito.when(this.itemDAO.read(1L)).thenReturn(warhammer);
		Mockito.when(this.dao.update(updated)).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.utils, Mockito.times(3)).getLong();
		Mockito.verify(this.dao, Mockito.times(1)).read(updated.getId());
		Mockito.verify(this.utils, Mockito.times(3)).getString();
		Mockito.verify(this.itemDAO, Mockito.times(1)).read(2L);
        Mockito.verify(this.itemDAO, Mockito.times(1)).read(1L);
		Mockito.verify(this.dao, Mockito.times(1)).update(updated);
	}

	@Test
	public void testDelete() {
		final long ID = 1L;

		Mockito.when(utils.getLong()).thenReturn(ID);
		Mockito.when(dao.delete(ID)).thenReturn(1);

		assertEquals(1L, this.controller.delete());

		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).delete(ID);
	}

}
