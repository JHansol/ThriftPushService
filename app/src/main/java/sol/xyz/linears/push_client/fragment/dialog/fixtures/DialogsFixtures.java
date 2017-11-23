package sol.xyz.linears.push_client.fragment.dialog.fixtures;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import sol.xyz.linears.push_client.fragment.dialog.model.Dialog;
import sol.xyz.linears.push_client.global.Global;

/*
 * Created by Anton Bevza on 07.09.16.
 */
public final class DialogsFixtures {
    private DialogsFixtures() {
        throw new AssertionError();
    }

    public static ArrayList<Dialog> getDialogs() {
        ArrayList list = new ArrayList<>();

        for(int i=0;i< Global.pushAlarmList.size();i++){
            Dialog dialog = Global.pushAlarmList.get(i);

            list.add(setDialog(dialog.getId(),dialog.getDialogMsg(),dialog.getDialogPhoto(),
                    dialog.getFrom(),dialog.getCreatedAt(), dialog.isRead(),dialog.isPay()));
        }
        return list;
    }

    private static Dialog setDialog(String id,String msg,String ImageURL,String from,Date time, Boolean read,Boolean pay){
        return new Dialog(id,msg,ImageURL,from,time,read,pay);
    }
    private static Dialog testGetDialog() {
        String a = "https://lh3.googleusercontent.com/NkWmYtXH8OO7QbLbpxp9p4cj5WGdhRiMqnCNBjknlljMSwij3LyXIX9aYzO3hZVO2g=w300";
        String name = "<font color=#4169e1>송금</font><font color=#000000> 정한솔님이 9,300원을 보냈습니다. 11.16.13:30 까지 받지 않으면 송금이 자동 취소 됩니다.";
        return new Dialog("1",name,a,"네이버페이 송금",getTime("2017-05-21T14:54:14.311Z"),false,true);
    }

    private static Date getTime(String time){
        String times = time;
        StringTokenizer tokenize = new StringTokenizer(times, "T");
        StringTokenizer tokenizer = new StringTokenizer(tokenize.nextToken(), "-");
        StringTokenizer tokenizer2 = new StringTokenizer(tokenize.nextToken(), ":");
        int y = Integer.parseInt(tokenizer.nextToken());
        int m = Integer.parseInt(tokenizer.nextToken())-1;
        int d = Integer.parseInt(tokenizer.nextToken());
        int h = Integer.parseInt(tokenizer2.nextToken());
        int mz = Integer.parseInt(tokenizer2.nextToken());
        Calendar ca = new GregorianCalendar(y,m,d,h,mz);
        return ca.getTime();
    }

}
