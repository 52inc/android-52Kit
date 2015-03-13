package com.ftinc.kit.ui.winds.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftinc.kit.R;
import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.FontLoader;
import com.ftinc.kit.ui.attributr.model.Library;
import com.ftinc.kit.ui.winds.model.Change;
import com.ftinc.kit.ui.winds.model.ChangeLog;
import com.ftinc.kit.ui.winds.model.Version;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.ui.winds.ui
 * Created by drew.heavner on 3/13/15.
 */
public class ChangeLogAdapter extends BetterRecyclerAdapter<Change, ChangeLogAdapter.VersionViewHolder>
        implements StickyRecyclerHeadersAdapter<ChangeLogAdapter.HeaderViewHolder> {

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private List<List<Change>> mHeaders = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private ChangeLog mChangeLog;

    /**
     * Set the changelog for this adapter
     * @param log
     */
    public void setChangeLog(ChangeLog log){
        mChangeLog = log;
    }

    /***********************************************************************************************
     *
     * Adapter Methods
     *
     */

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {

    }

    /***********************************************************************************************
     *
     * Sticky Header Methods
     *
     */


    @Override
    public long getHeaderId(int i) {
        if(i < getItemCount()) {

            Change data = getItem(i);

            for (List<Change> items : mHeaders) {
                if (items.contains(data)) {
                    return mHeaders.indexOf(items);
                }
            }
        }

        return -1;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_sticky_subheader, viewGroup, false);
        return new HeaderViewHolder(itemView);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int i) {
        long id = getHeaderId(i);
        String artist = mTitles.get((int)id);
        holder.title.setText(artist);
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    @Override
    protected void onFiltered() {
        buildSectionHeaders();
    }

    /**
     * Build the section headers for use in creating the headers
     */
    private void buildSectionHeaders(){
        // Update Artist maps
        HashMap<String, List<Change>> currMap = new HashMap<>();

//        // Loop through tuneRefs
//        for(int i=0; i<getItemCount(); i++){
//            Change version = getItem(i);
//
//            String license = version.type;
//
//            List<Version> libraries = currMap.get(license);
//            if(libraries != null){
//                libraries.add(version);
//                currMap.put(license, libraries);
//            }else{
//                libraries = new ArrayList<>();
//                libraries.add(version);
//                currMap.put(license, libraries);
//            }
//        }

        // Update maps
        mHeaders.clear();
        mHeaders.addAll(currMap.values());
        mTitles.clear();
        mTitles.addAll(currMap.keySet());
    }


    /***********************************************************************************************
     *
     * ViewHolder's
     *
     */

    public static class VersionViewHolder extends RecyclerView.ViewHolder{



        public VersionViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            title = ButterKnife.findById(itemView, R.id.title);
            FontLoader.apply(title, Face.ROBOTO_MEDIUM);
        }
    }

}
