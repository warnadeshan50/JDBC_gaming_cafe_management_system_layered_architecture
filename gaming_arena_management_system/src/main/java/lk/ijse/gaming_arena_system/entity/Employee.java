package lk.ijse.gaming_arena_system.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    private String employee_id;
    private String employee_name;
    private String employee_address;
    private String employee_contact;
    private String employee_role;
    private Double employee_salary;
}
