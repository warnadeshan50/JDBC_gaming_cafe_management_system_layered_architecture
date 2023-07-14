package lk.ijse.gaming_arena_system.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MembershipTM {
    private String member_id;
    private String customer_id;
    private String type;
    private Double package_price;
}
