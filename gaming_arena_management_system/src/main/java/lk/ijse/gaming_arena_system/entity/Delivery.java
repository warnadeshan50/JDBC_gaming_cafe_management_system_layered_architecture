package lk.ijse.gaming_arena_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Delivery {
    private String delivery_id;
    private String order_id;
    private String deliver_name;
    private String location;
    private String contact;
}
