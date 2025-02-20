package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.GenericResponse;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer customer){
        try {
            return customerRepository.save(customer);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create customer, please check all required fields are correct");
        }
    }

    public Customer getById(int id){
        return customerRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers matching that id were found"));
    }
    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    public Customer update(int id, Customer customer){
        Customer customerToUpdate = customerRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers matching that id were found"));

        if(customer.getName() != null)
            customerToUpdate.setName(customer.getName());
        if(customer.getEmail() != null)
            customerToUpdate.setEmail(customer.getEmail());
        if(customer.getPhone() != null)
            customerToUpdate.setPhone(customer.getPhone());

        try {
            return customerRepository.save(customerToUpdate);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not update the customer's details, please check all required fields are correct");
        }
    }

    public Customer delete(int id){
        Customer customerToDelete = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers matching that id were found"));
        customerRepository.delete(customerToDelete);

        return customerToDelete;
    }
}
