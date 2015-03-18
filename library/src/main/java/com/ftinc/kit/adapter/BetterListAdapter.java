/*
 * Copyright (c) 2015 52inc
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
 */

package com.ftinc.kit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Optimized List adapter based on Android Effiecent List Adapter
 * sample (List14.java)
 * 
 * @author r0adkll
 *
 * @param <T>
 */
public abstract class BetterListAdapter<T, VH extends BetterListAdapter.ViewHolder> extends ArrayAdapter<T>{

	/**
	 * Variables
	 */
	private int viewResource = -1;
	private LayoutInflater inflater;
	
	/**
	 * Constructor
	 * @param context					application context
	 * @param textViewResourceId		the item view id
	 * @param objects					the list of objects
	 */
	public BetterListAdapter(Context context, int textViewResourceId, List<T> objects) {
		super(context, textViewResourceId, objects);
		viewResource = textViewResourceId;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	/**
	 * The data holder for the view
	 * 
	 * @author drew.heavner
	 */
	public static abstract class ViewHolder{
        protected View itemView;
        public ViewHolder(View view){
            this.itemView = view;
        }
    }
	
	/**
	 * Called to retrieve the view 
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		VH holder;
		
		if(convertView == null){
			
			// Load the view from scratch
			convertView = inflater.inflate(viewResource, parent, false);
			
			// Load the ViewHolder
			holder = createHolder(convertView);
			
			// set holder to the tag
			convertView.setTag(holder);
			
		}else{
			
			// Pull the view holder from convertView's tag
			holder = (VH) convertView.getTag();
			
		}	


		// bind the data to the holder
		bindHolder(holder, position, getItem(position));
		
		return convertView;
	}
	
	
	
	/**
	 * Create View/View holder
	 * 
	 * 	Here you load all your views from the layout into a custom 
	 *  view holder that you overrid from ViewHolder();
	 * 
	 * @param view
	 * @return
	 */
	public abstract VH createHolder(View view);
	
	/**
	 * Bind the Data from the data object to the view elements you
	 * created in 'createHolder()'
	 * 
	 * @param holder
	 * @param position
	 */
	public abstract void bindHolder(VH holder, int position, T data);
	
}
