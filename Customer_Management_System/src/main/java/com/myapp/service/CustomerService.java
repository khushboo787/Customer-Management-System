package com.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myapp.entity.Customer;
import com.myapp.exception.CustomerNotFoundException;
import com.myapp.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    
     
    public String saveCustomer(Customer cus) throws CustomerNotFoundException{

        Optional<Customer> opt = customerRepository.findByEmail(cus.getEmail());

            if (opt.isPresent()) {
                throw new CustomerNotFoundException("Customer already exists with same email: " + cus.getEmail());
            }

            Customer c = new Customer();

            c.setFirstName(cus.getFirstName());
            c.setLastName(cus.getLastName());
            c.setStreet(cus.getStreet());
            c.setAddress(cus.getAddress());
            c.setCity(cus.getCity());
            c.setState(cus.getState());
            c.setEmail(cus.getEmail());
            c.setPhone(cus.getPhone());
            

            customerRepository.save(c);

            return "Customer registered successfully with email: " + cus.getEmail();


    }

    public Customer updateCustomer(Integer id, Customer customer) {
        customer.setId(id);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

     
    public Page<Customer> getCustomers(String keyword, int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        if (keyword != null && !keyword.isEmpty()) {
            return customerRepository.searchCustomers(keyword, pageable);
        } else {
            return customerRepository.findAll(pageable);
        }
    }

	public Customer getCustomerByEmail(String email) {
		// TODO Auto-generated method stub
		return customerRepository.findByEmail(email).orElse(null);
	} 
	 public void syncCustomers(List<Customer> customers) {
	        for (Customer customer : customers) {
	            Optional<Customer> existingCustomer = customerRepository.findById(customer.getId());
	            if (existingCustomer.isPresent()) {
	                // Update existing customer
	                Customer existing = existingCustomer.get();
	                existing.setFirstName(customer.getFirstName());
	                existing.setLastName(customer.getLastName());
	                existing.setStreet(customer.getStreet());
	                existing.setAddress(customer.getAddress());
	                existing.setCity(customer.getCity());
	                existing.setState(customer.getState());
	                existing.setEmail(customer.getEmail());
	                existing.setPhone(customer.getPhone());
	                customerRepository.save(existing);
	            } else {
	                 
	                customerRepository.save(customer);
	            }
	        }
}
}
