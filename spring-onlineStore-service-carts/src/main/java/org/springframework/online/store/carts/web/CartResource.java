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
package org.springframework.online.store.carts.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.online.store.carts.model.Cart;
import org.springframework.online.store.carts.model.CartRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Maciej Szarlinski
 */
@RequestMapping("/carts")
@RestController
@RequiredArgsConstructor
@Slf4j
@Timed("onlinestore.cart")
class CartResource {

    private final CartRepository cartRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cart createCart(@Valid @RequestBody Cart cart) {
        return cartRepository.save(cart);
    }

    @GetMapping(value = "/{cartId}")
    public Optional<Cart> findAccount(@PathVariable("cartId") int cartId) {
        return cartRepository.findById(cartId);
    }

    @GetMapping
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @PutMapping(value = "/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(@PathVariable("cartId") int cartId, @Valid @RequestBody Cart cartRequest) {
        final Optional<Cart> item = cartRepository.findById(cartId);

        final Cart cartModel = item.orElseThrow(() -> new RuntimeException("Cart "+ cartId +" not found"));
        cartModel.setUserId(cartRequest.getUserId());
        log.info("Saving item {}", cartModel);
        cartRepository.save(cartModel);
    }
}
