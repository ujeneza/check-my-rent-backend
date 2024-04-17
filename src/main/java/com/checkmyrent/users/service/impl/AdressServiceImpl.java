package com.checkmyrent.users.service.impl;

import com.checkmyrent.users.service.AddressService;
import com.checkmyrent.users.shared.dto.AddressDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdressServiceImpl implements AddressService {
    @Override
    public List<AddressDTO> getAddresses(String userId) {
        return null;
    }

    @Override
    public AddressDTO getAddress(String addressId) {
        return null;
    }
}
