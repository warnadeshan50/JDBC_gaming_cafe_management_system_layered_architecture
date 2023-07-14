package lk.ijse.gaming_arena_system.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SupplierItemsDTO {
    private String supplier_id;
    private String item_id;
    private Integer number_of_unit;
    private Double one_qty_price;
    private String date;
}
