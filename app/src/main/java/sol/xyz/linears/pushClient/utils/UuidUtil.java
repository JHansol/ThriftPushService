package sol.xyz.linears.pushClient.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;

import java.util.UUID;

public class UuidUtil {
    protected static final String PREFS_FILE = "push.xml";
    protected static final String PREFS_DEVICE_ID = "uuid";
    public static int count = 0;

    public static String IsUuid(Context context) {
        UUID uuid = null;
        final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0); // getSharedPreferences 이름 정해서 쓰기 /data/data/appPacakge/push/
        final String id = prefs.getString(PREFS_DEVICE_ID, null); // PREFS_DEVICE_ID 불러오기

        if (id != null) { // 해당 값이 있을경우
            uuid = UUID.fromString(id);
            return uuid.toString();
        } else {
            return null;
        }
    }

    public static String UuidWrite(Context context) {
        UUID uuid = null;
        final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0); // getSharedPreferences 이름 정해서 쓰기 /data/data/appPacakge/shared_prefs/
        final String id = prefs.getString(PREFS_DEVICE_ID, null); // PREFS_DEVICE_ID 불러오기

        if (id != null) { // 해당 값이 있을경우
            uuid = UUID.fromString(id);
        } else {
            final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
            try {
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                } else {
                    //final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    //uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString()).commit();
        }
        return uuid.toString();
    }

}
