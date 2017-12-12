package com.naver.npns.server.controller;

import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.naver.npns.server.idl.Message;
import com.naver.npns.server.idl.PushMessage;
import com.naver.npns.server.idl.PushReceiveService;
import com.naver.npns.server.model.PushResult;
import com.naver.npns.server.service.ClientService;
import com.naver.npns.server.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {

    private static Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ClientService clientService;
    @Autowired
    private PushService pushService;

    @RequestMapping("/admin")
    public ModelAndView admin() {

        ModelAndView modelAndView = new ModelAndView("admin"); //as per view resolver
        modelAndView.addObject("userList", clientService.getClientMapByUUID());
        return modelAndView;
    }

    @RequestMapping(value ="/admin/messages")
    public ModelAndView adminMessges(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("admin_messages");

        Map<String, PushReceiveService.Client> clientMapByUUID = clientService.getClientMapByUUID();
        Map<String, PushResult> pushResultMap = new HashMap<>();

        for(String uuid : clientMapByUUID.keySet()) {
            if(request.getParameter(uuid).equals("on")) {

                PushMessage pushMessage = new PushMessage();
                Message message = new Message();
                message.setTitle(request.getParameter("push_messages_title"));
                message.setMessage(request.getParameter("push_messages_content"));
                pushMessage.setSeq(pushService.getSeq());
                pushMessage.setMsg(message);
                PushResult pushResult = pushService.sendPushToDevice(uuid, pushMessage);
                pushResultMap.put(uuid, pushResult);

                log.warn("pushMessage = {}" , pushMessage);
                log.warn("on uuid is {} ", uuid);
            }
        }

        modelAndView.addObject("pushResult", pushResultMap);

        return modelAndView;

    }
}
