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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import com.example.androiddevchallenge.ui.theme.turquise
import com.example.androiddevchallenge.ui.theme.violette

@Composable
fun Counter(
    state: CounterState
) {
    val path = remember { Path() }

    Canvas(
        modifier = Modifier
            .width(50.dp)
            .height(100.dp)
    ) {

        path.reset()

        drawArc(
            color = if (state.digit % 2 == 0) turquise else violette,
            size = Size(70.dp.toPx(), 100.dp.toPx()),
            startAngle = 0f,
            sweepAngle = 360 * state.progress,
            useCenter = true
        )

        when (state.digit) {
            5 -> five(path)
            4 -> fiveToFour(path, state.progress)
            3 -> fourToThree(path, state.progress)
            2 -> threeToTwo(path, state.progress)
            1 -> twoToOne(path, state.progress)
        }
    }
}

private fun DrawScope.twoToOne(path: Path, progress: Float) {

    val nodes = lerpNodes(twoNodes, oneNodes, progress)
    val color = lerp(violette, turquise, progress)

    path.moveTo(nodes[0])
    path.cubicTo(nodes[1], nodes[2], nodes[3])
    path.lineTo(nodes[4])
    path.lineTo(nodes[5])
    path.lineTo(nodes[6])

    drawNumber(path, color)
}

private fun DrawScope.threeToTwo(path: Path, progress: Float) {

    val nodes = lerpNodes(threeNodes, twoNodes, progress)
    val color = lerp(turquise, violette, progress)

    path.moveTo(nodes[0])
    path.cubicTo(nodes[1], nodes[2], nodes[3])
    path.lineTo(nodes[4])
    path.lineTo(nodes[5])
    path.lineTo(nodes[6])

    drawNumber(path, color)
}

private fun DrawScope.fourToThree(path: Path, progress: Float) {

    val nodes = lerpNodes(fourNodes, threeNodes, progress)
    val color = lerp(violette, turquise, progress)

    path.moveTo(nodes[0])
    path.cubicTo(nodes[1], nodes[2], nodes[3])
    path.cubicTo(nodes[4], nodes[5], nodes[6])

    drawNumber(path, color)
}

private fun DrawScope.fiveToFour(path: Path, progress: Float) {

    val nodes = lerpNodes(fiveNodes, fourNodes, progress)
    val color = lerp(turquise, violette, progress)

    path.moveTo(nodes[0])
    path.lineTo(nodes[1])
    path.lineTo(nodes[2])
    path.lineTo(nodes[3])
    path.lineTo(nodes[4])
    path.lineTo(nodes[5])
    path.lineTo(nodes[6])

    drawNumber(path, color)
}

private fun DrawScope.five(path: Path) {

    val nodes = fiveNodes.map { it.toOffset() }

    path.moveTo(nodes[0])
    path.lineTo(nodes[1])
    path.lineTo(nodes[2])
    path.lineTo(nodes[3])
    path.cubicTo(nodes[4], nodes[5], nodes[6])
    drawNumber(path, turquise)
}

private val DrawScope.xStep get() = size.width / 6
private val DrawScope.yStep get() = size.height / 12

private val DrawScope.xStepInt get() = xStep.toInt()
private val DrawScope.yStepInt get() = yStep.toInt()

private fun DrawScope.drawNumber(path: Path, color: Color) {
    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = 30f,
            join = StrokeJoin.Round
        )
    )
}

private val DrawScope.oneNodes
    get() = listOf(
        IntOffset(0, 0 * yStepInt),
        IntOffset(0, 2 * yStepInt),
        IntOffset(0, 4 * yStepInt),
        IntOffset(0, 6 * yStepInt),
        IntOffset(0, 8 * yStepInt),
        IntOffset(0, 10 * yStepInt),
        IntOffset(0, 12 * yStepInt)
    )

private val DrawScope.twoNodes
    get() = listOf(
        IntOffset(0 * xStepInt, 4 * yStepInt),
        IntOffset(1 * xStepInt, 2 * yStepInt),
        IntOffset(5 * xStepInt, 2 * yStepInt),
        IntOffset(6 * xStepInt, 4 * yStepInt),
        IntOffset(3 * xStepInt, 8 * yStepInt),
        IntOffset(0 * xStepInt, 12 * yStepInt),
        IntOffset(6 * xStepInt, 12 * yStepInt)
    )

private val DrawScope.threeNodes
    get() = listOf(
        IntOffset(0 * xStepInt, 0 * yStepInt),
        IntOffset(6 * xStepInt, 1 * yStepInt),
        IntOffset(6 * xStepInt, 5 * yStepInt),
        IntOffset(1 * xStepInt, 6 * yStepInt),
        IntOffset(6 * xStepInt, 7 * yStepInt),
        IntOffset(6 * xStepInt, 11 * yStepInt),
        IntOffset(0 * xStepInt, 12 * yStepInt)
    )

private val DrawScope.fourNodes
    get() = listOf(
        IntOffset(6 * xStepInt, 8 * yStepInt),
        IntOffset(5 * xStepInt, 8 * yStepInt),
        IntOffset(0 * xStepInt, 8 * yStepInt),
        IntOffset((2.5 * xStepInt).toInt(), 4 * yStepInt),
        IntOffset(5 * xStepInt, 0 * yStepInt),
        IntOffset(5 * xStepInt, 8 * yStepInt),
        IntOffset(5 * xStepInt, 12 * yStepInt)
    )

private val DrawScope.fiveNodes
    get() = listOf(
        IntOffset(6 * xStepInt, 0 * yStepInt),
        IntOffset(3 * xStepInt, 0 * yStepInt),
        IntOffset(0 * xStepInt, 0 * yStepInt),
        IntOffset(0 * xStepInt, 5 * yStepInt),
        IntOffset(6 * xStepInt, 7 * yStepInt),
        IntOffset(6 * xStepInt, 9 * yStepInt),
        IntOffset(0 * xStepInt, 12 * yStepInt)
    )
