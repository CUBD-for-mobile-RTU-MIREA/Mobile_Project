package ru.realityfamily.partyapp.Domain.Model;

import java.util.Map;
import java.util.UUID;

public class Person {
    private String id;
    private String first_name;
    private String last_name;
    private String phone;
    private Role role;
    private Map<String, String> connections;

    public Person() {
        id = UUID.randomUUID().toString();
    }

    public Person(String first_name, String last_name) {
        this();
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Map<String, String> getConnections() {
        return connections;
    }

    public void setConnections(Map<String, String> connections) {
        this.connections = connections;
    }

    enum Role {
        Admin,
        Moder,
        User,
    }
}
