package uz.perevods.perevod.entitiy.authorization;

public class UserRoleResponse {
    private String fullName;
    private String roleName;
    private Integer role;

    public UserRoleResponse() {
    }

    public UserRoleResponse(String fullName, String roleName, Integer role) {
        this.fullName = fullName;
        this.roleName = roleName;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
