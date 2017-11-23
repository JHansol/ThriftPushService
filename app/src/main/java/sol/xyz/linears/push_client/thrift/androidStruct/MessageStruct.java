package sol.xyz.linears.push_client.thrift.androidStruct;

/**
 * Created by sol on 2017-11-23.
 */

public class MessageStruct {
    private int seq;
    private String title;
    private String messageBody;

    public MessageStruct(int seq,String title,String msg) {
        this.seq = seq;
        this.title = title;
        this.messageBody = msg;
    }

    public int getSeq() {
        return seq;
    }

    public String getTitle() {
        return title;
    }

    public String getMessageBody() {
        return messageBody;
    }
}
