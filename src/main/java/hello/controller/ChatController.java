package hello.controller;

import hello.domain.Message;
import hello.domain.OutputMessage;
import hello.manager.OnlineUsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChatController {
    private OnlineUsersManager onlineUsersManager;

    @Autowired
    public ChatController(final OnlineUsersManager onlineUsersManager) {
        this.onlineUsersManager = onlineUsersManager;
    }

    @MessageMapping("/chat/{topic}")
    @SendTo("/topic/messages")
    public OutputMessage send(@DestinationVariable("topic") String topic, Message message) throws Exception {
        if (topic.equals("login")) {
            onlineUsersManager.addOnlineUser(message.getFrom());
        }

        return new OutputMessage(message.getFrom().getUsername(), message.getText(), topic);
    }

    @GetMapping("/onlineusers")
    @ResponseBody
    public String getOnlineUsers() {
        return onlineUsersManager.getOnlineUsers().toString();
    }
}