package com.arturFerreira.TodoList.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_priority")
public class Priority {

    @Id
    @Column(name = "priority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priorityId;

    @Column(name = "priority_name")
    private String name;

    @Column(name = "priority_value")
    private Integer value;

    public Priority() {
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
