package wuxl.study.wsdemo.entity;

/**
 * @program: wsclient
 * @author: 吴小龙
 * @create: 2020-06-16 15:57
 * @description: 权限
 */

public class Permissions {

    private String id;
    private String permissionsName;

    public Permissions(String id, String permissionsName) {
        this.id = id;
        this.permissionsName = permissionsName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }
}
