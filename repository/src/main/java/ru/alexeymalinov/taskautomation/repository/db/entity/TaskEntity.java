package ru.alexeymalinov.taskautomation.repository.db.entity;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Table(name = "task")
@Entity
public class TaskEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "task_id_gen", sequenceName = "task_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "task_id_gen")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "value", columnDefinition = "JSONB")
    private String value;

    public TaskEntity() {
    }

    public TaskEntity(Integer id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
