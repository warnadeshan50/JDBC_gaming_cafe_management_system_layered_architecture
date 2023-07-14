package lk.ijse.gaming_arena_system.bo;

import lk.ijse.gaming_arena_system.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBOFactory() {
        if (boFactory == null) {
            return new BOFactory();
        } else {
            return boFactory;
        }
    }
    public enum BOType{
        CAFE,MEMBERSHIP,PRODUCT,CUSTOMER,CAFE_COMPUTER,DELIVERY,EMPLOYEE,SUPPLIER,BILLING;
    }
    public SuperBO getBO(BOType type){
        switch (type){
            case CUSTOMER:
                return new CustomerBOImpl();
            case PRODUCT:
                return  new ProductBOImpl();
            case CAFE:
                return new CafeBOImpl();
            case MEMBERSHIP:
                return new MembershipBOImpl();
            case BILLING:
                return new BillingBOImpl();
            default:
                return null;
        }
    }
}
