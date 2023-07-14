package lk.ijse.gaming_arena_system.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO {
    private String Employee_id;
    private String Employee_name;
    private String employee_address;
    private String employee_contact;
    private String employee_role;
    private Double salary;
}
