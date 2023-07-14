package lk.ijse.gaming_arena_system.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrderDTO {
    private String order_id;
    private String customer_id;
    private String date;
    private String time;
}
