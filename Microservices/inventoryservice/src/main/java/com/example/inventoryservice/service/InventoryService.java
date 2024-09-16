package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.InventoryItem;
import com.example.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // Get all inventory items
    public List<InventoryItem> getAllItems() {
        return inventoryRepository.findAll();
    }

    // Get a specific inventory item by ID
    public Optional<InventoryItem> getItemById(Long itemId) {
        return inventoryRepository.findById(itemId);
    }

    // Check if an item is available in the required quantity
    public boolean isItemAvailable(Long itemId, int requiredQuantity) {
        Optional<InventoryItem> item = inventoryRepository.findById(itemId);
        return item.map(inventoryItem -> inventoryItem.getQuantity() >= requiredQuantity).orElse(false);
    }

    // Reserve an item by reducing the available quantity
    public void reserveItem(Long itemId, int quantity) {
        Optional<InventoryItem> item = inventoryRepository.findById(itemId);
        if (item.isPresent()) {
            InventoryItem inventoryItem = item.get();
            if (inventoryItem.getQuantity() >= quantity) {
                inventoryItem.setQuantity(inventoryItem.getQuantity() - quantity);
                inventoryRepository.save(inventoryItem);
            } else {
                throw new RuntimeException("Insufficient stock for item ID: " + itemId);
            }
        } else {
            throw new RuntimeException("Item not found: " + itemId);
        }
    }

    // Deduct the final inventory after the order is completed
    public void deductItem(Long itemId, int quantity) {
        Optional<InventoryItem> item = inventoryRepository.findById(itemId);
        if (item.isPresent()) {
            InventoryItem inventoryItem = item.get();
            inventoryItem.setQuantity(inventoryItem.getQuantity() - quantity);
            inventoryRepository.save(inventoryItem);
        } else {
            throw new RuntimeException("Item not found: " + itemId);
        }
    }

    // Release a reserved item (cancel reservation)
    public void releaseItem(Long itemId, int quantity) {
        Optional<InventoryItem> item = inventoryRepository.findById(itemId);
        if (item.isPresent()) {
            InventoryItem inventoryItem = item.get();
            inventoryItem.setQuantity(inventoryItem.getQuantity() + quantity);
            inventoryRepository.save(inventoryItem);
        } else {
            throw new RuntimeException("Item not found: " + itemId);
        }
    }
}
