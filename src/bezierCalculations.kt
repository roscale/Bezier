import processing.core.PVector
import kotlin.math.pow

fun getCurvePoint(p1: PVector, p2: PVector, p3: PVector, p4: PVector, t: Float): PVector {
    fun getCurveCoordinate(n1: Float, n2: Float, n3: Float, n4: Float, t: Float): Float {
        return (1 - t).pow(3) * n1 +
                3 * (1 - t).pow(2) * t * n2 +
                3 * (1 - t) * t.pow(2) * n3 +
                t.pow(3) * n4
    }

    return PVector(getCurveCoordinate(p1.x, p2.x, p3.x, p4.x, t), getCurveCoordinate(p1.y, p2.y, p3.y, p4.y, t))
}

fun getCurvePoint(p1: PVector, p2: PVector, p3: PVector, t: Float): PVector {
    fun getCurveCoordinate(n1: Float, n2: Float, n3: Float, t: Float): Float {
        return (1 - t).pow(2) * n1 +
                2 * (1 - t) * t * n2 +
                t.pow(2) * n3
    }

    return PVector(getCurveCoordinate(p1.x, p2.x, p3.x, t), getCurveCoordinate(p1.y, p2.y, p3.y, t))
}

const val dt = 0.02f ///////////////////////////

fun getCurvePoints(p1: PVector, p2: PVector, p3: PVector, p4: PVector): List<PVector> {
    val points = arrayListOf<PVector>()

    var t = 0f
    while (t <= 1f + dt) {
        points.add(getCurvePoint(p1, p2, p3, p4, t))
        t += dt
    }

    return points
}

fun getCurvePoints(p1: PVector, p2: PVector, p3: PVector): List<PVector> {
    val points = arrayListOf<PVector>()

    var t = 0f
    while (t <= 1f + dt) {
        println(t)
        points.add(getCurvePoint(p1, p2, p3, t))
        t += dt
    }

    return points
}