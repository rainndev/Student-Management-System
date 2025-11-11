/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.model;

/**
 *
 * @author rainndev
 */
public class Role {
    private int roleID;
    private String roleName;

    
    public Role(int roleID) {
        this(roleID, "");
    }
    
    public Role(int roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }
    
    public int getRoleID (){ return roleID; }
    public String getRoleName (){ return roleName; }

    public void setRoleName (String roleName){ this.roleName = roleName;}
    public void setRoleID (int roleID){ this.roleID = roleID;}
}
