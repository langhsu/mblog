package mblog.core.persist.dao;

import mblog.core.persist.entity.NotifyPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author langhsu on 2015/8/31.
 */
public interface NotifyDao extends JpaRepository<NotifyPO, Long>, JpaSpecificationExecutor<NotifyPO> {
    Page<NotifyPO> findAllByOwnIdOrderByIdDesc(Pageable pageable, long ownId);
    /**
     * 查询我的未读消息
     * @param ownId
     * @return
     */
    int countByOwnIdAndStatus(long ownId, int status);

    /**
     * 标记我的消息为已读
     */
    @Modifying
    @Transactional
    @Query("update NotifyPO n set n.status = 1 where n.status = 0 and n.ownId = :id")
    int updateReadedByOwnId(@Param("id") Long id);
}
