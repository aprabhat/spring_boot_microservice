package com.example.inventoryservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventoryservice.entity.InventoryItem;
import com.example.inventoryservice.repository.InventoryRepository;

@Service
public class InventoryItemService {
	
	@Autowired
	private InventoryRepository inventoryRepository;

	public List<InventoryItem> getAllItems() {
		List<InventoryItem> findAll = inventoryRepository.findAll();
	}

}
