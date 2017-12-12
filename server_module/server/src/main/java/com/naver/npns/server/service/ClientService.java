package com.naver.npns.server.service;

import com.naver.npns.server.idl.PushReceiveService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class ClientService {
    private static Map<String, PushReceiveService.Client> clientMapByUUID = new HashMap<>();

    public static Map<String, PushReceiveService.Client> getClientMapByUUID() {
        return clientMapByUUID;
    }

    public PushReceiveService.Client getClient(String uuid) {
        return clientMapByUUID.get(uuid);
    }

    public void putClient(String uuid, PushReceiveService.Client client) {
        clientMapByUUID.put(uuid, client);
    }
}
