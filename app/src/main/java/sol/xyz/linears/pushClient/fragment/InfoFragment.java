package sol.xyz.linears.pushClient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import sol.xyz.linears.pushClient.R;
import sol.xyz.linears.pushClient.fragment.dialog.DialogsList;
import sol.xyz.linears.pushClient.fragment.dialog.DialogsListAdapter;
import sol.xyz.linears.pushClient.fragment.dialog.common.ImageLoader;
import sol.xyz.linears.pushClient.fragment.dialog.fixtures.DialogsFixtures;
import sol.xyz.linears.pushClient.fragment.dialog.model.Dialog;
import sol.xyz.linears.pushClient.global.Global;
import sol.xyz.linears.pushClient.httpLib.HttpHelper;
import sol.xyz.linears.pushClient.httpLib.HttpThreadExecutor;
import sol.xyz.linears.pushClient.utils.HanLog;

public class InfoFragment extends Fragment implements DialogsListAdapter.OnDialogClickListener<Dialog>,
        DialogsListAdapter.OnDialogLongClickListener<Dialog>,DialogsListAdapter.onExitClickListener<Dialog> {

    protected ImageLoader imageLoader;
    protected DialogsListAdapter<Dialog> dialogsAdapter;
    private DialogsList dialogsList = null;

    public static android.os.Handler refresh_handler = null;

    public static final String ARG_PAGE = "ARG_PAGE";
    public static InfoFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onExitClick(Dialog dialog) {
    }

    @Override
    public void onDialogClick(Dialog dialog) {
       dialog.setRead(true);
       dialogsAdapter.setItems(Global.pushAlarmList);
    }


    @Override
    public void onDialogLongClick(Dialog dialog) {


    }

    public static void msg_handler() {
        refresh_handler.sendEmptyMessage(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view2 = inflater.inflate(R.layout.activity_default_dialogs, container, false);

        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(getContext()).load(url).into(imageView);
            }
        };

        refresh_handler = new android.os.Handler() {
            public void handleMessage(android.os.Message msg) {
                dialogsAdapter.setItems(Global.pushAlarmList);
            }
        };

        if(dialogsAdapter != null)dialogsAdapter.clear();
        dialogsAdapter = new DialogsListAdapter<>(imageLoader,getContext());
        dialogsAdapter.setItems(DialogsFixtures.getDialogs());

        dialogsAdapter.setOnExitClickListener(this);
        dialogsAdapter.setOnDialogClickListener(this);
        dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList = (DialogsList) view2.findViewById(R.id.dialogsList);

        dialogsList.setAdapter(dialogsAdapter);

        return view2;
    }
}
