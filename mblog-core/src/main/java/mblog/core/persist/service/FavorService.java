package mblog.core.persist.service;

import mblog.core.data.Favor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author langhsu on 2015/8/31.
 */
public interface FavorService {
    /**
     *
     * @param userId
     * @param postId
     * @return
     */
    void add(long userId, long postId);
    void delete(long userId, long postId);

    /**
     * 分页查询用户的喜欢记录
     * @param pageable
     * @param ownId
     */
    Page<Favor> pagingByOwnId(Pageable pageable, long ownId);
}
