package org.example.backend.controller;

import org.example.backend.entity.SocketMsg;
import org.example.backend.mapper.MessageMapper;
import org.example.backend.mapper.UserMapper;
import org.example.backend.utils.JwtUtils;
import org.example.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;

    public List<SocketMsg> sortByTime(List<SocketMsg> messages) {
        return messages;
    }

    @GetMapping("/history")
    public Result getMessageHistory(@RequestParam int toUid, HttpServletRequest request) {

        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        int fromUid = userMapper.findByEmail(email).getId();

        List<SocketMsg> messages1 = messageMapper.findPrivateMessageHistoryByFromUidAndToUid(fromUid, toUid);
        List<SocketMsg> messages2 = messageMapper.findPrivateMessageHistoryByFromUidAndToUid(toUid, fromUid);
        List<SocketMsg> messages = new ArrayList<>();
        messages.addAll(messages1);
        messages.addAll(messages2);

        Comparator<SocketMsg> comparator = Comparator.comparing(SocketMsg::getCreateTime);
        messages.sort(comparator);
        return Result.ok().data("messages", messages);
    }
}
