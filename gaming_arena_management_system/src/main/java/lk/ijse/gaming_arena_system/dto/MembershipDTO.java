package lk.ijse.gaming_arena_system.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MembershipDTO {
    private String member_id;
    private String customer_id;
    private String type;
    private Double package_price;
}
