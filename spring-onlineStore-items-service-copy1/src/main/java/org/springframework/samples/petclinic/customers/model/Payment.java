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
package org.springframework.samples.petclinic.customers.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "some_date")
    @Temporal(TemporalType.DATE)
    private Date someDate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PaymentType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private Item owner;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getDate() {
        return someDate;
    }

    public void setDate(final Date birthDate) {
        this.someDate = birthDate;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(final PaymentType type) {
        this.type = type;
    }

    public Item getOwner() {
        return owner;
    }

    public void setOwner(final Item owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("id", this.getId())
            .append("name", this.getName())
            .append("someDate", this.getDate())
            .append("type", this.getType().getName())
            .append("ownerFirstname", this.getOwner().getName())
            .append("ownerLastname", this.getOwner().getPrice())
            .toString();
    }

}
