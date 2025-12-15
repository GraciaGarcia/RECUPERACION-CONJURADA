package com.example.gracia.service;

import com.example.gracia.model.Student;
import com.example.gracia.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la logica de negocio para Student
 */
@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Obtiene todos los estudiantes activos
     * @return lista de estudiantes activos
     */
    public List<Student> getAllStudents() {
        logger.info("Invocar: Obteniendo todos los estudiantes activos");
        List<Student> students = studentRepository.findByActive(true);
        logger.info("Invocar: Se encontraron {} estudiantes activos", students.size());
        return students;
    }
    
    /**
     * Obtiene todos los estudiantes (incluyendo inactivos)
     * @return lista de todos los estudiantes
     */
    public List<Student> getAllStudentsIncludingInactive() {
        logger.info("Invocar: Obteniendo todos los estudiantes (incluyendo inactivos)");
        List<Student> students = studentRepository.findAll();
        logger.info("Invocar: Se encontraron {} estudiantes en total", students.size());
        return students;
    }

    /**
     * Obtiene un estudiante por su ID
     * @param id el ID del estudiante
     * @return Optional con el estudiante si existe
     */
    public Optional<Student> getStudentById(Long id) {
        logger.info("Invocar: Buscando estudiante con ID: {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            Student s = student.get();
            logger.info("Invocar: Estudiante encontrado - DNI: {}, Nombre: {} {}, Promocion: {}", 
                s.getDni(), s.getFirstName(), s.getLastName(), s.getPromotion());
        } else {
            logger.warn("Invocar: No se encontro estudiante con ID: {}", id);
        }
        return student;
    }

    /**
     * Crea un nuevo estudiante
     * @param student el estudiante a crear
     * @return el estudiante creado
     */
    public Student createStudent(Student student) {
        // Establecer la fecha y hora actual y estado activo
        student.setDate(LocalDateTime.now());
        student.setActive(true);
        
        logger.info("Registrar: Creando nuevo estudiante - DNI: {}, Nombre: {} {}, Promocion: {}, Fecha: {}", 
            student.getDni(), student.getFirstName(), student.getLastName(), 
            student.getPromotion(), student.getDate());
        
        Student savedStudent = studentRepository.save(student);
        
        logger.info("Registrar: Estudiante creado exitosamente con ID: {}", savedStudent.getId());
        return savedStudent;
    }

    /**
     * Actualiza un estudiante existente
     * @param id el ID del estudiante a actualizar
     * @param studentDetails los nuevos datos del estudiante
     * @return Optional con el estudiante actualizado si existe
     */
    public Optional<Student> updateStudent(Long id, Student studentDetails) {
        logger.info("Actualizar: Intentando actualizar estudiante con ID: {}", id);
        
        return studentRepository.findById(id).map(student -> {
            logger.info("Actualizar: Datos anteriores - DNI: {}, Nombre: {} {}, Promocion: {}", 
                student.getDni(), student.getFirstName(), student.getLastName(), student.getPromotion());
            
            student.setDni(studentDetails.getDni());
            student.setFirstName(studentDetails.getFirstName());
            student.setLastName(studentDetails.getLastName());
            student.setPromotion(studentDetails.getPromotion());
            student.setDate(LocalDateTime.now());
            
            Student updatedStudent = studentRepository.save(student);
            
            logger.info("Actualizar: Datos nuevos - DNI: {}, Nombre: {} {}, Promocion: {}, Fecha: {}", 
                updatedStudent.getDni(), updatedStudent.getFirstName(), 
                updatedStudent.getLastName(), updatedStudent.getPromotion(), updatedStudent.getDate());
            
            return updatedStudent;
        });
    }

    /**
     * Elimina logicamente un estudiante por su ID (soft delete)
     * @param id el ID del estudiante a eliminar
     * @return true si se elimino, false si no existia
     */
    public boolean deleteStudent(Long id) {
        logger.info("Eliminar (Logico): Intentando eliminar logicamente estudiante con ID: {}", id);
        
        return studentRepository.findById(id).map(student -> {
            logger.info("Eliminar (Logico): Marcando como inactivo - DNI: {}, Nombre: {} {}, Promocion: {}", 
                student.getDni(), student.getFirstName(), student.getLastName(), student.getPromotion());
            
            student.setActive(false);
            student.setDate(LocalDateTime.now());
            studentRepository.save(student);
            
            logger.info("Eliminar (Logico): Estudiante con ID: {} marcado como inactivo exitosamente", id);
            return true;
        }).orElseGet(() -> {
            logger.warn("Eliminar (Logico): No se encontro estudiante con ID: {}", id);
            return false;
        });
    }
    
    /**
     * Elimina fisicamente un estudiante por su ID (hard delete)
     * @param id el ID del estudiante a eliminar
     * @return true si se elimino, false si no existia
     */
    public boolean hardDeleteStudent(Long id) {
        logger.info("Eliminar (Fisico): Intentando eliminar fisicamente estudiante con ID: {}", id);
        
        return studentRepository.findById(id).map(student -> {
            logger.info("Eliminar (Fisico): Eliminando permanentemente - DNI: {}, Nombre: {} {}, Promocion: {}", 
                student.getDni(), student.getFirstName(), student.getLastName(), student.getPromotion());
            
            studentRepository.delete(student);
            
            logger.info("Eliminar (Fisico): Estudiante con ID: {} eliminado permanentemente", id);
            return true;
        }).orElseGet(() -> {
            logger.warn("Eliminar (Fisico): No se encontro estudiante con ID: {}", id);
            return false;
        });
    }
    
    /**
     * Restaura un estudiante eliminado logicamente
     * @param id el ID del estudiante a restaurar
     * @return Optional con el estudiante restaurado si existe
     */
    public Optional<Student> restoreStudent(Long id) {
        logger.info("Restaurar: Intentando restaurar estudiante con ID: {}", id);
        
        return studentRepository.findById(id).map(student -> {
            logger.info("Restaurar: Reactivando estudiante - DNI: {}, Nombre: {} {}, Promocion: {}", 
                student.getDni(), student.getFirstName(), student.getLastName(), student.getPromotion());
            
            student.setActive(true);
            student.setDate(LocalDateTime.now());
            Student restoredStudent = studentRepository.save(student);
            
            logger.info("Restaurar: Estudiante con ID: {} restaurado exitosamente", id);
            return restoredStudent;
        });
    }
}
