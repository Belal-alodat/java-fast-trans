package com.odat.fastrans.repo;

import java.util.List;
import java.util.Optional;

import com.odat.fastrans.dto.ShipmentStatusConsttant;
import org.springframework.data.jpa.repository.JpaRepository;

import com.odat.fastrans.entity.Shipment;
import com.odat.fastrans.entity.ShipmentStatus;
import com.odat.fastrans.entity.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShipmentRepo  extends JpaRepository<Shipment, Long>{
	
	public Optional<List<Shipment>> findAllBySupplier(Supplier supplier);
	public Optional<List<Shipment>> findAllByShipmentStatus(ShipmentStatus shipmentStatus);
	public Optional<List<Shipment>> findAllBySupplierAndShipmentStatus(Supplier supplier,ShipmentStatus shipmentStatus);
	//public Optional<List<Shipment>> findAllBySupplierAndShipmentStatus(Supplier supplier,ShipmentStatus shipmentStatus);



	@Query(value = "select sh.* FROM  shipment as sh  ,  driver_shipment  as dr " +
			"where sh.id = dr.shipment_id and  sh.shipment_status_id =dr.shipment_status_id and " +
			" dr.driver_id = :driverId and  dr.shipment_status_id in (:statusList) ",
			nativeQuery = true)
	List<Shipment> queryBy(@Param("driverId") long driverId,
						   @Param("statusList")  List<String>   statusList);


	@Query(value = "select sh.* FROM  shipment as sh  ,  driver_shipment  as dr " +
			"where sh.id = dr.shipment_id and  sh.shipment_status_id=dr.shipment_status_id" +
			" and  dr.driver_id = :driverId and  dr.shipment_status_id in( "
			+ShipmentStatusConsttant.Driver_pick_Accepted+","
			+ ShipmentStatusConsttant.Driver_Picked
			+","+ShipmentStatusConsttant.Driver_deliver_Accepted+")",
			nativeQuery = true)
	List<Shipment> getByDriverAndStatus(@Param("driverId") long driverId);
	 
}
