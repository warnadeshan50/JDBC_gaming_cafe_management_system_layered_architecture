package lk.ijse.gaming_arena_system.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DeliveryDTO {
    private String delivery_id;
    private String order_id;
    private String delivery_name;
    private String location;
    private String contact;
}
