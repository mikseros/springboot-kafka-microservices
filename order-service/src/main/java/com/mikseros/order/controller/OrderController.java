package com.mikseros.order.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mikseros.domains.dto.Order;
import com.mikseros.domains.dto.OrderEvent;
import com.mikseros.order.kafka.OrderProducer;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
	
	private OrderProducer orderProducer;

	public OrderController(OrderProducer orderProducer) {
		super();
		this.orderProducer = orderProducer;
	}
	
	@PostMapping("/orders")
	public String placeOrder(@RequestBody Order order) {
		order.setOrderId(UUID.randomUUID().toString());
		OrderEvent orderEvent = new OrderEvent();
		orderEvent.setStatus("PENDING");
		orderEvent.setMessage("Order status is in pending state");
		orderEvent.setOrder(order);
		
		orderProducer.sendMessage(orderEvent);
		
		return "Order placed successfully...";
	}
}
