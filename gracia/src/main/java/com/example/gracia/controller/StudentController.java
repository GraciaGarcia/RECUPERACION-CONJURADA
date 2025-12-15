package com.example.gracia.controller;

import com.example.gracia.model.Student;
import com.example.gracia.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar las operaciones CRUD de Student
 * Endpoint base: /v1/api/student
 */
@RestController
@RequestMapping("/v1/api/student")
@Tag(name = "Student", description = "API para gestion de estudiantes - Operaciones CRUD completas")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * GET - Obtiene todos los estudiantes
     * @return lista de estudiantes
     */
    @Operation(summary = "Obtener todos los estudiantes", 
               description = "Retorna una lista completa de todos los estudiantes registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de estudiantes obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    /**
     * GET - Obtiene un estudiante por su ID
     * @param id el ID del estudiante
     * @return el estudiante si existe, 404 si no
     */
    @Operation(summary = "Obtener estudiante por ID", 
               description = "Busca y retorna un estudiante especifico por su identificador unico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estudiante encontrado"),
        @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @Parameter(description = "ID del estudiante a buscar", required = true)
            @PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST - Crea un nuevo estudiante
     * @param student los datos del estudiante a crear
     * @return el estudiante creado con status 201
     */
    @Operation(summary = "Crear nuevo estudiante", 
               description = "Registra un nuevo estudiante en el sistema. La fecha se genera automaticamente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Estudiante creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<Student> createStudent(
            @Parameter(description = "Datos del estudiante a crear", required = true)
            @Valid @RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    /**
     * PUT - Actualiza un estudiante existente
     * @param id el ID del estudiante a actualizar
     * @param student los nuevos datos del estudiante
     * @return el estudiante actualizado si existe, 404 si no
     */
    @Operation(summary = "Actualizar estudiante", 
               description = "Modifica los datos de un estudiante existente. La fecha se actualiza automaticamente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Estudiante no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos en la solicitud")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @Parameter(description = "ID del estudiante a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del estudiante", required = true)
            @Valid @RequestBody Student student) {
        return studentService.updateStudent(id, student)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE - Elimina logicamente un estudiante por su ID
     * @param id el ID del estudiante a eliminar
     * @return 204 si se elimino, 404 si no existia
     */
    @Operation(summary = "Eliminar estudiante (logico)", 
               description = "Marca un estudiante como inactivo sin eliminarlo fisicamente de la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Estudiante eliminado logicamente"),
        @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "ID del estudiante a eliminar", required = true)
            @PathVariable Long id) {
        if (studentService.deleteStudent(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * PATCH - Restaura un estudiante eliminado logicamente
     * @param id el ID del estudiante a restaurar
     * @return el estudiante restaurado si existe, 404 si no
     */
    @Operation(summary = "Restaurar estudiante", 
               description = "Reactiva un estudiante que fue eliminado logicamente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estudiante restaurado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @PatchMapping("/{id}/restore")
    public ResponseEntity<Student> restoreStudent(
            @Parameter(description = "ID del estudiante a restaurar", required = true)
            @PathVariable Long id) {
        return studentService.restoreStudent(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET - Obtiene todos los estudiantes incluyendo inactivos
     * @return lista de todos los estudiantes
     */
    @Operation(summary = "Obtener todos los estudiantes (incluyendo inactivos)", 
               description = "Retorna una lista completa de todos los estudiantes, activos e inactivos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista completa obtenida exitosamente")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudentsIncludingInactive() {
        List<Student> students = studentService.getAllStudentsIncludingInactive();
        return ResponseEntity.ok(students);
    }
}
