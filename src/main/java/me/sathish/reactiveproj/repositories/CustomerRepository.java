package me.sathish.reactiveproj.repositories;

import me.sathish.reactiveproj.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
