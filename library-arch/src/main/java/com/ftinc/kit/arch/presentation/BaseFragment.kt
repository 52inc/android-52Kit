/*
 * Copyright (c) 2018 52inc.
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
import android.support.v4.app.Fragment
import com.ftinc.kit.arch.presentation.delegates.FragmentDelegate
import com.ftinc.kit.arch.di.HasComponent
import io.reactivex.disposables.CompositeDisposable
import kotlin.reflect.KClass


abstract class BaseFragment : Fragment() {

    private val delegates = ArrayList<FragmentDelegate>()
    protected val disposables = CompositeDisposable()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupComponent()

        delegates.forEach { it.onActivityCreated(savedInstanceState) }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        delegates.forEach { it.onSaveInstanceState(outState) }
        super.onSaveInstanceState(outState)
    }


    override fun onPause() {
        super.onPause()
        delegates.forEach(FragmentDelegate::onPause)
    }


    override fun onResume() {
        super.onResume()
        delegates.forEach(FragmentDelegate::onResume)
    }


    override fun onDestroy() {
        disposables.clear()
        delegates.forEach(FragmentDelegate::onDestroy)
        super.onDestroy()
    }


    open fun setupComponent() {
    }


    protected fun <C : Any> getComponent(componentType: KClass<C>): C {
        if (parentFragment is HasComponent<*>) {
            return componentType.java.cast((parentFragment as HasComponent<*>).getComponent())
        } else if (activity is HasComponent<*>) {
            return componentType.java.cast((activity as HasComponent<*>).getComponent())!!
        }
        return componentType.java.cast((activity as HasComponent<*>).getComponent())!!
    }


    protected fun addDelegate(delegate: FragmentDelegate) = delegates.add(delegate)
    protected fun removeDelegate(delegate: FragmentDelegate) = delegates.remove(delegate)
}
