package com.naver.npns.server.controller;

import com.naver.npns.server.idl.PushMessage;
import com.naver.npns.server.idl.PushReceiveService;
import com.naver.npns.server.model.PushResult;
import com.naver.npns.server.service.ClientService;
import com.naver.npns.server.service.PushService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PushController {
    private static Logger log = LoggerFactory.getLogger(PushController.class);

    @Autowired
    private PushService pushService;


    @PostMapping("/users/{uuid}/messages")
    public PushResult sendPushToSpecificDevice(@PathVariable String uuid, @RequestBody PushMessage pushMessage) {
        return pushService.sendPushToDevice(uuid, pushMessage);
    }

    @PostMapping("/broadcast-messages")
    public List<PushResult> sendPushToBroadcastDevice(@RequestBody PushMessage pushMessage) {
        return pushService.sendPushToAllDevice(pushMessage);
    }



}
