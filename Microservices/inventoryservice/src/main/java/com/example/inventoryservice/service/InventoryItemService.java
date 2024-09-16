package com.example.inventoryservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventoryservice.dto.InventoryItemDto;
import com.example.inventoryservice.entity.InventoryItem;
import com.example.inventoryservice.repository.InventoryRepository;

@Service
public class InventoryItemService {

	@Autowired
	private InventoryRepository inventoryRepository;
	

	public List<InventoryItemDto> getAllItems() {
		List<InventoryItem> findAll = inventoryRepository.findAll();
		return buildInventoryItemDtoList(findAll);
	}

	private List<InventoryItemDto> buildInventoryItemDtoList(List<InventoryItem> inventoryItemList) {
		List<InventoryItemDto> inventoryItemDtos = new ArrayList<>();
		for (InventoryItem inventoryItem : inventoryItemList) {
			InventoryItemDto dto = buildInventoryItemDto(inventoryItem);
			inventoryItemDtos.add(dto);
		}
		return inventoryItemDtos;
	}

	private InventoryItemDto buildInventoryItemDto(InventoryItem inventoryItem) {
		InventoryItemDto inventoryItemDto = new InventoryItemDto();
		inventoryItemDto.setId(inventoryItem.getId());
		inventoryItemDto.setName(inventoryItem.getName());
		inventoryItemDto.setQuantity(inventoryItem.getQuantity());
		inventoryItemDto.setPrice(inventoryItem.getPrice());
		return inventoryItemDto;
	}

	public void storeInventoryItem(InventoryItemDto inventoryItemDto) {
		InventoryItem inventoryItem = buildInventoryItem(inventoryItemDto);
		inventoryRepository.save(inventoryItem);
	}

	private InventoryItem buildInventoryItem(InventoryItemDto inventoryItemDto) {
		return new InventoryItem(inventoryItemDto.getName(), inventoryItemDto.getQuantity(),
				inventoryItemDto.getPrice());
	}

	public InventoryItemDto getItemById(Long id) throws Exception {
		Optional<InventoryItem> findById = inventoryRepository.findById(id);
		if (findById.isPresent()) {
			InventoryItemDto dto = buildInventoryItemDto(findById.get());
			return dto;
		} else {
			throw new Exception("item not found");
		}
	}

}
