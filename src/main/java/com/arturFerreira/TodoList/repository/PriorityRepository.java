package com.arturFerreira.TodoList.repository;

import com.arturFerreira.TodoList.entity.Priority;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriorityRepository extends JpaRepository<Priority, Long> {

    Optional<Priority> findByNameIgnoreCase(String priority);

}
