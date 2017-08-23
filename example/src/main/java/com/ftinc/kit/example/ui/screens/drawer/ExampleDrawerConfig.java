/*
 * Copyright (c) 2017 52inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ftinc.kit.example.ui.screens.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ftinc.kit.drawer.Config;
import com.ftinc.kit.drawer.items.ActionDrawerItem;
import com.ftinc.kit.drawer.items.DrawerItem;
import com.ftinc.kit.drawer.items.IconDrawerItem;
import com.ftinc.kit.drawer.items.SeperatorDrawerItem;
import com.ftinc.kit.example.R;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.FontLoader;
import com.ftinc.kit.widget.BezelImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static com.ftinc.kit.example.ui.screens.drawer.ExampleDrawerConfig.Item.*;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.example.ui.screens.drawer
 * Created by drew.heavner on 5/12/15.
 */
public class ExampleDrawerConfig extends Config {

    public enum Item{
        BOOKS,
        BOOKMARKS,
        FAVORITES,

        SETTINGS,
        HELP_FEEDBACK;

        public int id(){ return ordinal(); }
        public static Item from(int ordinal){
            return Item.values()[ordinal];
        }
    }

    private Context mAppContext;

    /**
     * Constructor
     * @param context
     */
    public ExampleDrawerConfig(Context context){
        mAppContext = context;
    }

    /***********************************************************************************************
     *
     * Config Methods
     *
     */

    @Override
    protected void inflateItems(List<DrawerItem> items) {

        items.add(new IconDrawerItem.Builder(BOOKS.id())
                .text(R.string.drawer_books)
                .icon(R.drawable.ic_book_black_24dp)
                .build());

        items.add(new IconDrawerItem.Builder(BOOKMARKS.id())
                .text(R.string.drawer_bookmarks)
                .icon(R.drawable.ic_bookmark_black_24dp)
                .build());

        items.add(new IconDrawerItem.Builder(FAVORITES.id())
                .text(R.string.drawer_favs)
                .icon(R.drawable.ic_loyalty_black_24dp)
                .build());

        items.add(new SeperatorDrawerItem());

        items.add(new ActionDrawerItem(SETTINGS.ordinal(), R.string.action_settings, R.drawable.ic_settings_black_24dp));
        items.add(new ActionDrawerItem(HELP_FEEDBACK.ordinal(), R.string.action_help_feedback, R.drawable.ic_forum_black_24dp));

    }

    @Override
    protected void onDrawerItemClicked(DrawerItem item) {
        Item i = Item.from(item.getId());
        Toast.makeText(mAppContext, String.format("%s was selected", i.name()), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(mAppContext, DrawerActivity.class);
        intent.putExtra(DrawerActivity.EXTRA_PAGE, item.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mAppContext.startActivity(intent);

    }

    @Override
    protected View inflateHeader(LayoutInflater inflater, ViewGroup parent) {
        return null;
        //return inflater.inflate(R.layout.navdrawer_profile_header, parent, false);
    }

    @Override
    protected void bindHeader(View headerView) {
        final ImageView cover = ButterKnife.findById(headerView, R.id.profile_cover_image);
        final BezelImageView profile = ButterKnife.findById(headerView, R.id.profile_image);
        final TextView email = ButterKnife.findById(headerView, R.id.profile_email_text);
        final TextView name = ButterKnife.findById(headerView, R.id.profile_name_text);

        Picasso.with(headerView.getContext())
                .load(R.drawable.test_cover_image)
                .placeholder(R.drawable.default_cover)
                .into(cover);

        Picasso.with(headerView.getContext())
                .load(R.drawable.test_profile_image)
                .placeholder(R.drawable.person_image_empty)
                .into(profile);

        email.setText("veedubusc@gmail.com");
        name.setText("Drew Heavner");

    }

    @Override
    protected View inflateFooter(LayoutInflater inflater, ViewGroup parent) {
        View footer = inflater.inflate(R.layout.navdrawer_item, parent, false);
        TextView title = ButterKnife.findById(footer, R.id.title);
        ImageView icon = ButterKnife.findById(footer, R.id.icon);
        icon.setVisibility(View.GONE);
        title.setText("Logout");
        title.setTextColor(parent.getResources().getColor(R.color.red_400));
        FontLoader.apply(title, Face.ROBOTO_MEDIUM);
        return footer;
    }

    @Override
    protected void bindFooter(View footerView) {
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Logout!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onInsetsChanged(View headerView, Rect insets) {
        int padding = headerView.getResources().getDimensionPixelSize(R.dimen.keyline_1);
        RelativeLayout chosenAccountContentView = ButterKnife.findById(headerView, R.id.chosen_account_content_view);
        chosenAccountContentView.setPadding(padding, insets.top + padding, padding, 0);
    }

    @Override
    protected int getStatusBarColor(Context ctx) {
        return ctx.getResources().getColor(R.color.redd);
    }

    @Override
    protected boolean shouldAnimateIndicator() {
        return false;
    }
}
