package com.rajan.springrediscache.customer;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {
    private String name;
    private String email;
    private Long id;

    public Customer() {

    }

    public Customer(String name, String email, Long id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && id.equals(customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, id);
    }
}
