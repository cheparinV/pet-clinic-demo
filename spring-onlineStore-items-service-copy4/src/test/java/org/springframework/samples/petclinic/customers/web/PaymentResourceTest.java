package org.springframework.samples.petclinic.customers.web;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customers.model.*;
import org.springframework.samples.petclinic.customers.model.Payment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Maciej Szarlinski
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(PetResource.class)
@ActiveProfiles("test")
class PaymentResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PaymentRepository paymentRepository;

    @MockBean
    ItemRepository itemRepository;

    @Test
    void shouldGetAPetInJSonFormat() throws Exception {

        Payment payment = setupPet();

        given(paymentRepository.findById(2)).willReturn(Optional.of(payment));


        mvc.perform(get("/items/2/pets/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("Basil"))
            .andExpect(jsonPath("$.type.id").value(6));
    }

    private Payment setupPet() {
        Item owner = new Item();
        owner.setName("George");
        owner.setPrice("Bush");

        Payment payment = new Payment();

        payment.setName("Basil");
        payment.setId(2);

        PaymentType paymentType = new PaymentType();
        paymentType.setId(6);
        payment.setType(paymentType);

        owner.addPayment(payment);
        return payment;
    }
}
