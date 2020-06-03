package org.springframework.online.store.accounts.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.online.store.accounts.model.Account;
import org.springframework.online.store.accounts.model.AccountRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Maciej Szarlinski
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountResource.class)
@ActiveProfiles("test")
class AccountResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AccountRepository accountRepository;

    @Test
    void shouldGetAPetInJSonFormat() throws Exception {

        Account owner = setupPet();

        given(accountRepository.findById(2)).willReturn(Optional.of(owner));


        mvc.perform(get("/accounts/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.name").value("George"));
    }

    private Account setupPet() {
        Account owner = new Account();
        owner.setName("George");
        return owner;
    }
}
