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

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.lang.EntityStatus;
import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.modules.repository.SecurityCodeRepository;
import com.mtons.mblog.modules.service.SecurityCodeService;
import com.mtons.mblog.modules.entity.SecurityCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author langhsu on 2015/8/14.
 */
@Service
public class SecurityCodeServiceImpl implements SecurityCodeService {
    @Autowired
    private SecurityCodeRepository securityCodeRepository;

    // 验证码存活时间 单位：分钟
    private int survivalTime = 30;

    @Override
    @Transactional
    public String generateCode(String key, int type, String target) {
        SecurityCode po = securityCodeRepository.findByKey(key);

        String code = RandomStringUtils.randomNumeric(6);
        Date now = new Date();

        if (po == null) {
            po = new SecurityCode();
            po.setKey(key);
            po.setCreated(now);
            po.setExpired(DateUtils.addMinutes(now, survivalTime));
            po.setCode(code);
            po.setType(type);
            po.setTarget(target);
        } else {

            long interval = ( now.getTime() - po.getCreated().getTime() ) / 1000;

            if (interval <= 60) {
                throw new MtonsException("发送间隔时间不能少于1分钟");
            }

            // 把 验证位 置0
            po.setStatus(EntityStatus.ENABLED);
            po.setCreated(now);
            po.setExpired(DateUtils.addMinutes(now, survivalTime));
            po.setCode(code);
            po.setType(type);
            po.setTarget(target);
        }

        securityCodeRepository.save(po);

        return code;
    }

    @Override
    @Transactional
    public boolean verify(String key, int type, String code) {
        Assert.hasLength(code, "验证码不能为空");
        SecurityCode po = securityCodeRepository.findByKeyAndType(key, type);
        Assert.notNull(po, "您没有进行过类型验证");

        Date now = new Date();

        Assert.state(now.getTime() <= po.getExpired().getTime(), "验证码已过期");
        Assert.isTrue(po.getType() == type, "验证码类型错误");
        Assert.isTrue(po.getStatus() == Consts.CODE_STATUS_INIT, "验证码已经使用过");
        Assert.state(code.equals(po.getCode()), "验证码不对");

        po.setStatus(Consts.CODE_STATUS_CERTIFIED);
        securityCodeRepository.save(po);
        return true;
    }

}
