package sol.xyz.linears.push_client.httpLib;

import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sol.xyz.linears.push_client.global.Global;
import sol.xyz.linears.push_client.utils.HanLog;


/**
 * Created by sol on 2017-11-23.
 */

public class HttpHelper {
    // https://www.freeformatter.com/javascript-escape.html#ad-output - escape
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient mClient = new OkHttpClient();

    private volatile static HttpHelper instance;
    private HttpHelper(){ } // 생성자
    public static HttpHelper getInstance(){
        if ( instance == null){ // instance 동적 할당 안됬을 경우
            synchronized (HttpHelper.class) { // 한 스레드가 메소드를 사용하기 전까지 다른스레드는 대기함.
                if(instance == null){
                    instance = new HttpHelper();
                }
            }
        }
        return instance;
    }

    private String mHostUrl;

    public void post(String url, String ip, String uuid) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ip", ip);
        params.put("uuid", uuid);
        JSONObject parameter = new JSONObject(params);

        RequestBody body = RequestBody.create(JSON, parameter.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();

        mClient.newCall(request).enqueue(callback);
    }

    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            HanLog.han_Log("[error]" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            HanLog.han_Log("[success]" + response);
        }
    };

    public void SetMyIp(){
        HttpThreadExecutor.exec(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("www.google.com", 80);
                    String mClientUrl = socket.getLocalAddress().getHostAddress();
                    HanLog.han_Log("local ip update : " + mClientUrl);
                    Global.my_ip = mClientUrl;
                    socket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void PostMyIP(){
        HttpThreadExecutor.exec(new Runnable() {
            @Override
            public void run() {
                try {
                    HanLog.han_Log("ip send.");
                    HanLog.han_Log("보낼 때 , 아이피 : " + Global.my_ip);
                    HttpHelper.getInstance().post(Global.ServerUrl,Global.my_ip,Global.uuid);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
