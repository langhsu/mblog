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
import com.mtons.mblog.modules.utils.BeanMapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author langhsu on 2015/8/31.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Override
    @Transactional(readOnly = true)
    public Page<MessageVO> findByOwnId(Pageable pageable, long ownId) {
        Page<Message> page = messageRepository.findAllByOwnIdOrderByIdDesc(pageable, ownId);
        List<MessageVO> rets = new ArrayList<>();

        Set<Long> postIds = new HashSet<>();
        Set<Long> fromUserIds = new HashSet<>();

        // 筛选
        page.getContent().forEach(po -> {
            MessageVO no = BeanMapUtils.copy(po);

            rets.add(no);

            if (no.getPostId() > 0) {
                postIds.add(no.getPostId());
            }
            if (no.getFromId() > 0) {
                fromUserIds.add(no.getFromId());
            }

        });

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
    public void send(MessageVO notify) {
        if (notify == null || notify.getOwnId() <=0 || notify.getFromId() <= 0) {
            return;
        }

        Message po = new Message();
        BeanUtils.copyProperties(notify, po);
        po.setCreated(new Date());

        messageRepository.save(po);
    }

    @Override
    @Transactional(readOnly = true)
    public int unread4Me(long ownId) {
        return messageRepository.countByOwnIdAndStatus(ownId, Consts.UNREAD);
    }

    @Override
    @Transactional
    public void readed4Me(long ownId) {
        messageRepository.updateReadedByOwnId(ownId);
    }
}
