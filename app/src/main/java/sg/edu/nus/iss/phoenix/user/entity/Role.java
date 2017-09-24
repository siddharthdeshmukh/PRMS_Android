package sg.edu.nus.iss.phoenix.user.entity;

/**
 * Created by siddharth on 9/23/2017.
 */

public class Role {

    public static final String ADMIN = "admin";
    public static final String MANAGER = "manager";
    public static final String PRESENTER = "presenter";
    public static final String PRODUCER = "producer";


    private String role;
    private String accessPrivilege;

    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccessPrivilege() {
        return accessPrivilege;
    }

    public void setAccessPrivilege(String accessPrivilege) {
        this.accessPrivilege = accessPrivilege;
    }

}
