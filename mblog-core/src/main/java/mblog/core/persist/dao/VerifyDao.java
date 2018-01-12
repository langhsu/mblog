/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.persist.dao;

import mblog.core.persist.entity.VerifyPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author langhsu on 2015/8/14.
 */
public interface VerifyDao extends JpaRepository<VerifyPO, Long>, JpaSpecificationExecutor<VerifyPO> {
    VerifyPO findByUserIdAndType(long userId, int type);
    VerifyPO findByUserId(long userId);
}
