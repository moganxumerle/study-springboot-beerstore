package com.hibicode.beerstore.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;

public class BeerServiceTest {

	private BeerService beerServ;

	@Before
	public void setUp() {

		beerServ = new BeerService();

	}

	@Test(expected = BeerAlreadyExistException.class)
	public void should_deny_creation_of_beer_that_exists() {

		Beer newBeer = new Beer();
		newBeer.setName("Heineken");
		newBeer.setType(BeerType.LARGER);
		newBeer.setVolume(new BigDecimal("350"));

		beerServ.save(newBeer);

	}

	@Test
	public void should_create_new_beer() {

		Beer newBeer = new Beer();
		newBeer.setName("Heineken");
		newBeer.setType(BeerType.LARGER);
		newBeer.setVolume(new BigDecimal("350"));

		Beer beerSaved = beerServ.save(newBeer);
		
		assertThat(beerSaved.getId(), equalTo(10L));
		assertThat(beerSaved.getName(), equalTo("Heineken"));
		assertThat(beerSaved.getType(), equalTo(BeerType.LARGER));
		
	}

}
