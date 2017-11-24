package sol.xyz.linears.pushClient.global;

import android.app.Application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import sol.xyz.linears.pushClient.fragment.dialog.model.Dialog;

/**
 * Created by han on 2017-11-20.
 */

public class Global extends Application {
    //public static String ServerUrl = "http://10.83.37.21:18090/request_connect/";
    public static String ServerUrl = "http://10.83.32.40:18090/request_connect/";
    public static String my_ip;
    public static String uuid;
    public static List<Dialog> pushAlarmList;

    @Override
    public void onCreate() {
        super.onCreate();
        pushAlarmList = new LinkedList<>();
    }
}
