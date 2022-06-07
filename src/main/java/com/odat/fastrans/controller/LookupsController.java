package com.odat.fastrans.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.odat.fastrans.entity.Account;
import com.odat.fastrans.entity.Address;
import com.odat.fastrans.entity.City;
import com.odat.fastrans.entity.Dimension;
import com.odat.fastrans.entity.Product;
import com.odat.fastrans.entity.Town;
import com.odat.fastrans.entity.Village;
import com.odat.fastrans.repo.AccountRepo;
import com.odat.fastrans.repo.AddressRepo;
import com.odat.fastrans.repo.CityRepo;
import com.odat.fastrans.repo.DimensionRepo;
import com.odat.fastrans.repo.ProductRepo;
import com.odat.fastrans.repo.TownRepo;
import com.odat.fastrans.repo.VillageRepo;

@RestController
@RequestMapping("/lookups")
@CrossOrigin
@AllArgsConstructor
public class LookupsController {
	
private final VillageRepo villageRepo;
private final TownRepo townRepo;
private final CityRepo cityRepo;
private final AddressRepo addressRepo;
private final AccountRepo accountRepo;

private final ProductRepo productRepo;
private final DimensionRepo dimensionRepo;


	
	

	@GetMapping("/villages")
	public ResponseEntity<List<Village>> getVillages(){
		return ResponseEntity.ok(villageRepo.findAll());
	}
	
	@GetMapping("/dimensions")
	public ResponseEntity<List<Dimension>> getDimensions(){
		return ResponseEntity.ok(dimensionRepo.findAll());
	}
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts(){
		return ResponseEntity.ok(productRepo.findAll());
	}
	
	@PostMapping("/villages")
	public ResponseEntity<Void> postVillages(@RequestBody List<Village> villages){
		villageRepo.saveAll(villages);
		return new ResponseEntity<Void>( HttpStatus.CREATED );
	}
	
	
	@GetMapping("/cities")
	public ResponseEntity<List<City>> getCities(){
		return ResponseEntity.ok(cityRepo.findAll());
	}
	
	
	@PostMapping("/addresses")
	public ResponseEntity<Void> postAddresses(@RequestBody List<Address> addresses){
		addressRepo.saveAll(addresses);
		return new ResponseEntity<Void>( HttpStatus.CREATED );
	}
	
	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAccounts(){
		return ResponseEntity.ok(accountRepo.findAll());
	}
	
	@GetMapping("/addresses")
	public ResponseEntity<List<Address>> getAddresses(){
		return ResponseEntity.ok(addressRepo.findAll());
	}
	 
	@PostMapping("/cities")
	public ResponseEntity<Void> postCities(@RequestBody List<City> cities){
		for(City city: cities) {
			Town.assignCities(city);
		}
		cityRepo.saveAll(cities);
		return new ResponseEntity<Void>( HttpStatus.CREATED );
	}
	
	
	
	
	@GetMapping("/towns")
	public ResponseEntity<List<Town>> getTowns(){
		return ResponseEntity.ok(townRepo.findAll());
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
