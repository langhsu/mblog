package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.MessageVO;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.entity.Message;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.modules.repository.MessageRepository;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.service.MessageService;
import com.mtons.mblog.modules.service.UserService;
import com.mtons.mblog.base.utils.BeanMapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author langhsu
 */
@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Override
    public Page<MessageVO> pagingByUserId(Pageable pageable, long userId) {
        Page<Message> page = messageRepository.findAllByUserId(pageable, userId);

        Set<Long> postIds = new HashSet<>();
        Set<Long> fromUserIds = new HashSet<>();

        // 筛选
        List<MessageVO> rets = page
                .stream()
                .map(po -> {
                    if (po.getPostId() > 0) {
                        postIds.add(po.getPostId());
                    }
                    if (po.getFromId() > 0) {
                        fromUserIds.add(po.getFromId());
                    }

                    return BeanMapUtils.copy(po);
                })
                .collect(Collectors.toList());

        // 加载
        Map<Long, PostVO> posts = postService.findMapByIds(postIds);
        Map<Long, UserVO> fromUsers = userService.findMapByIds(fromUserIds);

        rets.forEach(n -> {
            if (n.getPostId() > 0) {
                n.setPost(posts.get(n.getPostId()));
            }
            if (n.getFromId() > 0) {
                n.setFrom(fromUsers.get(n.getFromId()));
            }
        });

        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public void send(MessageVO message) {
        if (message == null || message.getUserId() <=0 || message.getFromId() <= 0) {
            return;
        }

        Message po = new Message();
        BeanUtils.copyProperties(message, po);
        po.setCreated(new Date());

        messageRepository.save(po);
    }

    @Override
    public int unread4Me(long userId) {
        return messageRepository.countByUserIdAndStatus(userId, Consts.UNREAD);
    }

    @Override
    @Transactional
    public void readed4Me(long userId) {
        messageRepository.updateReadedByUserId(userId);
    }

    @Override
    @Transactional
    public int deleteByPostId(long postId) {
        return messageRepository.deleteByPostId(postId);
    }
}
