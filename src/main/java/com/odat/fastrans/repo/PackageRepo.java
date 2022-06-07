package com.odat.fastrans.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.odat.fastrans.entity.Package;
import com.odat.fastrans.entity.Customer;
public interface PackageRepo  extends JpaRepository<Package, Long>{

	public List<Package> findAllByCustomer(Customer customer);

}
