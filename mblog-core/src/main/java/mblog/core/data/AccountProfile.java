/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author langhsu
 *
 */
public class AccountProfile implements Serializable {
    private static final long serialVersionUID = 1748764917028425871L;
    private long id;
    private String username;
    private String avatar;
    private String name;
    private String email;

    private Date lastLogin;
    private int status;
    private boolean guest;

    private int roleId;
    private int activeEmail;

    private List<AuthMenu> authMenus;

    private BadgesCount badgesCount;

    public AccountProfile(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public List<AuthMenu> getAuthMenus() {
        return authMenus;
    }

    /**
     * 我也是拼了。。。冒泡排序法居然还能默写出来
     * @param authMenus
     */
    public void setAuthMenus(List<AuthMenu> authMenus) {
        for (int i = 0; i < authMenus.size(); i++) {
            for (int j = authMenus.size() - 1; j > 0; j--) {
                if (authMenus.get(i).getSort() > authMenus.get(j).getSort()) {
                    AuthMenu temp = authMenus.get(i);
                    authMenus.set(i, authMenus.get(j));
                    authMenus.set(j, temp);
                }
            }
        }
        this.authMenus = authMenus;
    }

    public int getActiveEmail() {
        return activeEmail;
    }

    public void setActiveEmail(int activeEmail) {
        this.activeEmail = activeEmail;
    }

    public BadgesCount getBadgesCount() {
        return badgesCount;
    }

    public void setBadgesCount(BadgesCount badgesCount) {
        this.badgesCount = badgesCount;
    }
}
