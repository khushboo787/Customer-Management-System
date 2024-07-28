package com.myapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String street;
    private String address;
    private String city;
    private String state;
    private String email;
    private String phone;

    public void update(Customer newCustomer) {
        if (newCustomer == null) {
            throw new IllegalArgumentException("Cannot update with null customer");
        }
        this.firstName = newCustomer.getFirstName();
        this.lastName = newCustomer.getLastName();
        this.street = newCustomer.getStreet();
        this.address = newCustomer.getAddress();
        this.city = newCustomer.getCity();
        this.state = newCustomer.getState();
        this.email = newCustomer.getEmail();
        this.phone = newCustomer.getPhone();
    }

	 
    
}
