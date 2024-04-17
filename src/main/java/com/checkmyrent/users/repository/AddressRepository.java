package com.checkmyrent.users.repository;


import com.checkmyrent.users.io.AddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
	// List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
	// AddressEntity findByAddressId(String addressId);
}
