package com.hibicode.beerstore.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;

public class BeerServiceTest {

	private BeerService beerServ;

	@Mock
	private Beers beersMocked;

	@Before
	public void setUp() {
		
		MockitoAnnotations.initMocks(this);
		beerServ = new BeerService(beersMocked);

	}

	@Test(expected = BeerAlreadyExistException.class)
	public void should_deny_creation_of_beer_that_exists() {

		Beer beerInDataBase = new Beer();
		beerInDataBase.setId(10L);
		beerInDataBase.setName("Heineken");
		beerInDataBase.setType(BeerType.LARGER);
		beerInDataBase.setVolume(new BigDecimal("350"));

		when(beersMocked.findByNameAndType("Heineken", BeerType.LARGER)).thenReturn(Optional.of(beerInDataBase));

		Beer newBeer = new Beer();
		newBeer.setName("Heineken");
		newBeer.setType(BeerType.LARGER);
		newBeer.setVolume(new BigDecimal("350"));

		beerServ.save(newBeer);

	}

	@Test
	public void should_create_new_beer() {

		Beer newBeerInDataBase = new Beer();
		newBeerInDataBase.setId(10L);
		newBeerInDataBase.setName("Heineken");
		newBeerInDataBase.setType(BeerType.LARGER);
		newBeerInDataBase.setVolume(new BigDecimal("350"));
		
		Beer newBeer = new Beer();
		newBeer.setName("Heineken");
		newBeer.setType(BeerType.LARGER);
		newBeer.setVolume(new BigDecimal("350"));

		when(beersMocked.save(newBeer)).thenReturn(newBeerInDataBase);
		
		Beer beerSaved = beerServ.save(newBeer);

		assertThat(beerSaved.getId(), equalTo(10L));
		assertThat(beerSaved.getName(), equalTo("Heineken"));
		assertThat(beerSaved.getType(), equalTo(BeerType.LARGER));

	}

}
