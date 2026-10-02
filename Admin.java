package model;

/**
 * Represents an Administrator. Extends Person (Inheritance).
 * In this phase, Admin credentials are provisioned once at first run
 * (see service.AuthService) rather than through a public "Add Admin"
 * menu, since the spec does not request self-registration for Admins.
 */
public class Admin extends Person {

    private static final long serialVersionUID = 1L;

    private String adminId;

    public Admin(String id, String name, String email, String password, String adminId) {
        super(id, name, email, password);
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Override
    public String displayDetails() {
        return "Admin ID : " + adminId + "\n" +
               "Name     : " + getName() + "\n" +
               "Email    : " + getEmail();
    }
}
