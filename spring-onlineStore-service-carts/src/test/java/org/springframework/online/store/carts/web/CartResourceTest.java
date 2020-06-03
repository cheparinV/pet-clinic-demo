package org.springframework.online.store.carts.web;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.online.store.carts.model.CartRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartResource.class)
@ActiveProfiles("test")
class CartResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CartRepository cartRepository;

}
