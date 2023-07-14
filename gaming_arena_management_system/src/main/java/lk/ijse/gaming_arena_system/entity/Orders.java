package lk.ijse.gaming_arena_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Orders {
    private String order_id;
    private String customer_id;
    private String date;
    private String time;
}
