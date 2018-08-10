package app.controller;

import app.domain.Message;
import app.domain.OnlineUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InboxController {
    @GetMapping("/inbox")
    public String viewInbox(final Model model) {
        final OnlineUser user1 = new OnlineUser();
        final OnlineUser user2 = new OnlineUser();
        final OnlineUser user3 = new OnlineUser();

        user1.setUsername("Adam");
        user2.setUsername("Wendy");
        user3.setUsername("Tom");
        final Message[] messages = new Message[3];

        messages[0] = new Message();
        messages[0].setFrom(user1);
        messages[0].setText("Establish your brand online with a custom domain name and online store. With instant access to hundreds of the best looking themes, and complete control over the look and feel, you finally have a gorgeous store of your own that reflects the personality of your business.");
        messages[1] = new Message();
        messages[1].setText("Selling your products in many places should be every bit as simple as selling in one. With Shopify’s ecommerce software, you get one unified platform to run your business with ease.");
        messages[1].setFrom(user2);
        messages[2] = new Message();
        messages[2].setText("When we say it’s never been easier to start a business, we mean it. Shopify handles everything from marketing and payments, to secure checkout and shipping. Now you can focus on the things you love.");
        messages[2].setFrom(user3);

        model.addAttribute("messages", messages);

        return "inbox";
    }

    @GetMapping("/conversation")
    public String viewConversation(@RequestParam(name = "userId", required = true) final Long userId, final Model model) {
        final OnlineUser user1 = new OnlineUser();
        final OnlineUser user2 = new OnlineUser();
        final OnlineUser user3 = new OnlineUser();

        user1.setUsername("Adam");
        user2.setUsername("Wendy");
        user3.setUsername("Tom");
        final Message[] messages = new Message[3];

        messages[0] = new Message();
        messages[0].setFrom(user1);
        messages[0].setText("Establish your brand online with a custom domain name and online store. With instant access to hundreds of the best looking themes, and complete control over the look and feel, you finally have a gorgeous store of your own that reflects the personality of your business.");
        messages[1] = new Message();
        messages[1].setText("Selling your products in many places should be every bit as simple as selling in one. With Shopify’s ecommerce software, you get one unified platform to run your business with ease.");
        messages[1].setFrom(user2);
        messages[2] = new Message();
        messages[2].setText("When we say it’s never been easier to start a business, we mean it. Shopify handles everything from marketing and payments, to secure checkout and shipping. Now you can focus on the things you love.");
        messages[2].setFrom(user1);

        model.addAttribute("conversation", messages);

        return "conversation";
    }
}