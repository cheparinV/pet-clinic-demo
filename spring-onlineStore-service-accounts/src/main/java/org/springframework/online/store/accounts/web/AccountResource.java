/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.online.store.accounts.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.online.store.accounts.model.Account;
import org.springframework.online.store.accounts.model.AccountRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/accounts")
@RestController
@Timed("onlinestore.account")
@RequiredArgsConstructor
@Slf4j
class AccountResource {

    private final AccountRepository accountRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@Valid @RequestBody Account account) {
        return accountRepository.save(account);
    }

    @GetMapping(value = "/{accountId}")
    public Optional<Account> findAccount(@PathVariable("accountId") int accountId) {
        return accountRepository.findById(accountId);
    }

    @GetMapping
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @PutMapping(value = "/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(@PathVariable("accountId") int accountId, @Valid @RequestBody Account accountRequest) {
        final Optional<Account> item = accountRepository.findById(accountId);

        final Account accountModel = item.orElseThrow(() -> new ResourceNotFoundException("Owner "+ accountId +" not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        accountModel.setName(accountRequest.getName());
        log.info("Saving item {}", accountModel);
        accountRepository.save(accountModel);
    }
}
