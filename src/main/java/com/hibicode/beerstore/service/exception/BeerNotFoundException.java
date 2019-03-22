package com.hibicode.beerstore.service.exception;

import org.springframework.http.HttpStatus;

public class BeerNotFoundException extends BusinessException {

	public BeerNotFoundException() {
		super("beers-6", HttpStatus.BAD_REQUEST);
	}

}
