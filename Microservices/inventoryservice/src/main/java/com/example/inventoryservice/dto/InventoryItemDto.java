package com.example.inventoryservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryItemDto {
	
	public InventoryItemDto(String name, Integer quantity, Double price) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	private Long id;

	private String name;

	private Integer quantity;

	private Double price;
}