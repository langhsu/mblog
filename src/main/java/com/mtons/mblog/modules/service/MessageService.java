package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.data.MessageVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author langhsu
 */
public interface MessageService {

    Page<MessageVO> pagingByOwnId(Pageable pageable, long ownId);

    /**
     * 发送通知
     * @param notify
     */
    void send(MessageVO notify);

    /**
     * 未读消息数量
     * @param ownId
     * @return
     */
    int unread4Me(long ownId);

    /**
     * 标记为已读
     * @param ownId
     */
    void readed4Me(long ownId);

    /**
     * 根据文章ID清理消息
     * @param postId
     * @return
     */
    int deleteByPostId(long postId);
}
