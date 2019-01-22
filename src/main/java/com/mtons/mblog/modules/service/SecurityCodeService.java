/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service;

/**
 * @author langhsu on 2015/8/14.
 */
public interface SecurityCodeService {
    /**
     * 生成验证码
     * @param userId
     * @param target : email mobile
     * @return
     */
    String generateCode(long userId, int type, String target);

    /**
     * 检验验证码有效性
     * @param userId
     * @param code
     * @return token
     */
    boolean verify(long userId, int type, String code);
}
