package lk.ijse.gaming_arena_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetail {
    private String oder_id;
    private String item_id;
    private Integer qty;
    private Double total_price;
}
