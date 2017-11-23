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

package sol.xyz.linears.push_client.fragment.dialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;

import sol.xyz.linears.push_client.fragment.dialog.common.models.IDialog;


/**
 * Component for displaying list of dialogs
 */
public class DialogsList extends RecyclerView {

    public DialogsList(Context context) {
        super(context);
    }

    public DialogsList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogsList(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        SimpleItemAnimator animator = new DefaultItemAnimator();

        setLayoutManager(layout);
        setItemAnimator(animator);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        throw new IllegalArgumentException("You can't set adapter to DialogsList. Use #setAdapter(DialogsListAdapter) instead.");
    }

    public <DIALOG extends IDialog> void setAdapter(DialogsListAdapter<DIALOG> adapter) {
        SimpleItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, true);

        setItemAnimator(itemAnimator);
        setLayoutManager(layoutManager);

        super.setAdapter(adapter);
    }

}
