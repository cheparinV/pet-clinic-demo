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
package org.springframework.online.store.payments.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.online.store.payments.model.Payment;
import org.springframework.online.store.payments.model.PaymentRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/payments")
@RestController
@Timed("onlinestore.payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentResource {

    private final PaymentRepository paymentRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment createItem(@Valid @RequestBody Payment payment) {
        return paymentRepository.save(payment);
    }

    @GetMapping(value = "/{paymentId}")
    public Optional<Payment> findItem(@PathVariable("paymentId") int paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @GetMapping
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @PutMapping(value = "/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(@PathVariable("paymentId") int paymentId, @Valid @RequestBody Payment paymentRequest) {
        final Optional<Payment> payment = paymentRepository.findById(paymentId);

        final Payment paymentModel = payment.orElseThrow(() -> new ResourceNotFoundException("Owner "+ paymentId +" not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        paymentModel.setName(paymentRequest.getName());
        paymentModel.setDate(paymentRequest.getDate());
        paymentModel.setType(paymentRequest.getType());
        log.info("Saving payment {}", paymentModel);
        paymentRepository.save(paymentModel);
    }
}
