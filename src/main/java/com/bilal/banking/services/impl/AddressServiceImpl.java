package com.bilal.banking.services.impl;

import com.bilal.banking.dto.AddressDto;
import com.bilal.banking.model.Address;
import com.bilal.banking.repository.AddressRepository;
import com.bilal.banking.services.AddressService;
import com.bilal.banking.validators.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final ObjectValidator<AddressDto> validator;
    @Override
    public Integer save(AddressDto dto) {

        validator.validate(dto);

        Address address = AddressDto.toEntity(dto);
        return repository.save(address).getId();
    }

    @Override
    public List<AddressDto> findAll() {

       return repository.findAll()
               .stream()
               .map(AddressDto::fromEntity)
               .collect(Collectors.toList());

    }

    @Override
    public AddressDto findById(Integer id) {

        return repository.findById(id)
                .map(AddressDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("No adress found with the ID: " + id));
    }

    @Override
    public void delete(Integer id) {

        repository.deleteById(id);

    }
}
