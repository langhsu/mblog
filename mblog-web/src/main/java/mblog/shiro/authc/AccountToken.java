/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.shiro.authc;

import org.apache.shiro.authc.AuthenticationToken;

public class AccountToken implements AuthenticationToken {
    private static final long serialVersionUID = 1L;

    private long id;
    private String username;
    private String nickname;

    public AccountToken() {
    }

    public AccountToken(long id, final String username) {
        this(id, username, username);
    }

    public AccountToken(long id, final String username, String nickname) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return null;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
