package com.hibicode.beerstore.service;

import org.springframework.stereotype.Service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;

@Service
public class BeerService {
	
	public void save(Beer beer) {
		throw new BeerAlreadyExistException();
	}

}
