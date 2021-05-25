package com.example.demo.student;

import java.awt.IllegalComponentStateException;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/students")
public class studentController {
	
	private static final List<Student> STUDENTS = Arrays.asList(
			new Student(1, "James"),
			new Student(2, "Maria"),
			new Student(3, "Anna")
			);
	@GetMapping(path = "{studentId}")
	public Student getStudent(@PathVariable("studentId") Integer studentId) {
		return STUDENTS
			.stream()
			.filter(student -> studentId.equals(student.getStudentId()))
			.findFirst()
			.orElseThrow(() -> new IllegalComponentStateException("StudentId " + studentId + " not exists"));
		
	}	
}
