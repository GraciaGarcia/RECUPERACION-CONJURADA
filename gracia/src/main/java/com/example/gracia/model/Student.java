package com.example.gracia.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * ENTIDAD STUDENT - MODELO DE DATOS
 * 
 * Esta clase representa la tabla "students" en la base de datos H2
 * 
 * ANOTACIONES JPA:
 * @Entity : Indica que esta clase es una entidad de base de datos
 * @Table : Define el nombre de la tabla en la BD (students)
 * @Id : Marca el campo como clave primaria
 * @GeneratedValue : Genera automaticamente el ID (auto-increment)
 * @Column : Configura las propiedades de la columna en la BD
 * 
 * CAMPOS REQUERIDOS SEGUN EL ENUNCIADO:
 * - dni: Documento Nacional de Identidad (String)
 * - firstName: Nombres del estudiante (String)
 * - lastName: Apellidos del estudiante (String)
 * - promotion: Numero de promocion (Integer)
 * - date: Fecha y hora del momento (LocalDateTime)
 */
@Entity
@Table(name = "students")
@Schema(description = "Entidad que representa un estudiante en el sistema")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico del estudiante (generado automaticamente)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El DNI es obligatorio")
    @Column(unique = true, nullable = false)
    @Schema(description = "Documento Nacional de Identidad del estudiante (debe ser unico)", example = "87654321", required = true)
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    @Schema(description = "Nombres del estudiante", example = "Gracia", required = true)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false)
    @Schema(description = "Apellidos del estudiante", example = "Garcia", required = true)
    private String lastName;

    @NotNull(message = "La promocion es obligatoria")
    @Column(nullable = false)
    @Schema(description = "Numero de promocion del estudiante", example = "232", required = true)
    private Integer promotion;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora de registro/actualizacion (generada automaticamente)", example = "2025-12-13T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime date;

    @Column(nullable = false)
    @Schema(description = "Estado del registro (true = activo, false = eliminado)", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean active = true;

    // Constructor vacio requerido por JPA
    public Student() {
        this.active = true;
    }

    // Constructor con parametros
    public Student(String dni, String firstName, String lastName, Integer promotion, LocalDateTime date) {
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.promotion = promotion;
        this.date = date;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
