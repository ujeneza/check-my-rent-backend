package com.checkmyrent.users.service;

import com.checkmyrent.users.shared.dto.AddressDTO;

import java.util.List;


public interface AddressService {
    List<AddressDTO> getAddresses(String userId);
    AddressDTO getAddress(String addressId);



}
