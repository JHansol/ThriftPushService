package sol.xyz.linears.pushClient.fragment.dialog.model;

import java.util.Date;

import sol.xyz.linears.pushClient.fragment.dialog.common.models.IDialog;

/*
 * Created by troy379 on 04.04.17.
 */
public class Dialog implements IDialog {

    private String id;
    private String dialogPhoto;
    private String dialogMsg;
    private String from;
    private Date date;
    private boolean isRead;
    private boolean isPay;

    public Dialog(String id, String name, String photo, String from, Date date, boolean read,boolean p) {
        this.id = id;
        this.dialogMsg = name;
        this.dialogPhoto = photo;
        this.from = from;
        this.date = date;
        this.isRead = read;
        this.isPay = p;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogMsg() {
        return dialogMsg;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public void setLastMessage(String a) {
        this.from = a;
    }

    @Override
    public Date getCreatedAt() {
        return date;
    }

    @Override
    public boolean isPay() {
        return isPay;
    }
}
