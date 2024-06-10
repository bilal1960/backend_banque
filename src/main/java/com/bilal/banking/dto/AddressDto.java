package com.bilal.banking.dto;

import com.bilal.banking.model.Address;
import com.bilal.banking.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AddressDto {

    private Integer id;

    private String rue;

    private Integer houseNumber;

    private Integer zipCode;

    private String city;

    private String country;

    private Integer userId;

    public  static AddressDto fromEntity(Address address){

        return AddressDto.builder()
                .id(address.getId())
                .rue(address.getRue())
                .houseNumber(address.getHouseNumber())
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .country(address.getCountry())
                .userId(address.getUser().getId())
                .build();
    }

    public  static Address toEntity(AddressDto address){

        return Address.builder()
                .id(address.getId())
                .rue(address.getRue())
                .houseNumber(address.getHouseNumber())
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .country(address.getCountry())
                .user(
                        User.builder()
                                .id(address.getUserId())
                                .build()
                )
                .build();
    }

}
