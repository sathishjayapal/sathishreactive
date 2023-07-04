package me.sathish.reactiveproj.client;

import me.sathish.reactiveproj.entities.Customer;
import org.springframework.web.reactive.function.client.WebClient;

public class CustomerWebClient {
    private WebClient client = WebClient.create("http://localhost:8080");

    public void fetchBookByIdDemo() {
        int id = 101;
        client.get()
                .uri("/api/customers")
                .exchange()
                .flatMap(res -> res.bodyToMono(String.class))
                .subscribe(customer -> System.out.println("GET: " + customer));
    }

    public void createBookDemo() {
        client.post()
                .uri("/create")
                .bodyValue(new Customer(1L, "Sathish"))
                .exchange()
                .flatMap(res -> res.bodyToMono(Customer.class))
                .subscribe(
                        book ->
                                System.out.println(
                                        "POST: " + book.getId() + ", " + book.getText()));
    }
}
