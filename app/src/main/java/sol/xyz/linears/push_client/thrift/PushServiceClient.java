package sol.xyz.linears.push_client.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import sol.xyz.linears.push_client.thrift.struct.PushReceiveService;
import sol.xyz.linears.push_client.thrift.struct.pushMessage;

/**
 * This class is a stub that the server can use to send messages back
 * to the client.
 *
 * @author Joel Meyer
 */
public class PushServiceClient{
    protected final TTransport transport;
    protected final String addy;
    protected final int port;
    protected final PushReceiveService.Client client;

    public PushServiceClient(TTransport transport) {
        TSocket tsocket = (TSocket)transport;
        this.transport = transport;

        this.client = new PushReceiveService.Client(new TCompactProtocol(transport));
        this.addy = tsocket.getSocket().getInetAddress().getHostAddress();
        this.port = tsocket.getSocket().getPort();

    }

    public String getAddy() {
        return addy;
    }

    public void sendMessage(pushMessage msg) throws TException {
        System.out.println("sendMessage()");
        //this.client.getStruct(1);
        //this.client.send_getStruct(123);
        //this.client.getStruct(9);
    }
}