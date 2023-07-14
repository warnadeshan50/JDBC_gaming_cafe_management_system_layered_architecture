package lk.ijse.gaming_arena_system.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductTM {
    private String item_id;
    private String description;
    private String type;
    private Integer on_hand_qty;
    private Double one_qty_price;
}
