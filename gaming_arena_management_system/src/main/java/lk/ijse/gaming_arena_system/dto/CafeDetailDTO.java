package lk.ijse.gaming_arena_system.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CafeDetailDTO {
    private String bill_id;
    private String computer_id;
    private Double start_time;
    private Double end_time;
    private Double payment;
}
