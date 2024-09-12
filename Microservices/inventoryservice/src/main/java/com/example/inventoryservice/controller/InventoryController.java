package com.example.inventoryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventoryservice.entity.InventoryItem;
import com.example.inventoryservice.service.InventoryItemService;

@RestController
public class InventoryController {
	
	@Autowired
	private InventoryItemService inventoryItemService;
	
	
	@GetMapping("/items")
	public List<InventoryItem> getAllItems(){
		return inventoryItemService.getAllItems();
		
	}
}
