package com.myapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.dto.AuthenticationTokenResponse;
import com.myapp.dto.LoginUserRequest;
import com.myapp.dto.RegisterRequest;
import com.myapp.dto.UserRegisterResponse;
import com.myapp.entity.Customer;
import com.myapp.entity.Role;
import com.myapp.entity.UserDto;
import com.myapp.exception.CustomerNotFoundException;
import com.myapp.exception.UserNotFoundException;
import com.myapp.exception.UserRegistrationFailedException;
import com.myapp.service.AuthenticationService;
import com.myapp.service.CustomerService;
import com.myapp.service.RemoteApiService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

     
    private AuthenticationService authenticationService;

    @Autowired
    private RemoteApiService remoteApiService;

    public CustomerController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

    }
    
    
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "register";
    }
    @GetMapping("/login")
    public String showLoginForm() {  
        return "login";
    }

    @PostMapping("/addrole")
    public ResponseEntity<Role> addRole(@RequestBody Role role) throws RoleNotFoundException {
    	log.info("Inside the addRole method of controller");
      Role role1 = authenticationService.addRole(role);
        return new ResponseEntity<Role>(role1, HttpStatus.CREATED);
    }
    
 
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerNewUserHandler(@Valid @RequestBody RegisterRequest registerRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            authenticationService.registerNewUserService(registerRequest);
            response.put("message", "User registered successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Failed to register user");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUserHandler(
            @Valid @RequestBody LoginUserRequest loginUserRequest) {
        try {
            AuthenticationTokenResponse response = authenticationService.loginUserService(loginUserRequest);

            // Prepare the response with the token and a success message
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("token", response.getToken());
            responseBody.put("message", "Login successful!");

            return ResponseEntity.ok(responseBody);
        } catch (BadCredentialsException e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Invalid email or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }


    
    @GetMapping("/customers")
    public String listCustomers(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {
        
        Page<Customer> customerPage = customerService.getCustomers(keyword, page, size, sortBy, sortDir);
        
        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("totalItems", customerPage.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        
        return "customer_list";
    }

 
    @GetMapping("/add")
    public String createCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "create_customer";
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Map<String, String>> saveCustomer(@RequestBody Customer customer) {
        Map<String, String> response = new HashMap<>();
        try {
            customerService.saveCustomer(customer);
            response.put("message", "Customer saved successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Failed to save customer");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer")
    public String getCustomerById(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            Customer customer = customerService.getCustomerById(id);
            if (customer != null) {
                model.addAttribute("customer", customer);
            } else {
                model.addAttribute("message", "Customer ID " + id + " is not present.");
            }
        }
        return "getCustomerById";
    }

    @GetMapping("/updateForm/{id}")
    public String showUpdateCustomerForm(@PathVariable Integer id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "update_customer";  
    }

    @PostMapping("/updateCustomer/{id}")
    public ResponseEntity<Map<String, String>> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        Map<String, String> response = new HashMap<>();
        try {
            customerService.updateCustomer(id, customer);
            response.put("message", "Customer updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Failed to update customer");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable Integer id) {
        Map<String, String> response = new HashMap<>();
        try {
            customerService.deleteCustomer(id);
            response.put("message", "Customer deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Failed to delete customer");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/"; // Redirect to home after logout
    }
    
    @PostMapping("/sync")
    public ResponseEntity<String> syncCustomers(@RequestBody List<Customer> customers) {
        customerService.syncCustomers(customers);
        return ResponseEntity.ok("Customers synced successfully!");
    }
    


}