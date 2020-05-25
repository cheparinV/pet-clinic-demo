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
package org.springframework.samples.petclinic.customers.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.customers.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Maciej Szarlinski
 */
@RestController
@Timed("petclinic.pet")
@RequiredArgsConstructor
@Slf4j
class PetResource {

    private final PaymentRepository paymentRepository;
    private final ItemRepository itemRepository;


    @GetMapping("/petTypes")
    public List<PaymentType> getPetTypes() {
        return paymentRepository.findPetTypes();
    }

    @PostMapping("/items/{itemId}/pets")
    @ResponseStatus(HttpStatus.CREATED)
    public Payment processCreationForm(
        @RequestBody PetRequest petRequest,
        @PathVariable("itemId") int itemId) {

        final Payment payment = new Payment();
        final Optional<Item> optionalOwner = itemRepository.findById(itemId);
        Item item = optionalOwner.orElseThrow(() -> new ResourceNotFoundException("Owner "+itemId+" not found"));
        item.addPayment(payment);

        return save(payment, petRequest);
    }

    @PutMapping("/items/*/pets/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processUpdateForm(@RequestBody PetRequest petRequest) {
        int petId = petRequest.getId();
        Payment payment = findPetById(petId);
        save(payment, petRequest);
    }

    private Payment save(final Payment payment, final PetRequest petRequest) {

        payment.setName(petRequest.getName());
        //payment.setDate(petRequest.getDate());

        paymentRepository.findPetTypeById(petRequest.getTypeId())
            .ifPresent(payment::setType);

        log.info("Saving payment {}", payment);
        return paymentRepository.save(payment);
    }

    @GetMapping("items/*/pets/{petId}")
    public PetDetails findPet(@PathVariable("petId") int petId) {
        return new PetDetails(findPetById(petId));
    }


    private Payment findPetById(int petId) {
        Optional<Payment> pet = paymentRepository.findById(petId);
        if (!pet.isPresent()) {
            throw new ResourceNotFoundException("Payment "+petId+" not found");
        }
        return pet.get();
    }

}
