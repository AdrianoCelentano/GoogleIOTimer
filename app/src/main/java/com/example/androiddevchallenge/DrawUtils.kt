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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.toOffset

fun DrawScope.lerpNodes(
    startNodes: List<IntOffset>,
    endNodes: List<IntOffset>,
    progress: Float
): List<Offset> {
    return startNodes.mapIndexed { index, startNode ->
        androidx.compose.ui.unit.lerp(startNode, endNodes[index], progress)
            .toOffset()
    }
}

fun Path.moveTo(offset: Offset) {
    moveTo(x = offset.x, y = offset.y)
}

fun Path.cubicTo(offsetOne: Offset, offsetTwo: Offset, offsetThree: Offset) {
    cubicTo(
        x1 = offsetOne.x, y1 = offsetOne.y,
        x2 = offsetTwo.x, y2 = offsetTwo.y,
        x3 = offsetThree.x, y3 = offsetThree.y,
    )
}

fun Path.lineTo(offset: Offset) {
    lineTo(x = offset.x, y = offset.y)
}
