package com.qa.ims.persistence.domain;

import java.util.List;

public class Order {
    
	private Long id;
	private Customer cust;
	private List<Item> items;
	
	public Order(Customer cust, List<Item> items) {
		this.setCust(cust);
		this.setItems(items);
	}
	
	public Order(Long id, Customer cust, List<Item> items) {
		this.setId(id);
		this.setCust(cust);
		this.setItems(items);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Customer getCust() {
		return cust;
	}
	
	public void setCust(Customer cust) {
		this.cust = cust;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	private String itemsToString(List<Item> items) {
		String str = "";
		for (int i = 0; i < items.size(); i++) {
			str = str + items.get(i).getName() + ", price:" + items.get(i).getPrice() + "\r\n";
		}
		return null;
	}

	@Override
	public String toString() {
		return "id:" + id + " name:" + cust.getFirstName() + " " + cust.getSurname() + "\r\n items:\r\n" + itemsToString(items);
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cust == null) ? 0 : cust.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (getCust() == null) {
			if (other.getCust() != null)
				return false;
		} else if (!getCust().equals(other.getCust()))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;		
		return true;
	}
}
