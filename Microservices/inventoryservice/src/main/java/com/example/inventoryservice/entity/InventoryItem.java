package com.example.inventoryservice.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "inventory_items")
@NoArgsConstructor
@JacksonXmlRootElement(localName = "inventory")
public class InventoryItem {
	
	public InventoryItem(String name, Integer quantity, Double price) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private Double price;
	
}
