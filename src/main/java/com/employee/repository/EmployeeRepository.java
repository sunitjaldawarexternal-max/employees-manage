package com.employee.repository;

import com.employee.entity.Employee;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<@NotNull Employee, @NotNull Long> {


    boolean existsByEmail(String email);

    void deleteById(int id);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findById(int id);

    @Modifying
    @Transactional
    @Query("""
                UPDATE Employee e
                SET
                    e.name = :#{#employee.name},
                    e.email = :#{#employee.email},
                    e.department = :#{#employee.department},
                    e.salary = :#{#employee.salary}
                WHERE e.id = :#{#employee.id}
            """)
    void update(int id, @Param("employee") Employee employee);
}
