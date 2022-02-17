package com.qa.ims.controller;

import com.qa.ims.persistence.domain.Order;

public interface CostController extends CrudController<Order>{
	
    double cost();
	
}