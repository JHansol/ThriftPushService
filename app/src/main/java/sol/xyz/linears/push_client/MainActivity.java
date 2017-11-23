package sol.xyz.linears.push_client;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sol.xyz.linears.push_client.global.Global;
import sol.xyz.linears.push_client.httpLib.HttpHelper;
import sol.xyz.linears.push_client.pushService.Prefer;
import sol.xyz.linears.push_client.pushService.PushManager;
import sol.xyz.linears.push_client.utils.HanLog;
import sol.xyz.linears.push_client.utils.UuidUtil;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request();
        BadgeCountInit();
    }

    private void request(){
        uuid_setup();
        setClientIP();

        PushManager.initialize(getApplicationContext()); // Service Execute

        Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
        startActivity(intent);
        finish();
    }
    private void uuid_setup(){
        String uuid = UuidUtil.IsUuid(getApplicationContext());
        if ( uuid == null )
            uuid = UuidUtil.UuidWrite(getApplicationContext());
        HanLog.han_Log(uuid);
        Global.uuid = uuid;
    }
    public void setClientIP() {
        HttpHelper.getInstance().SetMyIp();
    }

    public void BadgeCountInit(){
        Prefer.getInstance(getApplicationContext()).BadgeCountInit();

        final String main = "sol.xyz.linears.push_client.MainActivity";

        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", 0);
        intent.putExtra("badge_count_package_name", getApplicationContext().getPackageName());
        intent.putExtra("badge_count_class_name", main);
        getApplicationContext().sendBroadcast(intent);
    }
}



/*
{
        "seq":0,
        "msg":{
        "title":"t_title",
        "message":"{\r\n\t\t\"id\" : \"1\",\r\n\t\t\"dialogMsg\":\"<font color=#4169e1>\uC1A1\uAE08<\/font><font color=#000000> \uC815\uD55C\uC194\uB2D8\uC774 9,300\uC6D0\uC744 \uBCF4\uB0C8\uC2B5\uB2C8\uB2E4. 11.16.13:30 \uAE4C\uC9C0 \uBC1B\uC9C0 \uC54A\uC73C\uBA74 \uC1A1\uAE08\uC774 \uC790\uB3D9 \uCDE8\uC18C \uB429\uB2C8\uB2E4.\",\r\n\t\t\"dialogPhoto\" : \"https:\/\/goo.gl\/TPtLVP\",\r\n\t\t\"from\" : \"\uC9D1\",\r\n\t\t\"date\" : \"13\",\r\n\t\t\"isRead\" : False,\r\n\t\t\"isPay\" : False\r\n\t}"
        }
        }*/
