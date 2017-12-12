package com.naver.npns.server.service;

import com.naver.npns.server.entity.MessageEntity;
import com.naver.npns.server.idl.PushMessage;
import com.naver.npns.server.idl.PushReceiveService;
import com.naver.npns.server.model.PushResult;
import com.naver.npns.server.repository.MessageRepository;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PushService {

    @Autowired
    private ClientService clientService;
    private int seq = 0;

    @Autowired
    private MessageRepository messageRepository;

    public int getSeq() {
        return ++seq;
    }

    public PushResult sendPushToDevice(String uuid, PushMessage pushMessage) {

        PushResult pushResult = new PushResult();
        try {
            PushReceiveService.Client client = clientService.getClient(uuid);
            pushResult.setPushResult(client.recv(pushMessage));

            MessageEntity messageEntity = new MessageEntity((long)pushMessage.getSeq(), pushMessage.getMsg().title,
                    pushMessage.getMsg().message, System.currentTimeMillis(), uuid);
            messageRepository.save(messageEntity);
        } catch (TException e) {
            e.printStackTrace();
        }
        return pushResult;
    }

    public List<PushResult> sendPushToAllDevice(@RequestBody PushMessage pushMessage) {
        List<PushResult> pushResultArrayList = new ArrayList<>();
        Map<String, PushReceiveService.Client> clientMapByUUID = clientService.getClientMapByUUID();
        for (String uuid : clientMapByUUID.keySet()) {
            pushResultArrayList.add(sendPushToDevice(uuid, pushMessage));
        }
        return pushResultArrayList;
    }
}
