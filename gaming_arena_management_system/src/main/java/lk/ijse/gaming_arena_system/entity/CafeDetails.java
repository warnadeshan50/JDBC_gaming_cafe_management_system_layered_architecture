package lk.ijse.gaming_arena_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CafeDetails {
    private String bill_id;
    private String computer_id;
    private Double start_time;
    private Double end_time;
    private Double payment;
}
