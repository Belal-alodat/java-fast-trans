package com.odat.fastrans.controller;

import com.odat.fastrans.dto.ShipmentDTO;
import com.odat.fastrans.entity.Shipment;
import com.odat.fastrans.service.DriverService;
import com.odat.fastrans.service.OperatorService;
import com.odat.fastrans.service.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drivers")
@AllArgsConstructor
@CrossOrigin
public class DriverController {
    final private ShipmentService shipmentService;
    final private OperatorService operatorService;
    final private DriverService driverService;

    @GetMapping("/shipments")
    public ResponseEntity<List<ShipmentDTO>> getShipments(@RequestParam(name="status",required = true) String statusList) {

        List<Shipment> shipments= driverService.getShipmentsByStatus(statusList);

        List<ShipmentDTO> shipmentsDto = shipments.stream().map(
                s -> new ShipmentDTO(s)
        ).collect(Collectors.toList());

        return  new ResponseEntity<List<ShipmentDTO>>(shipmentsDto, HttpStatus.OK);
    }



    @PatchMapping ("/shipments/{shipmentId}/status/{shipmentStatusId}")
    public ResponseEntity<Void> updateShipmentsStatus(@PathVariable(name ="shipmentId" ) long shipmentId,
                                                      @PathVariable(name ="shipmentStatusId" ) long shipmentStatusId) {

        driverService.updateDriverShipment(shipmentId,shipmentStatusId);

        return  new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PatchMapping ("/shipments/{shipmentId}/status/{shipmentStatusId}/to/{toShipmentStatusId}")
    public ResponseEntity<Void> updateShipmentsStatus(@PathVariable(name ="shipmentId" ) long shipmentId
                                                      ,@PathVariable(name ="shipmentStatusId" ) long shipmentStatusId
                                                      ,@PathVariable(name ="toShipmentStatusId" ) long toShipmentStatusId) {

        driverService.updateDriverShipment(shipmentId,shipmentStatusId,toShipmentStatusId);

        return  new ResponseEntity<Void>(HttpStatus.OK);
    }

}
