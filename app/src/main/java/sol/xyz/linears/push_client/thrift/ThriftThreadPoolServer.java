package sol.xyz.linears.push_client.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import sol.xyz.linears.push_client.thrift.struct.PushReceiveService;
import sol.xyz.linears.push_client.utils.HanLog;

public class ThriftThreadPoolServer implements Runnable {

    @Override
    public void run() {
        start();
    }

    public void start() {
        try {
            //PushReceiveService.Processor processor = new PushReceiveService.Processor(new ArithmeticServiceImpl());
            TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(new TServerSocket(10000));
//            serverArgs.protocolFactory(new TCompactProtocol.Factory());
//            serverArgs.transportFactory(new TFastFramedTransport.Factory());
            serverArgs.minWorkerThreads(20);
            serverArgs.maxWorkerThreads(1500);

            final ArithmeticServiceImpl arithmeticService = new ArithmeticServiceImpl();

            new Thread(arithmeticService).start();
            TProcessorFactory processorFactory = new TProcessorFactory(null) {
                @Override
                public TProcessor getProcessor(TTransport trans) {
                    arithmeticService.addClient(new PushServiceClient(trans));
                    return new PushReceiveService.Processor(arithmeticService);
                }
            };
            serverArgs.processorFactory(processorFactory);

            TServer server = new TThreadPoolServer(serverArgs);
            HanLog.han_Log("Server on, port : 10000");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}