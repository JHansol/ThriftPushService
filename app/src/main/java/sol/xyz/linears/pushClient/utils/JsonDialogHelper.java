package sol.xyz.linears.pushClient.utils;

import org.json.JSONObject;

import java.util.Date;

import sol.xyz.linears.pushClient.fragment.dialog.model.Dialog;

/**
 * Created by sol on 2017-11-23.
 */

public class JsonDialogHelper {
    private String msg;
    public JsonDialogHelper(String msg) {
        this.msg = msg;
    }

    public String JsonDataParsing(){
        JSONObject jarray;
        try {
            jarray = new JSONObject(msg);
            String dialogMsg = jarray.getString("from");

            return dialogMsg;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Dialog JsonParsing(){
        JSONObject jarray;
        try {
            jarray = new JSONObject(msg);
            String id = jarray.getString("id");
            String dialogMsg = jarray.getString("dialogMsg");
            String dialogPhoto = jarray.getString("dialogPhoto");
            String from =  jarray.getString("from");
            String date =   jarray.getString("date");
            boolean isRead =  jarray.getBoolean("isRead");
            boolean isPay =   jarray.getBoolean("isPay");

            return new Dialog(id,dialogMsg,dialogPhoto,from,new Date(),isRead,isPay);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
