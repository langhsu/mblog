/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.repository;

import com.mtons.mblog.modules.entity.Channel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * @author langhsu
 *
 */
public interface ChannelRepository extends JpaRepository<Channel, Integer>, JpaSpecificationExecutor<Channel> {
	List<Channel> findAllByStatus(int status, Sort sort);

	@Query("select coalesce(max(weight), 0) from Channel")
	int maxWeight();
}
