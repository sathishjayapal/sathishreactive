package me.sathish.reactiveproj.services;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import me.sathish.reactiveproj.entities.Customer;
import me.sathish.reactiveproj.model.response.PagedResult;
import me.sathish.reactiveproj.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class CustomerService {

    public static Map<Long, Customer> DATABASE = new LinkedHashMap<>();
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Flux<Customer> findAllReactCustomers() {
        if (DATABASE.size() <= 1) {
            Customer customer1 = new Customer(1L, "Sathish- Static customer");
            DATABASE.put(customer1.getId(), customer1);
        }
        return Flux.fromStream(DATABASE.values().stream());
    }

    public Mono<Customer> saveMonoCustomer(Customer customer) {
        if (DATABASE.get(customer.getId()) == null) {
            DATABASE.put(customer.getId(), customer);
        } else return Mono.error(new Throwable("Object exists"));
        return Mono.just(customer);
    }

    public PagedResult<Customer> findAllCustomers(
            int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort =
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Customer> customersPage = customerRepository.findAll(pageable);

        return new PagedResult<>(customersPage);
    }

    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
