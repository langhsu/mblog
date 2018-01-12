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

import mblog.core.persist.entity.ChannelPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

/**
 * @author langhsu
 *
 */
public interface ChannelDao extends JpaRepository<ChannelPO, Integer>, JpaSpecificationExecutor<ChannelPO> {
	List<ChannelPO> findAllByStatus(int status);
	List<ChannelPO> findAllByIdIn(Collection<Integer> id);
	ChannelPO findByKey(String key);
}
