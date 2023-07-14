package lk.ijse.gaming_arena_system.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Membership {
    private String member_id;
    private String customer_id;
    private String type;
    private Double package_price;
}
