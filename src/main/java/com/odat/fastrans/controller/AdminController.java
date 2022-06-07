package com.odat.fastrans.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odat.fastrans.entity.Account;
import com.odat.fastrans.entity.Address;
import com.odat.fastrans.entity.City;
import com.odat.fastrans.entity.Town;
import com.odat.fastrans.entity.Village;
import com.odat.fastrans.repo.AccountRepo;
import com.odat.fastrans.repo.AddressRepo;
import com.odat.fastrans.repo.CityRepo;
import com.odat.fastrans.repo.TownRepo;
import com.odat.fastrans.repo.VillageRepo;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
private final VillageRepo villageRepo;
private final TownRepo townRepo;
private final CityRepo cityRepo;
private final AddressRepo addressRepo;
private final AccountRepo accountRepo;
	public AdminController(AccountRepo accountRepo,AddressRepo addressRepo,
			CityRepo cityRepo,TownRepo townRepo,VillageRepo villageRepo) {
		super();
		this.villageRepo = villageRepo;
		this.townRepo = townRepo;
		this.cityRepo=cityRepo;
		this.addressRepo=addressRepo;
		this.accountRepo=accountRepo;
		 
	}
	@PostMapping("/villages")
	public ResponseEntity<Void> postVillages(@RequestBody List<Village> villages){
		villageRepo.saveAll(villages);
		return new ResponseEntity<Void>( HttpStatus.CREATED );
	}
	@PostMapping("/addresses")
	public ResponseEntity<Void> postAddresses(@RequestBody List<Address> addresses){
		addressRepo.saveAll(addresses);
		return new ResponseEntity<Void>( HttpStatus.CREATED );
	}
	@PostMapping("/cities")
	public ResponseEntity<Void> postCities(@RequestBody List<City> cities){
		for(City city: cities) {
			Town.assignCities(city);
		}
		cityRepo.saveAll(cities);
		return new ResponseEntity<Void>( HttpStatus.CREATED );
	}

	@PostMapping("/towns")
	public ResponseEntity<Void> postTownٌs(@RequestBody List<Town> towns){
		
		for(Town town :towns) {
			 Village.assignTowns(town);
		}
		townRepo.saveAll(towns);
		return new ResponseEntity<Void>( HttpStatus.CREATED );
	}
}
