package ru.maxima.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "person_security")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 2, max = 50, message ="Min 2 chars, max 50 chars")
    private String name;

    @Min(value = 1, message = "Min 2 years")
    @Column(name = "age")
    private Integer age;

    @Column(name = "password")
    @Size(min = 8, message ="Min 8 chars, max 20 chars")
    private String password;

    @Column(name = "role")
    private String role;
}
