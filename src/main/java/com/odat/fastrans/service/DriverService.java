package com.odat.fastrans.service;

import com.odat.fastrans.dto.ShipmentStatusConsttant;
import com.odat.fastrans.entity.*;
import com.odat.fastrans.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@AllArgsConstructor
@Service
public class DriverService {

    final private AccountRepo accountRepo;
    final private CustomerRepo customerRepo;
    final private DriverRepo driverRepo;
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
    final private DriverShipmentRepo driverShipmentRepo;

    public List<Shipment> getShipmentsByStatus(String statusId) {

        Driver driver = getDriver();
        String [] a = statusId.split(",");
        List<String> statusList =   Arrays.asList(a);
        List<Shipment>	  shipments = shipmentRepo.queryBy(driver.getId(),statusList);
        return shipments;
    }

    public Account getAccount() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = ((org.springframework.security.core.userdetails.User) principle).getUsername();
        Account account = accountRepo.findByEmail(email).get();

        return account;
    }

    public Driver getDriver() {

        Optional<Driver> d = driverRepo.findByAccount(getAccount());
        if (d.isEmpty()) {
            throw new NoSuchElementException("Driver not found");
        }
        return d.get();
    }


    public List<Shipment> getShipmentsCart(){
        Driver driver = getDriver();
        List<Shipment> shipments =   shipmentRepo.getByDriverAndStatus(driver.getId());
        return  shipments;
    }
    public void updateDriverShipment(long shipmentId,long lCurrentShipmentStatus,
                                                long lToShipmentStatus) {

        Driver driver = getDriver();

        Optional<Shipment> optionalShipment = shipmentRepo.findById(shipmentId);

        if (optionalShipment.isEmpty()) {
            throw new NoSuchElementException("Shipment not found");
        }

        Shipment shipment = optionalShipment.get();

        Optional<ShipmentStatus> optionalCurrentShipmentStatus = shipmentStatusRepo.findById(lCurrentShipmentStatus);


        if (optionalCurrentShipmentStatus.isEmpty()) {
            throw new NoSuchElementException("ShipmentStatus not found");
        }

        ShipmentStatus currentShipmentStatus = optionalCurrentShipmentStatus.get();

        Optional<DriverShipment>  optionalDriverShipment =  driverShipmentRepo.findByDriverAndShipmentAndShipmentStatus(  driver,  shipment,   currentShipmentStatus);
        if (optionalDriverShipment.isEmpty()) {
            throw new NoSuchElementException("DriverShipment not found");
        }
        DriverShipment driverShipment = optionalDriverShipment.get();

       Optional<ShipmentStatus> optionalToShipmentStatus = shipmentStatusRepo.findById(lToShipmentStatus);


        if (optionalToShipmentStatus.isEmpty()) {
            throw new NoSuchElementException("ShipmentStatus not found");
        }

        ShipmentStatus toShipmentStatus = optionalToShipmentStatus.get();

        driverShipment.setShipmentStatus(toShipmentStatus);
        driverShipmentRepo.saveAndFlush(driverShipment);

        shipment.setShipmentStatus(toShipmentStatus);
        shipmentRepo.saveAndFlush(shipment);
    }

        public void updateDriverShipment(long shipmentId,long lToShipmentStatus) {
            long lCurrentShipmentStatus = ShipmentStatusConsttant.Operator_Assigned_For_Delivery;

            if(lToShipmentStatus  == ShipmentStatusConsttant.Driver_pick_Accepted ||
                    lToShipmentStatus ==  ShipmentStatusConsttant.Driver_pick_Rejected ) {
                lCurrentShipmentStatus = ShipmentStatusConsttant.Operator_Assigned_For_Picking;
            }else{

            }
            updateDriverShipment(  shipmentId,  lCurrentShipmentStatus, lToShipmentStatus);

    }






}
