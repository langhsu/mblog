/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.utils;

import com.mtons.mblog.modules.data.*;
import com.mtons.mblog.modules.entity.*;
import org.springframework.beans.BeanUtils;

/**
 * @author langhsu
 */
public class BeanMapUtils {
    private static String[] USER_IGNORE = new String[]{"password", "extend", "roles"};

    public static UserVO copy(User po) {
        if (po == null) {
            return null;
        }
        UserVO ret = new UserVO();
        BeanUtils.copyProperties(po, ret, USER_IGNORE);
        return ret;
    }

    public static AccountProfile copyPassport(User po) {
        AccountProfile passport = new AccountProfile(po.getId(), po.getUsername());
        passport.setName(po.getName());
        passport.setEmail(po.getEmail());
        passport.setAvatar(po.getAvatar());
        passport.setLastLogin(po.getLastLogin());
        passport.setStatus(po.getStatus());
        return passport;
    }

    public static CommentVO copy(Comment po) {
        CommentVO ret = new CommentVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static PostVO copy(Post po) {
        PostVO d = new PostVO();
        BeanUtils.copyProperties(po, d);
        return d;
    }

    public static MessageVO copy(Message po) {
        MessageVO ret = new MessageVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static FavoriteVO copy(Favorite po) {
        FavoriteVO ret = new FavoriteVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

}
