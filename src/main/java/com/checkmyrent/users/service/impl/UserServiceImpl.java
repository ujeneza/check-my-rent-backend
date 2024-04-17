package com.checkmyrent.users.service.impl;

import com.checkmyrent.users.exceptions.UserServiceException;
import com.checkmyrent.users.io.UserEntity;
import com.checkmyrent.users.repository.UserRepository;
import com.checkmyrent.users.service.UserService;
import com.checkmyrent.users.shared.dto.AddressDTO;
import com.checkmyrent.users.shared.dto.UserDTO;
import com.checkmyrent.users.shared.dto.Utils;
import com.checkmyrent.users.ui.model.response.ErrorMessages;
import com.checkmyrent.users.ui.model.response.UserResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public UserDTO createUser(UserDTO user) {

        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new UserServiceException("Record already exists");

        for(int i=0;i<user.getAddresses().size();i++)
        {
            AddressDTO address = user.getAddresses().get(i);
            address.setUserDetails(user);
            address.setAddressId(utils.generateAddressId(30));
            user.getAddresses().set(i, address);
        }

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDTO returnValue  = modelMapper.map(storedUserDetails, UserDTO.class);
        return returnValue;
    }

    @Override
    public List<UserResponseModel> getUsers() {
        ModelMapper modelMapper = new ModelMapper();
        List<UserEntity> users = userRepository.findAll();
        // Object k;
        return users.stream().map(user -> modelMapper.map(user, UserResponseModel.class)).collect(Collectors.toList());
        // UserEntity userEntity = modelMapper.map(user, UserEntity.class);
         // null;
    }

    @Override
    public UserDTO getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }



    @Override
    public UserDTO getUserByUserId(String userId) {
        UserDTO returnValue = new UserDTO();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());;

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO user) {
        UserDTO returnValue = new UserDTO();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        returnValue = new ModelMapper().map(updatedUserDetails, UserDTO.class);

        return returnValue;
    }

    @Transactional
    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userRepository.delete(userEntity);

    }
}
