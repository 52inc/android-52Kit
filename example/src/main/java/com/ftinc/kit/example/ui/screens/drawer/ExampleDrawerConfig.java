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

        items.add(new IconDrawerItem(BOOKS.ordinal(), R.string.drawer_books, R.drawable.ic_book_black_24dp));
        items.add(new IconDrawerItem(BOOKMARKS.ordinal(), R.string.drawer_bookmarks, R.drawable.ic_bookmark_black_24dp));
        items.add(new IconDrawerItem(FAVORITES.ordinal(), R.string.drawer_favs, R.drawable.ic_loyalty_black_24dp));

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
        return inflater.inflate(R.layout.navdrawer_profile_header, parent, false);
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
    protected void onInsetsChanged(View headerView, Rect insets) {
        int padding = headerView.getResources().getDimensionPixelSize(R.dimen.keyline_1);
        RelativeLayout chosenAccountContentView = ButterKnife.findById(headerView, R.id.chosen_account_content_view);
        chosenAccountContentView.setPadding(padding, insets.top + padding, padding, 0);
    }
}
