/*
 * Copyright (c) 2019 52inc.
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

package com.ftinc.kit.arch.presentation


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.ftinc.kit.arch.R
import com.ftinc.kit.arch.presentation.delegates.ActivityDelegate
import com.ftinc.kit.arch.util.bindOptionalView
import io.reactivex.disposables.CompositeDisposable


abstract class BaseActivity : AppCompatActivity() {

    protected val appbar: Toolbar? by bindOptionalView(R.id.appbar)
    protected val disposables = CompositeDisposable()
    protected val delegates: ArrayList<ActivityDelegate> = ArrayList()


    protected abstract fun setupComponent()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupComponent()

        delegates.forEach { it.onCreate(savedInstanceState) }
    }


    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setSupportActionBar(appbar)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        delegates.forEach { it.onSaveInstanceState(outState) }
    }


    override fun onResume() {
        super.onResume()
        delegates.forEach { it.onResume() }
    }


    override fun onStart() {
        super.onStart()
        delegates.forEach { it.onStart() }
    }


    override fun onPause() {
        super.onPause()
        delegates.forEach { it.onPause() }
    }


    override fun onStop() {
        super.onStop()
        delegates.forEach { it.onStop() }
    }


    override fun onDestroy() {
        disposables.clear()
        delegates.forEach { it.onDestroy() }
        super.onDestroy()
    }
}