package lk.ijse.gaming_arena_system.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CafeComputerTM {
    private String computer_id;
    private String description;
    private Double price_for_per_hour;
}
