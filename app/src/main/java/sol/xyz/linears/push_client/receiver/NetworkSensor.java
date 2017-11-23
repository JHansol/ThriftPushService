package sol.xyz.linears.push_client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import sol.xyz.linears.push_client.pushService.PushNotifyService;
import sol.xyz.linears.push_client.utils.HanLog;

/**
 * Created by sol on 2017-11-24.
 */

public class NetworkSensor extends BroadcastReceiver {
    public NetworkSensor() {
        super();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action= intent.getAction();

        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            try {
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
                NetworkInfo _wifi_network =
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if(_wifi_network.isConnected() || activeNetInfo != null){
                    HanLog.han_Log("네트워크 감지");
                    PushNotifyService.network_Handler();
                }
            } catch (Exception e) {
                HanLog.han_Log("BroadCast Receiver error : " + e.getMessage());
            }
        }
    }
}
