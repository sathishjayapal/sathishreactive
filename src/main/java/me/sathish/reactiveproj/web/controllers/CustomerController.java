package me.sathish.reactiveproj.web.controllers;

import lombok.extern.slf4j.Slf4j;
import me.sathish.reactiveproj.entities.Customer;
import me.sathish.reactiveproj.model.response.PagedResult;
import me.sathish.reactiveproj.services.CustomerService;
import me.sathish.reactiveproj.utils.AppConstants;
import me.sathish.reactiveproj.utils.ReactiveUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customers")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public PagedResult<Customer> getAllCustomers(
            @RequestParam(
                            value = "pageNo",
                            defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                            required = false)
                    int pageNo,
            @RequestParam(
                            value = "pageSize",
                            defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                            required = false)
                    int pageSize,
            @RequestParam(
                            value = "sortBy",
                            defaultValue = AppConstants.DEFAULT_SORT_BY,
                            required = false)
                    String sortBy,
            @RequestParam(
                            value = "sortDir",
                            defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                            required = false)
                    String sortDir) {
        return customerService.findAllCustomers(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService
                .findCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @Validated Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id, @RequestBody Customer customer) {
        return customerService
                .findCustomerById(id)
                .map(
                        customerObj -> {
                            customer.setId(id);
                            return ResponseEntity.ok(customerService.saveCustomer(customer));
                        })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        return customerService
                .findCustomerById(id)
                .map(
                        customer -> {
                            customerService.deleteCustomerById(id);
                            return ResponseEntity.ok(customer);
                        })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("reactiveCustomer")
    public Flux<Customer> getAllCustomers() {
        customerService
                .findAllReactCustomers()
                .subscribe(
                        ReactiveUtils.onNext(),
                        ReactiveUtils.onError(),
                        ReactiveUtils.onComplete());
        customerService
                .findAllReactCustomers()
                .subscribe(
                        ReactiveUtils.onNext(),
                        ReactiveUtils.onError(),
                        ReactiveUtils.onComplete());
        return customerService.findAllReactCustomers();
    }

    @PostMapping("monocreate")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> createMonoCustomer(@RequestBody Customer customer) {
        return customerService.saveMonoCustomer(customer);
    }
}
