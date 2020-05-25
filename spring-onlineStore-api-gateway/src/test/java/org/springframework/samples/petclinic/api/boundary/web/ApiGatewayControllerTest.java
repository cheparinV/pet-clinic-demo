package org.springframework.samples.petclinic.api.boundary.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.samples.petclinic.api.application.InventoryServiceClient;
import org.springframework.samples.petclinic.api.application.VisitsServiceClient;
import org.springframework.samples.petclinic.api.dto.ItemDetails;
import org.springframework.samples.petclinic.api.dto.PaymentDetails;
import org.springframework.samples.petclinic.api.dto.VisitDetails;
import org.springframework.samples.petclinic.api.dto.Visits;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ApiGatewayController.class)
@Import(ReactiveResilience4JAutoConfiguration.class)
class ApiGatewayControllerTest {

    @MockBean
    private InventoryServiceClient inventoryServiceClient;

    @MockBean
    private VisitsServiceClient visitsServiceClient;

    @Autowired
    private WebTestClient client;


    @Test
    void getOwnerDetails_withAvailableVisitsService() {
        ItemDetails owner = new ItemDetails();
        PaymentDetails cat = new PaymentDetails();
        cat.setId(20);
        cat.setName("Garfield");
        owner.getPayments().add(cat);
        Mockito
            .when(inventoryServiceClient.getItem(1))
            .thenReturn(Mono.just(owner));

        Visits visits = new Visits();
        VisitDetails visit = new VisitDetails();
        visit.setId(300);
        visit.setDescription("First visit");
        visit.setPetId(cat.getId());
        visits.getItems().add(visit);
        Mockito
            .when(visitsServiceClient.getVisitsForPets(Collections.singletonList(cat.getId())))
            .thenReturn(Mono.just(visits));

        client.get()
            .uri("/api/gateway/owners/1")
            .exchange()
            .expectStatus().isOk()
            //.expectBody(String.class)
            //.consumeWith(response ->
            //    Assertions.assertThat(response.getResponseBody()).isEqualTo("Garfield"));
            .expectBody()
            .jsonPath("$.pets[0].name").isEqualTo("Garfield")
            .jsonPath("$.pets[0].visits[0].description").isEqualTo("First visit");
    }

    /**
     * Test Resilience4j fallback method
     */
    @Test
    void getOwnerDetails_withServiceError() {
        ItemDetails owner = new ItemDetails();
        PaymentDetails cat = new PaymentDetails();
        cat.setId(20);
        cat.setName("Garfield");
        owner.getPayments().add(cat);
        Mockito
            .when(inventoryServiceClient.getItem(1))
            .thenReturn(Mono.just(owner));

        Mockito
            .when(visitsServiceClient.getVisitsForPets(Collections.singletonList(cat.getId())))
            .thenReturn(Mono.error(new ConnectException("Simulate error")));

        client.get()
            .uri("/api/gateway/owners/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.pets[0].name").isEqualTo("Garfield")
            .jsonPath("$.pets[0].visits").isEmpty();
    }

}
