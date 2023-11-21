package by.javaguru.profiler.persistence.repository;

import by.javaguru.profiler.persistence.model.Course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
