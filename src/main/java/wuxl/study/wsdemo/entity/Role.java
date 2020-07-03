package wuxl.study.wsdemo.entity;

import java.util.Set;

/**
 * @program: wsclient
 * @author: 吴小龙
 * @create: 2020-06-16 15:57
 * @description: 角色
 */

public class Role {
    private String id;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<Permissions> permissions;

    public Role(String s, String admin, Set<Permissions> permissionsSet) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }
}
