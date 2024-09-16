package com.example.inventoryservice.exception;

public class ItemNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4292324572110982309L;

	public ItemNotFoundException(String message) {
        super(message);
    }
}
