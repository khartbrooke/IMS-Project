package com.qa.ims.controller;

import java.util.List;

public interface CostController<T> {
	
	List<T> readAll();

	T create();

	T update();

	int delete();
	
    double cost();
	
}