package com.odat.fastrans.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.odat.fastrans.dto.AddressDTO;
import com.odat.fastrans.dto.CityDTO;
import com.odat.fastrans.dto.DimensionDTO;
import com.odat.fastrans.dto.LoginCredentialsDTO;
import com.odat.fastrans.dto.PackageDTO;
import com.odat.fastrans.dto.ProductDTO;
import com.odat.fastrans.dto.ShipmentDTO;
import com.odat.fastrans.dto.SupplierDTO;
import com.odat.fastrans.dto.TownDTO;
import com.odat.fastrans.dto.VillageDTO;
import com.odat.fastrans.entity.Account;
import com.odat.fastrans.entity.Address;
import com.odat.fastrans.entity.City;
import com.odat.fastrans.entity.Dimension;
import com.odat.fastrans.entity.Package;
import com.odat.fastrans.entity.Product;
import com.odat.fastrans.entity.Shipment;
import com.odat.fastrans.entity.Supplier;
import com.odat.fastrans.entity.Town;
import com.odat.fastrans.entity.Village;
import com.odat.fastrans.repo.AccountRepo;
import com.odat.fastrans.repo.AddressRepo;
import com.odat.fastrans.repo.CityRepo;
import com.odat.fastrans.repo.DimensionRepo;
import com.odat.fastrans.repo.PackageRepo;
import com.odat.fastrans.repo.ProductRepo;
import com.odat.fastrans.repo.ShipmentRepo;
import com.odat.fastrans.repo.SupplierRepo;
import com.odat.fastrans.repo.TownRepo;
import com.odat.fastrans.repo.VillageRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SupplierService {

	final private AccountRepo accountRepo;
	final private SupplierRepo supplierRepo;
	final private ProductRepo productRepo;
	final private DimensionRepo dimensionRepo;
	final private PackageRepo packageRepo;
	final private CityRepo cityRepo;
	final private TownRepo townRepo;
	final private VillageRepo villageRepo;
	final private ShipmentRepo shipmentRepo;
	final private AddressRepo addressRepo;
	final private PasswordEncoder passwordEncoder;

	public Account getAccount() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Account account = accountRepo.findByEmail(email).get();

		return account;
	}

	public Supplier getSupplier() {

		Optional<Supplier> s = supplierRepo.findByAccount(getAccount());
		PackageDTO x = new PackageDTO();
		x.setProductName(new ProductDTO());
		x.setDimension(new DimensionDTO());
		return s.get();
	}

	private Package savePackage(Supplier supplier, PackageDTO packageDTO) {
		Optional<Product> productOptional = productRepo.findById(packageDTO.getProductName().getId());
		if (productOptional.isEmpty()) {
			throw new NoSuchElementException("Product not found");
		}
		Product product = productOptional.get();

		Optional<Dimension> dimensionOptional = dimensionRepo.findById(packageDTO.getDimension().getId());
		if (dimensionOptional.isEmpty()) {
			throw new NoSuchElementException("Dimension not found");
		}
		Dimension dim = dimensionOptional.get();
		// Dimension dim = null;

		Package pakage = new Package(product, dim, packageDTO.getPrice());
		pakage = packageRepo.save(pakage);

		return pakage;
	}

	
	public  ShipmentDTO getShipment() {
		ShipmentDTO shipmentDTO = new ShipmentDTO();
		shipmentDTO.setPickupDate(Date.valueOf("2022-12-12"));
		shipmentDTO.setPickupTime(Time.valueOf("12:12:12"));
		int buildingNumber=1;
		double longitude=1.1;
		double latitude=1.1;
		AddressDTO add =	new AddressDTO(1, new CityDTO(), new TownDTO(-1,""),
				new VillageDTO(-1,""), 
				"mobile","street",buildingNumber,
				"fullName",latitude, longitude, false);
		shipmentDTO.setFromAddress(add);
		shipmentDTO.setToAddress(add);
		PackageDTO packagedetails = new PackageDTO();
		packagedetails.setDimension(new DimensionDTO());
		packagedetails.setProductName(new ProductDTO());
		shipmentDTO.setPackageDetails(packagedetails );
		return shipmentDTO;
	} 
	
	public  List<Shipment> getShipments() {
		
		Optional<Supplier> supplierOptinal = supplierRepo.findByAccount(getAccount());
		if (supplierOptinal.isEmpty()) {
			throw new NoSuchElementException("Supplier not found");
		}
		Optional<List<Shipment>> shipmentsOptional = shipmentRepo.findAllBySupplier(supplierOptinal.get());
		List<Shipment> shipments= shipmentsOptional.get();
		
	 return shipments;
	} 
	
	
	public List<Address> getAddressBySupplierAndIsFavourite () {
		
		Optional<Supplier> supplierOptinal = supplierRepo.findByAccount(getAccount());
		if (supplierOptinal.isEmpty()) {
			throw new NoSuchElementException("Supplier not found");
		}
		 Supplier supplier =supplierOptinal.get();
		return addressRepo.findAllBySupplierAndIsFavourite(supplier ,true);
	}
	
	public Shipment saveShipments(ShipmentDTO shipmentDTO) {

		Optional<Supplier> supplierOptinal = supplierRepo.findByAccount(getAccount());
		if (supplierOptinal.isEmpty()) {
			throw new NoSuchElementException("Supplier not found");
		}

		Supplier supplier = supplierOptinal.get();
		Address fromAddress = saveAddress(shipmentDTO.getFromAddress(),supplier);
		Address toAddress = saveAddress(shipmentDTO.getToAddress(),supplier);
		Package pakage = savePackage(supplier, shipmentDTO.getPackageDetails());
		Shipment shipment = new Shipment(supplier,fromAddress, toAddress, shipmentDTO.getPickupDate(),
				shipmentDTO.getPickupTime(), pakage);

		shipmentRepo.save(shipment);

		return shipment;
	}

	public Supplier registerHandler(SupplierDTO supplierDTO) {
		
		LoginCredentialsDTO loginCredentials = supplierDTO.getCredentials();
		String encodedPass = passwordEncoder.encode(loginCredentials.getPassword());
		Account account = new Account(loginCredentials.getMobile(), loginCredentials.getEmail(), encodedPass,
				loginCredentials.getFullName(), 1);

		account = accountRepo.save(account);

		Supplier supplier = new Supplier(account);

		supplier = supplierRepo.save(supplier);

		return supplier;
	}

	private Address saveAddress(AddressDTO addressDTO,Supplier supplier) {
		long cityId = addressDTO.getCity().getId();
		long townId = addressDTO.getTown().getId();
		long villageId = addressDTO.getVillage().getId();

		
		Address address = null;
		if (addressDTO.getId() == -1) {
			
			Optional<City> cityOptional = cityRepo.findById(cityId);
			if (cityOptional.isEmpty()) {
				throw new NoSuchElementException("City not found");
			}

			Optional<Town> townOptional = townRepo.findById(townId);
			if (townOptional.isEmpty()) {
				throw new NoSuchElementException("Town not found");
			}

			Optional<Village> villageOptional = villageRepo.findById(villageId);
			if (villageOptional.isEmpty()) {
				throw new NoSuchElementException("Village not found");
			}

			
			address = new Address(cityOptional.get(), townOptional.get(), villageOptional.get(),
					addressDTO.getMobile(),
					addressDTO.isFavourite());
			
			if(addressDTO.isFavourite()) address.setSupplier(supplier);
			
			address.setBuildingNumber(addressDTO.getBuildingNumber());
			address.setFullName(addressDTO.getFullName());
			address.setLatitude(addressDTO.getLatitude());
			address.setLongitude(addressDTO.getLongitude());
			address.setStreet(addressDTO.getStreet());
			address = addressRepo.save(address);
		} else {
			Optional<Address> addressOptional = addressRepo.findById(addressDTO.getId());
			if (addressOptional.isEmpty()) {
				throw new NoSuchElementException("Address not found");
			}
			address = addressOptional.get();
		}
		return address;
	}
}