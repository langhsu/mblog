/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.persist.service.impl;

import mblog.base.lang.Consts;
import mblog.base.lang.EntityStatus;
import mblog.base.lang.MtonsException;
import mblog.core.persist.dao.VerifyDao;
import mblog.core.persist.entity.VerifyPO;
import mblog.core.persist.service.VerifyService;
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
public class VerifyServiceImpl implements VerifyService {
    @Autowired
    private VerifyDao verifyDao;

    // 验证码存活时间 单位：分钟
    private int survivalTime = 30;

    @Override
    @Transactional
    public String generateCode(long userId, int type, String target) {
        VerifyPO po = verifyDao.findByUserId(userId);

        String code = RandomStringUtils.randomNumeric(10);
        Date now = new Date();

        if (po == null) {
            po = new VerifyPO();
            po.setUserId(userId);
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

        verifyDao.save(po);

        return code;
    }

    @Override
    @Transactional
    public String verify(long userId, int type, String code) {
        Assert.hasLength(code, "验证码不能为空");

        VerifyPO po = verifyDao.findByUserIdAndType(userId, type);

        Assert.notNull(po, "您没有进行过类型验证");

        Date now = new Date();

        Assert.state(now.getTime() <= po.getExpired().getTime(), "验证码已过期");

        Assert.isTrue(po.getStatus() == Consts.VERIFY_STATUS_INIT, "验证码已经使用过");

        Assert.state(code.equals(po.getCode()), "验证码不对");

        String token = RandomStringUtils.randomNumeric(8);
        po.setToken(token);

        po.setStatus(Consts.VERIFY_STATUS_TOKEN);

        verifyDao.save(po);
        return token;
    }

    @Override
    @Transactional
    public void verifyToken(long userId, int type, String token) {
        Assert.hasLength(token, "验证码不能为空");

        VerifyPO po = verifyDao.findByUserIdAndType(userId, type);

        Assert.notNull(po, "您没有进行过类型验证");

        Assert.isTrue(po.getStatus() == Consts.VERIFY_STATUS_TOKEN, "操作步骤不对");

        Assert.state(token.equals(po.getToken()), "令牌不对");

        po.setStatus(Consts.VERIFY_STATUS_CERTIFIED);

        verifyDao.save(po);
    }
}
