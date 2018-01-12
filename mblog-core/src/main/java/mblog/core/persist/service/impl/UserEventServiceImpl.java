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

import mblog.core.persist.entity.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mblog.core.persist.dao.UserDao;
import mblog.core.persist.service.UserEventService;

import java.util.List;

/**
 * @author langhsu on 2015/8/6.
 */
@Service
public class UserEventServiceImpl implements UserEventService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public void identityPost(Long userId, long postId, boolean identity) {
        UserPO po = userDao.findOne(userId);

        if (po != null) {
            po.setPosts(po.getPosts() + ((identity) ? 1 : -1));
            userDao.save(po);
        }
    }

    @Override
    @Transactional
    public void identityComment(Long userId, long commentId, boolean identity) {
        UserPO po = userDao.findOne(userId);

        if (po != null) {
            po.setComments(po.getComments() + ((identity) ? 1 : -1));
            userDao.save(po);
        }
    }

    @Override
    @Transactional
    public void identityFollow(Long userId, long followId, boolean identity) {
        UserPO po = userDao.findOne(userId);

        if (po != null) {
            po.setFollows(po.getFollows() + ((identity) ? 1 : -1));
            userDao.save(po);
        }
    }

    @Override
    @Transactional
    public void identityFans(Long userId, long fansId, boolean identity) {
        UserPO po = userDao.findOne(userId);

        if (po != null) {
            po.setFans(po.getFans() + ((identity) ? 1 : -1));
            userDao.save(po);
        }
    }

    @Override
    @Transactional
    public void identityFavors(Long userId, boolean identity, int targetType, long targetId) {
        UserPO po = userDao.findOne(userId);

        if (po != null) {
            po.setFavors(po.getFavors() + ((identity) ? 1 : -1));
            userDao.save(po);
        }
    }
}
