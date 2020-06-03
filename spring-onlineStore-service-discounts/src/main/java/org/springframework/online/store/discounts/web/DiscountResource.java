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
package org.springframework.online.store.discounts.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.online.store.discounts.model.Discount;
import org.springframework.online.store.discounts.model.DiscountRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/discounts")
@RestController
@Timed("onlinestore.discount")
@RequiredArgsConstructor
@Slf4j
public class DiscountResource {

    private final DiscountRepository discountRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Discount createItem(@Valid @RequestBody Discount discount) {
        return discountRepository.save(discount);
    }

    @GetMapping(value = "/{discountId}")
    public Optional<Discount> findItem(@PathVariable("discountId") int discountId) {
        return discountRepository.findById(discountId);
    }

    @GetMapping
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @PutMapping(value = "/{discountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(@PathVariable("discountId") int discountId, @Valid @RequestBody Discount discountRequest) {
        final Optional<Discount> discount = discountRepository.findById(discountId);

        final Discount discountModel = discount.orElseThrow(() -> new ResourceNotFoundException("Owner "+ discountId +" not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        discountModel.setItemId(discountRequest.getItemId());
        discountModel.setDiscount(discountRequest.getDiscount());
        log.info("Saving discount {}", discountModel);
        discountRepository.save(discountModel);
    }
}
