package com.odat.fastrans.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.odat.fastrans.entity.Package;
public interface PackageRepo  extends JpaRepository<Package, Long>{

}
