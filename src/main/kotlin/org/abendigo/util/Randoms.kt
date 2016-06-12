package org.abendigo.util

import java.util.concurrent.ThreadLocalRandom.current

fun random(min: Int, max: Int) = min + current().nextInt() * (max - min)

fun random(max: Int) = random(0, max)

fun randomFloat(min: Float, max: Float) = min + current().nextFloat() * (max - min)