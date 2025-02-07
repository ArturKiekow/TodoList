package com.arturFerreira.TodoList.repository;

import com.arturFerreira.TodoList.entity.Priority;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority, Long> {

    Priority findByNameIgnoreCase(String priority);

}
