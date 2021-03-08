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
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.turquise
import com.example.androiddevchallenge.ui.theme.violette
import kotlinx.coroutines.launch

@Composable
fun PlayButton(onClick: () -> Unit) {

    val path = remember { Path() }
    val progress = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Canvas(
        modifier = Modifier
            .height(65.dp)
            .width(50.dp)
            .clickable {
                onClick()
                coroutineScope.launch {
                    val newValue = if (progress.value == 1f) 0f else 1f
                    progress.animateTo(newValue, animationSpec = tween(300))
                }
            }
    ) {

        path.reset()

        val nodesOne = lerpNodes(playOneNodes, pauseOneNodes, progress.value)
        val nodesTwo = lerpNodes(playTwoNodes, pauseTwoNodes, progress.value)
        val color = lerp(turquise, violette, progress.value)

        path.moveTo(nodesOne[0])
        path.lineTo(nodesOne[1])
        path.lineTo(nodesOne[2])
        path.lineTo(nodesOne[3])
        path.lineTo(nodesOne[4])

        path.moveTo(nodesTwo[0])
        path.lineTo(nodesTwo[1])
        path.lineTo(nodesTwo[2])
        path.lineTo(nodesTwo[3])
        path.lineTo(nodesTwo[4])

        drawPath(path = path, color = color)
    }
}

val DrawScope.playOneNodes
    get() = listOf(
        IntOffset(0 * xStepInt, 5 * yStepInt),
        IntOffset(0 * xStepInt, 0 * yStepInt),
        IntOffset(4 * xStepInt, (2.5 * yStepInt).toInt()),
        IntOffset(8 * xStepInt, 5 * yStepInt),
        IntOffset(0 * xStepInt, 5 * yStepInt),
    )

val DrawScope.playTwoNodes
    get() = listOf(
        IntOffset(0 * xStepInt, 10 * yStepInt),
        IntOffset(0 * xStepInt, 5 * yStepInt),
        IntOffset(8 * xStepInt, 5 * yStepInt),
        IntOffset(4 * xStepInt, (7.5 * yStepInt).toInt()),
        IntOffset(0 * xStepInt, 10 * yStepInt),
    )

val DrawScope.pauseOneNodes
    get() = listOf(
        IntOffset(5 * xStepInt, 0 * yStepInt),
        IntOffset(7 * xStepInt, 0 * yStepInt),
        IntOffset(7 * xStepInt, 10 * yStepInt),
        IntOffset(5 * xStepInt, 10 * yStepInt),
        IntOffset(5 * xStepInt, 0 * yStepInt),
    )

val DrawScope.pauseTwoNodes
    get() = listOf(
        IntOffset(1 * xStepInt, 0 * yStepInt),
        IntOffset(3 * xStepInt, 0 * yStepInt),
        IntOffset(3 * xStepInt, 10 * yStepInt),
        IntOffset(1 * xStepInt, 10 * yStepInt),
        IntOffset(1 * xStepInt, 0 * yStepInt),
    )

private val DrawScope.xStep get() = size.width / 8
private val DrawScope.xStepInt get() = xStep.toInt()
private val DrawScope.yStep get() = size.height / 10
private val DrawScope.yStepInt get() = yStep.toInt()
