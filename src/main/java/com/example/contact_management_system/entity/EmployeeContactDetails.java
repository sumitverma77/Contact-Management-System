package com.example.contact_management_system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contacts")
public class EmployeeContactDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String phone ;
}
