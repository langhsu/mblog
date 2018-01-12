package mblog.core.persist.dao;

import mblog.core.persist.entity.FavorPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author langhsu on 2015/8/31.
 */
public interface FavorDao extends JpaRepository<FavorPO, Long>, JpaSpecificationExecutor<FavorPO> {
    /**
     * 指定查询
     * @param ownId
     * @param postId
     * @return
     */
    FavorPO findByOwnIdAndPostId(long ownId, long postId);

    Page<FavorPO> findAllByOwnIdOrderByCreatedDesc(Pageable pageable, long ownId);
}
