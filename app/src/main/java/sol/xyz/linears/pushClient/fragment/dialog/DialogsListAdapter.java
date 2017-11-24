/*******************************************************************************
 * Copyright 2016 stfalcon.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package sol.xyz.linears.pushClient.fragment.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import sol.xyz.linears.pushClient.R;
import sol.xyz.linears.pushClient.fragment.dialog.common.ImageLoader;
import sol.xyz.linears.pushClient.fragment.dialog.common.ViewHolder;
import sol.xyz.linears.pushClient.fragment.dialog.common.models.IDialog;
import sol.xyz.linears.pushClient.utils.DateFormatter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Adapter for {@link DialogsList}
 */
public class DialogsListAdapter<DIALOG extends IDialog> extends RecyclerView.Adapter<DialogsListAdapter.BaseDialogViewHolder> {

    private List<DIALOG> items = new ArrayList<>();
    private int itemLayoutId;
    private Class<? extends BaseDialogViewHolder> holderClass;
    private ImageLoader imageLoader;
    private onExitClickListener<DIALOG> onExitClickListener;
    private OnDialogClickListener<DIALOG> onDialogClickListener;
    private OnDialogLongClickListener<DIALOG> onLongItemClickListener;
    private DateFormatter.Formatter datesFormatter;
    private Context context1;

    public DialogsListAdapter(ImageLoader imageLoader) {
        this(R.layout.alarm_dialog, DialogViewHolder.class, imageLoader);
    }

    public DialogsListAdapter(@LayoutRes int itemLayoutId, ImageLoader imageLoader) {
        this(itemLayoutId, DialogViewHolder.class, imageLoader);
    }

    public DialogsListAdapter(@LayoutRes int itemLayoutId, Class<? extends BaseDialogViewHolder> holderClass,
                              ImageLoader imageLoader) {
        this.itemLayoutId = itemLayoutId;
        this.holderClass = holderClass;
        this.imageLoader = imageLoader;
    }

    public DialogsListAdapter(ImageLoader imageLoader, Context background) {
        this.itemLayoutId = R.layout.alarm_dialog;
        this.holderClass = DialogViewHolder.class;
        this.imageLoader = imageLoader;
        this.context1 = background;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseDialogViewHolder holder, int position) {
        holder.setImageLoader(imageLoader);
        holder.setContext(context1);
        holder.setonExitClickListener(onExitClickListener);
        holder.setOnDialogClickListener(onDialogClickListener);
        holder.setOnLongItemClickListener(onLongItemClickListener);
        holder.setDatesFormatter(datesFormatter);
        holder.onBind(items.get(position));
    }

    @Override
    public BaseDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
        //create view holder by reflation
        try {
            Constructor<? extends BaseDialogViewHolder> constructor = holderClass.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            BaseDialogViewHolder baseDialogViewHolder = constructor.newInstance(v);
            return baseDialogViewHolder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getItemCount() {
        return items.size();
    }

    public void deleteById(String id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        if (items != null) {
            items.clear();
        }
        notifyDataSetChanged();
    }

    public void setItems(List<DIALOG> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<DIALOG> newItems) {
        if (newItems != null) {
            if (items == null) {
                items = new ArrayList<>();
            }
            int curSize = items.size();
            items.addAll(newItems);
            notifyItemRangeInserted(curSize, items.size());
        }
    }

    public void addItem(DIALOG dialog) {
        items.add(dialog);
        notifyItemInserted(0);
    }

    public void addItem(int position, DIALOG dialog) {
        items.add(position, dialog);
        notifyItemInserted(position);
    }

    public void updateItem(int position, DIALOG item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.set(position, item);
        notifyItemChanged(position);
    }

    public void updateItemById(DIALOG item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(item.getId())) {
                items.set(i, item);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public boolean updateDialogWithMessage(String dialogId, String message) {
        boolean dialogExist = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(dialogId)) {
                items.get(i).setLastMessage(message);
                notifyItemChanged(i);
                if (i != 0) {
                    Collections.swap(items, i, 0);
                    notifyItemMoved(i, 0);
                }
                dialogExist = true;
                break;
            }
        }
        return dialogExist;
    }


    public void sortByLastMessageDate() {
        Collections.sort(items, new Comparator<DIALOG>() {
            @Override
            public int compare(DIALOG o1, DIALOG o2) {
                if (o1.getCreatedAt().after(o2.getCreatedAt())) {
                    return -1;
                } else if (o1.getCreatedAt().before(o2.getCreatedAt())) {
                    return 1;
                } else return 0;
            }
        });
        notifyDataSetChanged();
    }

    public void sort(Comparator<DIALOG> comparator) {
        Collections.sort(items, comparator);
        notifyDataSetChanged();
    }


    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public OnDialogClickListener getOnDialogClickListener() {
        return onDialogClickListener;
    }

