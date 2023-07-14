package lk.ijse.gaming_arena_system.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductStatusTM {
    private String item_id;
    private String description;
    private Double onePrice;
    private String status;
}
