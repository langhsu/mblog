/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.modules.data.OpenOauthVO;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.entity.OpenOauth;
import com.mtons.mblog.modules.entity.User;
import com.mtons.mblog.modules.repository.UserRepository;
import com.mtons.mblog.modules.service.OpenOauthService;
import com.mtons.mblog.modules.utils.BeanMapUtils;
import com.mtons.mblog.base.utils.MD5;
import com.mtons.mblog.modules.repository.OpenOauthRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 第三方登录授权管理
 * @author langhsu on 2015/8/12.
 */
@Service
public class OpenOauthServiceImpl implements OpenOauthService {
    @Autowired
    private OpenOauthRepository openOauthRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserVO getUserByOauthToken(String oauth_token) {
        OpenOauth thirdToken = openOauthRepository.findByAccessToken(oauth_token);
        User userPO = userRepository.findOne(thirdToken.getId());
        return BeanMapUtils.copy(userPO, 0);
    }

    @Override
    @Transactional
    public OpenOauthVO getOauthByToken(String oauth_token) {
        OpenOauth po = openOauthRepository.findByAccessToken(oauth_token);
        OpenOauthVO vo = null;
        if (po != null) {
            vo = new OpenOauthVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

    @Override
    @Transactional
    public OpenOauthVO getOauthByUid(long userId) {
        OpenOauth po = openOauthRepository.findByUserId(userId);
        OpenOauthVO vo = null;
        if (po != null) {
            vo = new OpenOauthVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

    @Override
    @Transactional
    public boolean checkIsOriginalPassword(long userId) {
        OpenOauth po = openOauthRepository.findByUserId(userId);
        if (po != null) {
            User upo = userRepository.findOne(userId);

            String pwd = MD5.md5(po.getAccessToken());
            // 判断用户密码 和 登录状态
            if (pwd.equals(upo.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void saveOauthToken(OpenOauthVO oauth) {
        OpenOauth po = new OpenOauth();
        BeanUtils.copyProperties(oauth, po);
        openOauthRepository.save(po);
    }

	@Override
	@Transactional
	public OpenOauthVO getOauthByOauthUserId(String oauthUserId) {
		OpenOauth po = openOauthRepository.findByOauthUserId(oauthUserId);
        OpenOauthVO vo = null;
        if (po != null) {
            vo = new OpenOauthVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
	}

}
