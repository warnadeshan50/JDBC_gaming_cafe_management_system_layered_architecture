package lk.ijse.gaming_arena_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SupplierItem {
   private String supplier_id;
   private String item_id;
   private Integer number_of_unit;
   private Double one_qty_price;
   private String date;
}
