package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.DBUtils;

public class CustomerDAOTest {

	private final CustomerDAO DAO = new CustomerDAO();

	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@Test
	public void testCreate() {
		final Customer created = new Customer(3L, "Chris", "Perrins", "40 Elm Street", "LN66 2NP", "chrisperrins@gmail.com");
		assertEquals(created, DAO.create(created));
	}

	@Test
	public void testReadAll() {
		List<Customer> expected = new ArrayList<>();
		expected.add(new Customer(1L, "Jordan", "Harrison", "64 Zoo Lane", "ZP11 4LS", "jordanharrison@gmail.com"));
		expected.add(new Customer(2L, "Hazel", "Smith", "3 Wood Road", "WF10 3QR", "hazelsmith@gmail.com"));
		assertEquals(expected, DAO.readAll());
	}

	@Test
	public void testReadLatest() {
		assertEquals(new Customer(2L, "Hazel", "Smith", "3 Wood Road", "WF10 3QR", "hazelsmith@gmail.com"), DAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		assertEquals(new Customer(ID, "Jordan", "Harrison", "64 Zoo Lane", "ZP11 4LS", "jordanharrison@gmail.com"), DAO.read(ID));
	}

	@Test
	public void testUpdate() {
		final Customer updated = new Customer(1L, "chris", "perrins", "40 Elm Street", "LN66 2NP", "chrisperrins@gmail.com");
		assertEquals(updated, DAO.update(updated));

	}

	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
}
