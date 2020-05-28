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
package org.springframework.online.store.inventory.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.online.store.inventory.model.Item;
import org.springframework.online.store.inventory.model.ItemRepository;
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

@RequestMapping("/items")
@RestController
@Timed("petclinic.item")
@RequiredArgsConstructor
@Slf4j
class ItemResource {

    private final ItemRepository itemRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(@Valid @RequestBody Item item) {
        return itemRepository.save(item);
    }

    @GetMapping(value = "/{itemId}")
    public Optional<Item> findItem(@PathVariable("itemId") int itemId) {
        return itemRepository.findById(itemId);
    }

    @GetMapping
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @PutMapping(value = "/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(@PathVariable("itemId") int itemId, @Valid @RequestBody Item itemRequest) {
        final Optional<Item> item = itemRepository.findById(itemId);

        final Item itemModel = item.orElseThrow(() -> new ResourceNotFoundException("Owner " + itemId + " not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        itemModel.setName(itemRequest.getName());
        itemModel.setPrice(itemRequest.getPrice());
        itemModel.setDescription(itemRequest.getDescription());
        log.info("Saving item {}", itemModel);
        itemRepository.save(itemModel);
    }
}
