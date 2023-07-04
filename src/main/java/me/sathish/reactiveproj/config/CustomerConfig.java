package me.sathish.reactiveproj.config;

import me.sathish.reactiveproj.web.controllers.CustomerController;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;

public class CustomerConfig {
    @Bean
    public RouterFunction root(CustomerController bookHandler) {
        //        return RouterFunctions.route()
        //            .GET("/api/customers", RequestPredicates.accept(MediaType.TEXT_PLAIN),
        // bookHandler::getAllCustomers)
        //            .POST("/api/customers/monocreate",
        // RequestPredicates.contentType(MediaType.APPLICATION_JSON),
        // bookHandler::createMonoCustomer)
        //            .build();
        return null;
    }
}
