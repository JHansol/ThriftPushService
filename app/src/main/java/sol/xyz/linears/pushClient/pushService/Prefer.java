package sol.xyz.linears.pushClient.pushService;

/**
 * Created by han on 2017-10-30.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sunkuan on 15/4/18.
 */
public class Prefer {
    protected static final String PREFS_FILE = "push.xml";
    private static Prefer staticInstance;

    private SharedPreferences sharedPreferences;

    private Prefer(Context context){
        sharedPreferences = context.getSharedPreferences(PREFS_FILE,0);
    }

    public static Prefer getInstance(Context context){
        if(staticInstance == null){
            staticInstance = new Prefer(context);
        }
        return staticInstance;
    }
    public int getDaemonPid(){
        return Integer.valueOf(sharedPreferences.getString("pid","-1"));
    }

    public void setDaemonPid(String pid){
        if (pid == null || pid == ""){
            pid = "-1";
        }
        sharedPreferences.edit().putString("pid", pid).commit();
    }

    public void setUUID(String uuid){
        sharedPreferences.edit().putString("uuid",uuid).commit();
    }

    public int getBadgeCount() {
        return Integer.valueOf(sharedPreferences.getString("badge","0"));
    }

    public void BadgeCountAdd() {
        int count = Integer.valueOf(sharedPreferences.getString("badge","0"));
        count++;
        sharedPreferences.edit().putString("badge", String.valueOf(count)).commit();
    }

    public void BadgeCountInit() {
        sharedPreferences.edit().putString("badge", "0").commit();
    }
}

