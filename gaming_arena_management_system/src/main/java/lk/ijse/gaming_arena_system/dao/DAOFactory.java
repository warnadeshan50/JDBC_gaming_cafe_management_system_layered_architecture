package lk.ijse.gaming_arena_system.dao;

import lk.ijse.gaming_arena_system.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}

    public static DAOFactory getDaoFactory() {
        if (daoFactory == null) {
            return new DAOFactory();
        }
        return daoFactory;
    }
    public enum DAOTypes{
        CUSTOMER,PRODUCT,ORDER,ORDERDETAILS,SUPPLIERS,MEMBERSHIP,MEMBER,DELIVERY,CAFE,CAFEDETAILS,SUPPLIER_ITEM,EMPLOYEE,CAFE_COMPUTER;
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAILS:
                return new OrderDetailDAOImpl();
            case SUPPLIERS:
                return new SupplierDAOImpl();
            case MEMBERSHIP:
                return new MembershipDAOImpl();
            case DELIVERY:
                return new DeliveryDAOImpl();
            case CAFE:
                return new CafeDAOImpl();
            case CAFEDETAILS:
                return new CafeDetailDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case SUPPLIER_ITEM:
                return new SupplierItemDAOImpl();
            case CAFE_COMPUTER:
                return new CafeComputerDAOImpl();
            default:
                return null;
        }
    }
}
