package testing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @SequenceGenerator(name = "employee_seq_gen", sequenceName = "employee_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq_gen")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNum;

    private Boolean status = Boolean.FALSE;
}
