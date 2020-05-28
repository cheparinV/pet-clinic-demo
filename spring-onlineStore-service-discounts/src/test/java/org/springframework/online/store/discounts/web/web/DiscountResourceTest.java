package org.springframework.online.store.discounts.web.web;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.online.store.discounts.model.DiscountRepository;
import org.springframework.online.store.discounts.web.DiscountResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Maciej Szarlinski
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(DiscountResource.class)
@ActiveProfiles("test")
class DiscountResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    DiscountRepository discountRepository;

}
