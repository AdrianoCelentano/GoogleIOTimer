/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CounterState(
    private val coroutinScope: CoroutineScope
) {

    private var immutableDigit = mutableStateOf(5)
    private val immutableProgress = Animatable(0f)
    val digit get() = immutableDigit.value
    val progress get() = immutableProgress.value

    fun toggle() {
        if (immutableProgress.isRunning.not()) startCounter()
        else coroutinScope.launch { immutableProgress.stop() }
    }

    fun isActive(): Boolean = immutableProgress.value != 0f

    private fun startCounter() {
        coroutinScope.launch {
            while (digit > 0) {
                immutableProgress.animateTo(1f, tween(1000))
                immutableDigit.value -= 1
                immutableProgress.snapTo(0f)
            }
        }
    }
}
