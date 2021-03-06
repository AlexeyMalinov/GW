package ru.alexeymalinov.taskautomation.orchestrator.db.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "robots_group")
public class RobotsGroupEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "robots_group_id_gen", sequenceName = "robots_group_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "robots_group_id_gen")
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "robotsGroup", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<RobotEntity> robots;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotsGroupEntity that = (RobotsGroupEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "RobotsGroupEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", robots=" + robots +
                '}';
    }
}
