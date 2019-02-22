package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.data.MessageVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author langhsu
 */
public interface MessageService {

    Page<MessageVO> pagingByUserId(Pageable pageable, long userId);

    /**
     * 发送通知
     * @param message
     */
    void send(MessageVO message);

    /**
     * 未读消息数量
     * @param userId
     * @return
     */
    int unread4Me(long userId);

    /**
     * 标记为已读
     * @param userId
     */
    void readed4Me(long userId);

    /**
     * 根据文章ID清理消息
     * @param postId
     * @return
     */
    int deleteByPostId(long postId);
}
