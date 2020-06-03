package org.springframework.online.payments.carts.web;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.online.store.payments.model.PaymentRepository;
import org.springframework.online.store.payments.web.PaymentResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Maciej Szarlinski
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(PaymentResource.class)
@ActiveProfiles("test")
class PaymentResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PaymentRepository paymentRepository;
}
