package com.odat.fastrans.service;

import com.odat.fastrans.entity.*;
import com.odat.fastrans.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@AllArgsConstructor
@Service
public class DriverService {

    final private AccountRepo accountRepo;
    final private SupplierRepo supplierRepo;
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

    public List<Shipment> getShipmentsByStatus(long statusId) {

        Driver driver = getDriver();
        List<Shipment>	  shipments = shipmentRepo.queryBy(driver.getId(),statusId);
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




    public void updateDriverShipment(long shipmentId,long shipmentStatusId) {
        Driver driver = getDriver();


        Optional<ShipmentStatus> optionalShipmentStatus = shipmentStatusRepo.findById(shipmentStatusId);
        ShipmentStatus shipmentStatus = optionalShipmentStatus.get();

        if (optionalShipmentStatus.isEmpty()) {
            throw new NoSuchElementException("ShipmentStatus not found");
        }
        Optional<Shipment> optionalShipment = shipmentRepo.findById(shipmentId);

        if (optionalShipment.isEmpty()) {
            throw new NoSuchElementException("Shipment not found");
        }

        Shipment shipment = optionalShipment.get();


        ShipmentStatus currentShipmentStatus ;
        if(shipmentStatus.getId() == Driver_pick_Accepted || shipmentStatus.getId() ==  Driver_pick_Rejected ) {
            currentShipmentStatus = shipmentStatusRepo.findById(Operator_Assigned_For_Picking).get();
        }else{
            currentShipmentStatus = shipmentStatusRepo.findById(Operator_Assigned_For_Delivery).get();
        }

        Optional<DriverShipment>  optionalDriverShipment =  driverShipmentRepo.findByDriverAndShipmentAndShipmentStatus(  driver,  shipment,   currentShipmentStatus);
        if (optionalDriverShipment.isEmpty()) {
            throw new NoSuchElementException("DriverShipment not found");
        }
        DriverShipment driverShipment = optionalDriverShipment.get();

           driverShipment.setShipmentStatus(shipmentStatus);
           driverShipmentRepo.saveAndFlush(driverShipment);

           if( shipmentStatus.getId() ==  Driver_pick_Rejected){
               shipmentStatus = shipmentStatusRepo.findById(Customer_Accepted).get();
           } else if( shipmentStatus.getId() ==  Driver_deliver_Rejected){
               shipmentStatus = shipmentStatusRepo.findById(Operator_Store_Accepted).get();
           }

         shipment.setShipmentStatus(shipmentStatus);
          shipmentRepo.saveAndFlush(shipment);

    }




    final static long Driver_pick_Accepted =  12l;
    final static long Driver_pick_Rejected =  13l;

    final static long Driver_deliver_Accepted =  15l;

    final static long Driver_deliver_Rejected =  16l;

    final static long Operator_Assigned_For_Picking =  1l;
    final static long Operator_Assigned_For_Delivery =  3l;
    final static long Operator_Store_Accepted =  11l;

    final static long Customer_Accepted =  7l;

}
