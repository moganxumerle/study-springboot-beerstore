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
import com.hibicode.beerstore.service.exception.BeerNotFoundException;

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
		beerInDataBase.setType(BeerType.LAGER);
		beerInDataBase.setVolume(new BigDecimal("350"));

		when(beersMocked.findByNameAndType("Heineken", BeerType.LAGER)).thenReturn(Optional.of(beerInDataBase));

		Beer newBeer = new Beer();
		newBeer.setName("Heineken");
		newBeer.setType(BeerType.LAGER);
		newBeer.setVolume(new BigDecimal("350"));

		beerServ.save(newBeer);

	}

	@Test
	public void should_create_new_beer() {

		Beer newBeerInDataBase = new Beer();
		newBeerInDataBase.setId(10L);
		newBeerInDataBase.setName("Heineken");
		newBeerInDataBase.setType(BeerType.LAGER);
		newBeerInDataBase.setVolume(new BigDecimal("350"));

		Beer newBeer = new Beer();
		newBeer.setName("Heineken");
		newBeer.setType(BeerType.LAGER);
		newBeer.setVolume(new BigDecimal("350"));

		when(beersMocked.save(newBeer)).thenReturn(newBeerInDataBase);

		Beer beerSaved = beerServ.save(newBeer);

		assertThat(beerSaved.getId(), equalTo(10L));
		assertThat(beerSaved.getName(), equalTo("Heineken"));
		assertThat(beerSaved.getType(), equalTo(BeerType.LAGER));

	}

	@Test
	public void should_update_beer() {

		final Beer beerInDatabase = new Beer();
		beerInDatabase.setId(10L);
		beerInDatabase.setName("Devassa");
		beerInDatabase.setType(BeerType.PILSEN);
		beerInDatabase.setVolume(new BigDecimal("300"));

		when(beersMocked.findByNameAndType("Devassa", BeerType.PILSEN)).thenReturn(Optional.of(beerInDatabase));

		final Beer beerToUpdate = new Beer();
		beerToUpdate.setId(10L);
		beerToUpdate.setName("Devassa");
		beerToUpdate.setType(BeerType.PILSEN);
		beerToUpdate.setVolume(new BigDecimal("200"));

		final Beer beerMocked = new Beer();
		beerMocked.setId(10L);
		beerMocked.setName("Devassa");
		beerMocked.setType(BeerType.PILSEN);
		beerMocked.setVolume(new BigDecimal("200"));

		when(beersMocked.save(beerToUpdate)).thenReturn(beerMocked);

		final Beer beerUpdated = beerServ.save(beerToUpdate);
		assertThat(beerUpdated.getId(), equalTo(10L));
		assertThat(beerUpdated.getName(), equalTo("Devassa"));
		assertThat(beerUpdated.getType(), equalTo(BeerType.PILSEN));
		assertThat(beerUpdated.getVolume(), equalTo(new BigDecimal("200")));

	}

	@Test(expected = BeerAlreadyExistException.class)
	public void should_deny_update_of_an_existing_beer_that_already_exists() {
		
		final Beer beerInDatabase = new Beer();
		beerInDatabase.setId(10L);
		beerInDatabase.setName("Heineken");
		beerInDatabase.setType(BeerType.LAGER);
		beerInDatabase.setVolume(new BigDecimal("355"));

		when(beersMocked.findByNameAndType("Heineken", BeerType.LAGER)).thenReturn(Optional.of(beerInDatabase));

		final Beer beerToUpdate = new Beer();
		beerToUpdate.setId(5L);
		beerToUpdate.setName("Heineken");
		beerToUpdate.setType(BeerType.LAGER);
		beerToUpdate.setVolume(new BigDecimal("355"));

		beerServ.save(beerToUpdate);
	}
	
	@Test(expected = BeerNotFoundException.class)
	public void should_deny_delete_an_beer_that_not_exists() {
		
		final Beer beerToDelete = new Beer();
		beerToDelete.setId(3L);
		beerToDelete.setName("Heineken");
		beerToDelete.setType(BeerType.LAGER);
		beerToDelete.setVolume(new BigDecimal("355"));
		
		when(beersMocked.findById(beerToDelete.getId())).thenReturn(Optional.empty());
		
		beerServ.delete(beerToDelete.getId());
		
	}
	
	@Test
	public void should_delete_an_beer_that_exists() {
		
		final Beer beerInDataBase = new Beer();
		beerInDataBase.setId(77L);
		beerInDataBase.setName("Heineken");
		beerInDataBase.setType(BeerType.LAGER);
		beerInDataBase.setVolume(new BigDecimal("355"));
		
		final Beer beerToDelete = new Beer();
		beerToDelete.setId(77L);
		beerToDelete.setName("Heineken");
		beerToDelete.setType(BeerType.LAGER);
		beerToDelete.setVolume(new BigDecimal("355"));
		
		when(beersMocked.findById(beerToDelete.getId())).thenReturn(Optional.of(beerInDataBase));
		
		beerServ.delete(77L);
		
	}

}
