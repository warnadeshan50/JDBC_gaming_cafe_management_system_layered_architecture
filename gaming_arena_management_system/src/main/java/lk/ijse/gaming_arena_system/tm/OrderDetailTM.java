package lk.ijse.gaming_arena_system.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderDetailTM {
    private String order_id;
    private String item_id;
    private Integer num_of_qty;
    private Double price;
}
