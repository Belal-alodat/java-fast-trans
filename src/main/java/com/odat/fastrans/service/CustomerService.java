package com.odat.fastrans.service;

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
import com.odat.fastrans.dto.LoginCredentialsDTO;
import com.odat.fastrans.dto.PackageDTO;
import com.odat.fastrans.dto.ShipmentDTO;
import com.odat.fastrans.dto.CustomerDTO;
import com.odat.fastrans.repo.AccountRepo;
import com.odat.fastrans.repo.AddressRepo;
import com.odat.fastrans.repo.CityRepo;
import com.odat.fastrans.repo.DimensionRepo;
import com.odat.fastrans.repo.PackageRepo;
import com.odat.fastrans.repo.ProductRepo;
import com.odat.fastrans.repo.ShipmentRepo;
import com.odat.fastrans.repo.ShipmentStatusRepo;
import com.odat.fastrans.repo.CustomerRepo;
import com.odat.fastrans.repo.TownRepo;
import com.odat.fastrans.repo.VillageRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomerService {

	final private AccountRepo accountRepo;
	final private CustomerRepo customerRepo;
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


	public Package savePackage(PackageDTO packageDTO) {
		Customer supplier = getCustomer();
		return  savePackage(supplier,packageDTO);
		
	}
	
	public Package savePackage(Customer supplier, PackageDTO packageDTO) {
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
		pakage.setCustomer(supplier);
		pakage = packageRepo.save(pakage);

		return pakage;
	}


	public  List<Shipment> getShipments(String statusId) {
		List<Shipment> shipments ;
		Customer customer = getCustomer();

		if(null == statusId || statusId.isEmpty()){
			Optional<List<Shipment>> shipmentsOptional = shipmentRepo.findAllByCustomer(customer);
			  shipments= shipmentsOptional.get();
		}else {

			String[] a = statusId.split(",");
			List<String> statusList = Arrays.asList(a);

			  shipments = shipmentRepo.findAllByCustomerAndShipmentStatuses(customer.getId(), statusList);
		}

		return shipments;
	}



	public List<Address> getAddressByCustomerAndFromAddress(boolean isFromAddress) {

		Customer supplier = getCustomer();
		return addressRepo.findAllByCustomerAndFromAddress(supplier,isFromAddress);
	}
	
	public Shipment saveShipments(ShipmentDTO shipmentDTO) {

		Customer supplier = getCustomer();
		Address fromAddress = saveAddress(shipmentDTO.getFromAddress(),null);
		Address toAddress = saveAddress(shipmentDTO.getToAddress(),null);
		Package pakage = savePackage(null, shipmentDTO.getPackageDetails());
		long  shipmentStatusId = 0l;
		ShipmentStatus shipmentStatus = getShipmentStatus(shipmentStatusId);

		Shipment shipment = new Shipment(supplier,fromAddress, toAddress, shipmentDTO.getPickupDate(),
				shipmentDTO.getPickupTime(), pakage,shipmentStatus);

		shipmentRepo.save(shipment);

		return shipment;
	}

	private ShipmentStatus getShipmentStatus(long shipmentStatusId) {
		Optional<ShipmentStatus> shipmentStatusOptional = shipmentStatusRepo.findById(shipmentStatusId);
		if (shipmentStatusOptional.isEmpty()) {
			throw new NoSuchElementException("shipmentStatus not found");
		}
		ShipmentStatus shipmentStatus = shipmentStatusOptional.get();
		return shipmentStatus;
	}

	public Customer registerHandler(CustomerDTO customerDTO) {
		
		LoginCredentialsDTO loginCredentials = customerDTO.getCredentials();
		String encodedPass = passwordEncoder.encode(loginCredentials.getPassword());
		Account account = new Account(loginCredentials.getMobile(), loginCredentials.getEmail(), encodedPass,
				loginCredentials.getFullName(), 1,"USER");

		account = accountRepo.save(account);

		Customer supplier = new Customer(account);

		supplier = customerRepo.save(supplier);

		return supplier;
	}

	 
	private Address saveAddress(AddressDTO addressDTO, Customer supplier) {
		long cityId = addressDTO.getCity().getId();
		long townId = addressDTO.getTown().getId();
		long villageId = addressDTO.getVillage().getId();
		Address address = null;
		if (addressDTO.getId() == -1) {
			City city = getCity(cityId);
			Town town = getTown(townId);
			Village village = getVillage(villageId);
			address = new 	Address( supplier, city,   town,   village,  addressDTO);
			address = addressRepo.save(address);
		} else {
			address = getAddress(addressDTO.getId());
		}
		return address;
	}

	private Address getAddress(long addressId) {
		Address address;
		Optional<Address> addressOptional = addressRepo.findById(addressId);
		if (addressOptional.isEmpty()) {
			throw new NoSuchElementException("Address not found");
		}
		address = addressOptional.get();
		return address;
	}

	private  Village getVillage(long villageId) {
		Optional<Village> villageOptional = villageRepo.findById(villageId);
		if (villageOptional.isEmpty()) {
			throw new NoSuchElementException("Village not found");
		}
		return villageOptional.get();
	}

	private Town getTown(long townId) {
		Optional<Town> townOptional = townRepo.findById(townId);
		if (townOptional.isEmpty()) {
			throw new NoSuchElementException("Town not found");
		}

		Town town = townOptional.get();
		return town;
	}

	private City getCity(long cityId) {
		Optional<City> cityOptional = cityRepo.findById(cityId);
		if (cityOptional.isEmpty()) {
			throw new NoSuchElementException("City not found");
		}
		City city =cityOptional.get();
		return city;
	}

	public Address saveAddresses(AddressDTO addressDTO) {
		Customer supplier = getCustomer();
		return  saveAddress(addressDTO,supplier);
		
	}

	public List<Package> getPackagesByCustomer() {
		Customer supplier = getCustomer();
		return packageRepo.findAllByCustomer(supplier);
	}





	public  List<Shipment> getShipments(long status) {

		Customer supplier = getCustomer();

		ShipmentStatus shipmentStatus = getShipmentStatus(status);

		Optional<List<Shipment>> shipmentsOptional = shipmentRepo.findAllByCustomerAndShipmentStatus(supplier,shipmentStatus);
		List<Shipment> shipments= shipmentsOptional.get();

		return shipments;
	}

	private Customer getCustomer() {
		Optional<Customer> supplierOptinal = customerRepo.findByAccount(getAccount());
		if (supplierOptinal.isEmpty()) {
			throw new NoSuchElementException("Supplier not found");
		}

		Customer supplier = supplierOptinal.get();
		return supplier;
	}
}