package com.odat.fastrans.service;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.odat.fastrans.entity.*;
import com.odat.fastrans.entity.Package;
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
import com.odat.fastrans.repo.AccountRepo;
import com.odat.fastrans.repo.AddressRepo;
import com.odat.fastrans.repo.CityRepo;
import com.odat.fastrans.repo.DimensionRepo;
import com.odat.fastrans.repo.PackageRepo;
import com.odat.fastrans.repo.ProductRepo;
import com.odat.fastrans.repo.ShipmentRepo;
import com.odat.fastrans.repo.ShipmentStatusRepo;
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
	final private ShipmentStatusRepo shipmentStatusRepo;
	public Account getAccount() {
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String email = ((org.springframework.security.core.userdetails.User) principle).getUsername();
		Account account = accountRepo.findByEmail(email).get();

		return account;
	}

	public Supplier getSupplier() {

		Optional<Supplier> s = supplierRepo.findByAccount(getAccount());
		return s.get();
	}
	public Package savePackage(PackageDTO packageDTO) {
		Optional<Supplier> supplierOptinal = supplierRepo.findByAccount(getAccount());
		if (supplierOptinal.isEmpty()) {
			throw new NoSuchElementException("Supplier not found");
		}

		Supplier supplier = supplierOptinal.get();
		return  savePackage(supplier,packageDTO);
		
	}
	
	public Package savePackage(Supplier supplier, PackageDTO packageDTO) {
		Optional<Product> productOptional = productRepo.findById(packageDTO.getProduct().getId());
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

		Package pakage = new Package(product, dim, packageDTO.getPrice(),packageDTO.getWeight(),packageDTO.getPieces());
		pakage.setSupplier(supplier);
		pakage = packageRepo.save(pakage);

		return pakage;
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


	public  List<Shipment> getShipments(String statusId) {

		String [] a = statusId.split(",");
		List<String> statusList =   Arrays.asList(a);
		Optional<Supplier> supplierOptinal = supplierRepo.findByAccount(getAccount());
		if (supplierOptinal.isEmpty()) {
			throw new NoSuchElementException("Supplier not found");
		}
		Supplier supplier =  supplierOptinal.get();
		 List<Shipment>  shipments = shipmentRepo.findAllBySupplierAndShipmentStatuses(supplier.getId(),statusList);


		return shipments;
	}



	public List<Address> getAddressBySupplierAndFromAddress (boolean isFromAddress) {
		
		Optional<Supplier> supplierOptinal = supplierRepo.findByAccount(getAccount());
		if (supplierOptinal.isEmpty()) {
			throw new NoSuchElementException("Supplier not found");
		}
		 Supplier supplier =supplierOptinal.get();
		return addressRepo.findAllBySupplierAndFromAddress(supplier,isFromAddress);
	}
	
	public Shipment saveShipments(ShipmentDTO shipmentDTO) {

		Optional<Supplier> supplierOptinal = supplierRepo.findByAccount(getAccount());
		if (supplierOptinal.isEmpty()) {
			throw new NoSuchElementException("Supplier not found");
		}

		Supplier supplier = supplierOptinal.get();
		//shipmentDTO.getFromAddress().setFavourite(false);		
		Address fromAddress = saveAddress(shipmentDTO.getFromAddress(),null);
		//shipmentDTO.getToAddress().setFavourite(false);
		Address toAddress = saveAddress(shipmentDTO.getToAddress(),null);
		 
		Package pakage = savePackage(null, shipmentDTO.getPackageDetails());
		
		Optional<ShipmentStatus> shipmentStatusOptional = shipmentStatusRepo.findById(0L);
		if (shipmentStatusOptional.isEmpty()) {
			throw new NoSuchElementException("shipmentStatus not found");
		}
		ShipmentStatus shipmentStatus = shipmentStatusOptional.get();
		
		Shipment shipment = new Shipment(supplier,fromAddress, toAddress, shipmentDTO.getPickupDate(),
				shipmentDTO.getPickupTime(), pakage,shipmentStatus);

		shipmentRepo.save(shipment);

		return shipment;
	}

	public Supplier registerHandler(SupplierDTO supplierDTO) {
		
		LoginCredentialsDTO loginCredentials = supplierDTO.getCredentials();
		String encodedPass = passwordEncoder.encode(loginCredentials.getPassword());
		Account account = new Account(loginCredentials.getMobile(), loginCredentials.getEmail(), encodedPass,
				loginCredentials.getFullName(), 1,"USER");

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
					/*addressDTO.isFavourite(),*/addressDTO.isFromAddress());
			
			//if(addressDTO.isFavourite()) 
			address.setSupplier(supplier);
			
			address.setBuildingNumber(addressDTO.getBuildingNumber());
			address.setFullName(addressDTO.getFullName());
			address.setLatitude(addressDTO.getLatitude());
			address.setLongitude(addressDTO.getLongitude());
			address.setStreet(addressDTO.getStreet());
			address.setFromAddress(addressDTO.isFromAddress());
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

	public Address saveAddresses(AddressDTO addressDTO) {
		Optional<Supplier> supplierOptinal = supplierRepo.findByAccount(getAccount());
		if (supplierOptinal.isEmpty()) {
			throw new NoSuchElementException("Supplier not found");
		}

		Supplier supplier = supplierOptinal.get();
		return  saveAddress(addressDTO,supplier);
		
	}

	public List<Package> getPackagesBySupplier() {
		Optional<Supplier> supplierOptinal = supplierRepo.findByAccount(getAccount());
		if (supplierOptinal.isEmpty()) {
			throw new NoSuchElementException("Supplier not found");
		}
		 Supplier supplier =supplierOptinal.get();
		return packageRepo.findAllBySupplier(supplier);
	}





	public  List<Shipment> getShipments(long status) {

		Optional<Supplier> supplierOptinal = supplierRepo.findByAccount(getAccount());
		if (supplierOptinal.isEmpty()) {
			throw new NoSuchElementException("Supplier not found");
		}

		Supplier supplier = supplierOptinal.get();

		Optional<ShipmentStatus> shipmentStatusOptional = shipmentStatusRepo.findById(status);
		if (shipmentStatusOptional.isEmpty()) {
			throw new NoSuchElementException("shipmentStatus not found");
		}
		ShipmentStatus shipmentStatus = shipmentStatusOptional.get();

		Optional<List<Shipment>> shipmentsOptional = shipmentRepo.findAllBySupplierAndShipmentStatus(supplier,shipmentStatus);
		List<Shipment> shipments= shipmentsOptional.get();

		return shipments;
	}
}