    public void setOnDialogClickListener(OnDialogClickListener<DIALOG> onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    public void setOnExitClickListener(onExitClickListener<DIALOG> onDialogClickListener) {
        this.onExitClickListener = onDialogClickListener;
    }

    public OnDialogLongClickListener getOnLongItemClickListener() {
        return onLongItemClickListener;
    }

    public void setOnDialogLongClickListener(OnDialogLongClickListener<DIALOG> onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    public void setDatesFormatter(DateFormatter.Formatter datesFormatter) {
        this.datesFormatter = datesFormatter;
    }

    public interface OnDialogClickListener<DIALOG extends IDialog> {
        void onDialogClick(DIALOG dialog);
    }

    public interface OnDialogLongClickListener<DIALOG extends IDialog> {
        void onDialogLongClick(DIALOG dialog);
    }

    public interface onExitClickListener<DIALOG extends IDialog> {
        void onExitClick(DIALOG dialog);
    }

    public abstract static class BaseDialogViewHolder<DIALOG extends IDialog> extends ViewHolder<DIALOG> {

        protected ImageLoader imageLoader;
        protected OnDialogClickListener onDialogClickListener;
        protected onExitClickListener onExitClickListener;
        protected OnDialogLongClickListener onLongItemClickListener;
        protected DateFormatter.Formatter datesFormatter;
        protected Context context1;

        void setContext(Context context1){
            this.context1 = context1;
        }
        public BaseDialogViewHolder(View itemView) {
            super(itemView);
        }

        void setImageLoader(ImageLoader imageLoader) {
            this.imageLoader = imageLoader;
        }

        void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
            this.onDialogClickListener = onDialogClickListener;
        }

        void setonExitClickListener(onExitClickListener onDialogClickListener) {
            this.onExitClickListener = onDialogClickListener;
        }

        void setOnLongItemClickListener(OnDialogLongClickListener onLongItemClickListener) {
            this.onLongItemClickListener = onLongItemClickListener;
        }

        public void setDatesFormatter(DateFormatter.Formatter dateHeadersFormatter) {
            this.datesFormatter = dateHeadersFormatter;
        }
    }

    public static class DialogViewHolder<DIALOG extends IDialog> extends BaseDialogViewHolder<DIALOG> {
        protected ViewGroup container;
        protected ViewGroup root;
        protected HtmlTextView tvName;
        protected TextView tvDate;
        protected ImageView ivAvatar;
        protected ImageView ivPay;
        protected TextView tvFrom;
        protected ImageView blurredView;
        protected TextView dialogFromDivider;
        protected View divider;

        public DialogViewHolder(View itemView) {
            super(itemView);
            root = (ViewGroup) itemView.findViewById(R.id.dialogRootLayout);
            container = (ViewGroup) itemView.findViewById(R.id.dialogContainer);
            ivAvatar = (ImageView) itemView.findViewById(R.id.dialogAvatar);

            tvName = (HtmlTextView ) itemView.findViewById(R.id.dialogName);
            tvDate = (TextView) itemView.findViewById(R.id.dialogDate);
            ivPay = (ImageView) itemView.findViewById(R.id.dialogFromImage);
            dialogFromDivider = (TextView) itemView.findViewById(R.id.dialogFromDivider);

            tvFrom = (TextView) itemView.findViewById(R.id.dialogFrom);
            divider = itemView.findViewById(R.id.dialogDivider);
            blurredView = (ImageView) itemView.findViewById(R.id.blur1);
        }

        @Override
        public void onBind(final DIALOG dialog) {

            // Set Background image
            if (blurredView != null) {
                if(dialog.isRead())
                    blurredView.setBackgroundColor(Color.rgb(245,245,245));
                else
                    blurredView.setBackgroundColor(Color.rgb(219,219,219));
            }

            ivPay.setVisibility(dialog.isPay() ? VISIBLE : GONE);
            if(!dialog.isPay()) {
                ConstraintLayout.LayoutParams plControl = (ConstraintLayout.LayoutParams) tvFrom.getLayoutParams();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context1.getResources().getDisplayMetrics());
                plControl.leftMargin = (int) px;
                tvFrom.setLayoutParams(plControl);
            }

            //Set Name
            tvName.setHtml(dialog.getDialogMsg());

            //Set Date
            String formattedDate = null;
            Date lastMessageDate = dialog.getCreatedAt();
            if (datesFormatter != null) formattedDate = datesFormatter.format(lastMessageDate);
            tvDate.setText(formattedDate == null
                    ? getDateString(lastMessageDate)
                    : formattedDate);

            //Set Dialog avatar
            if (imageLoader != null) {
                imageLoader.loadImage(ivAvatar, dialog.getDialogPhoto());
            }

            tvFrom.setText(dialog.getFrom());

            /*if (onExitClickListener != null) {
                ExitLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onExitClickListener.onExitClick(dialog);
                    }
                });
            }*/

            if (onDialogClickListener != null) {
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDialogClickListener.onDialogClick(dialog);
                    }
                });
            }

            if (onLongItemClickListener != null) {
                container.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onLongItemClickListener.onDialogLongClick(dialog);
                        return true;
                    }
                });
            }
        }

        protected String getDateString(Date date) {
            return DateFormatter.format(date, DateFormatter.Template.TIME);
        }

    }
}
