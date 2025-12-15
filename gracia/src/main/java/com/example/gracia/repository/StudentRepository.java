package com.example.gracia.repository;

import com.example.gracia.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * REPOSITORIO DE ESTUDIANTES
 * 
 * Esta interfaz extiende JpaRepository que proporciona automaticamente
 * los metodos CRUD basicos sin necesidad de implementarlos:
 * 
 * METODOS HEREDADOS DE JpaRepository:
 * - save(Student) : Guarda o actualiza un estudiante (INSERT/UPDATE)
 * - findAll() : Obtiene todos los estudiantes (SELECT *)
 * - findById(Long) : Busca un estudiante por ID (SELECT WHERE id=?)
 * - deleteById(Long) : Elimina un estudiante por ID (DELETE WHERE id=?)
 * - count() : Cuenta el total de estudiantes (SELECT COUNT(*))
 * - existsById(Long) : Verifica si existe un estudiante (SELECT EXISTS)
 * 
 * METODOS PERSONALIZADOS:
 * Spring Data JPA genera automaticamente la implementacion basandose
 * en el nombre del metodo (Query Methods)
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    /**
     * Busca un estudiante por su DNI
     * Spring genera automaticamente: SELECT * FROM students WHERE dni = ?
     * 
     * @param dni el DNI del estudiante a buscar
     * @return Optional con el estudiante si existe, vacio si no existe
     */
    Optional<Student> findByDni(String dni);
    
    /**
     * Verifica si existe un estudiante con el DNI dado
     * Spring genera automaticamente: SELECT EXISTS(SELECT 1 FROM students WHERE dni = ?)
     * 
     * @param dni el DNI a verificar
     * @return true si existe un estudiante con ese DNI, false si no existe
     */
    boolean existsByDni(String dni);
    
    /**
     * Busca todos los estudiantes segun su estado (activo/inactivo)
     * Spring genera automaticamente: SELECT * FROM students WHERE active = ?
     * 
     * @param active true para estudiantes activos, false para inactivos
     * @return lista de estudiantes que coinciden con el estado
     */
    List<Student> findByActive(Boolean active);
}
