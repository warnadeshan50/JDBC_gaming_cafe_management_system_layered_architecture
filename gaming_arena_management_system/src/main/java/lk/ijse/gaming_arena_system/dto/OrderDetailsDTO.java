package lk.ijse.gaming_arena_system.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrderDetailsDTO {
    private String oder_id;
    private String item_id;
    private Integer qty;
    private Double total_price;
}
