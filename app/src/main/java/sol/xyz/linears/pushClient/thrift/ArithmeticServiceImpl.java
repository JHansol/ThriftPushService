package sol.xyz.linears.pushClient.thrift;

import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import sol.xyz.linears.pushClient.pushService.PushNotifyService;
import sol.xyz.linears.pushClient.thrift.androidStruct.MessageStruct;
import sol.xyz.linears.pushClient.thrift.struct.Message;
import sol.xyz.linears.pushClient.thrift.struct.PushReceiveService;
import sol.xyz.linears.pushClient.thrift.struct.pushMessage;
import sol.xyz.linears.pushClient.utils.HanLog;

public class ArithmeticServiceImpl implements PushReceiveService.Iface, Runnable {

    private final BlockingQueue<pushMessage> messageQueue;
    private final List<PushServiceClient> clients;

    public ArithmeticServiceImpl() {
        this.messageQueue = new LinkedBlockingQueue<pushMessage>();
        this.clients = new ArrayList<PushServiceClient>();
    }

    public void addClient(PushServiceClient client) {
        // There should be some synchronization around this list
        clients.add(client);
    }

    @Override
    public void run() {
        while (true) {
            try {
                pushMessage msg = messageQueue.take();

                Iterator<PushServiceClient> clientItr = clients.iterator();
                while (clientItr.hasNext()) {
                    PushServiceClient client = clientItr.next();
                    try {
                        client.sendMessage(msg);
                    } catch (TException te) {
                        // Most likely client disconnected, should remove it from the list
                        clientItr.remove();
                    }
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void sendMessage(pushMessage msg) throws TException {
        messageQueue.add(msg);
    }

    @Override
    public String ping() throws TException {
        HanLog.han_Log("ping()");
        String pingData = "pong";
        return pingData;
    }

    @Override
    public boolean recv(pushMessage msg) {
        try {
            int seq = msg.getSeq();
            Message message = msg.getMsg();
            String title = message.getTitle();
            String messageBody = message.getMessage();
            MessageStruct sendMsg = new MessageStruct(seq,title,messageBody);

            PushNotifyService.msg_handler(sendMsg);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}