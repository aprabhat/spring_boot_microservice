package com.example.inventoryservice.exception;

public class InsufficientStockException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5958311330451929615L;

	public InsufficientStockException(String message) {
        super(message);
    }
}