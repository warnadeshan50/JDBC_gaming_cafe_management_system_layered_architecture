package lk.ijse.gaming_arena_system.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartTM {
    private String item_id;
    private String description;
    private Double onePrice;
    private Integer qty;
    private Double total;
    private Button btn;
}
