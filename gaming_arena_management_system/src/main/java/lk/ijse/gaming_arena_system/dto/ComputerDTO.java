package lk.ijse.gaming_arena_system.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ComputerDTO {
    private String computer_id;
    private String description;
    private Double one_hour_price;
}
