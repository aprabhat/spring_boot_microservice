package com.example.inventoryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventoryservice.dto.InventoryItemDto;
import com.example.inventoryservice.exception.ItemNotFoundException;
import com.example.inventoryservice.service.InventoryItemService;

@RestController
public class InventoryController {

	// CRUD --

	@Autowired
	private InventoryItemService inventoryItemService;

	@GetMapping("/items")
	public List<InventoryItemDto> getAllItems() {
		return inventoryItemService.getAllItems();

	}

	@PostMapping("/items")
	public void storeItem(@RequestBody InventoryItemDto inventoryItemDto) {
		inventoryItemService.storeInventoryItem(inventoryItemDto);
	}

	@GetMapping("/items/{id}")
	public InventoryItemDto getItemById(@PathVariable("id") Long id) {
		try {
			return inventoryItemService.getItemById(id);
		} catch (Exception e) {
			
			return null;
		}

	}

}
