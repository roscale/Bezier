import processing.core.PApplet
import processing.core.PVector
import processing.event.MouseEvent


fun MouseEvent.getPosition(): PVector {
    return PVector(x.toFloat(), y.toFloat())
}

val anchors = arrayListOf<Anchor>()
val curvePoints = hashMapOf<Pair<Anchor, Anchor>, List<PVector>>()

var beganDragging = false
var currentAnchor: Anchor? = null


fun regenCurvePoints(first: Anchor, second: Anchor) {
    // Cubic
    curvePoints[Pair(first, second)] = if (first.nextHandle != null && second.prevHandle != null) {
        getCurvePoints(first.position, first.nextHandle?.position!!, second.prevHandle?.position!!, second.position)
    }

    // Quadratic
    else if ((first.nextHandle == null && second.prevHandle != null) or
            (first.nextHandle != null && second.prevHandle == null)) {

        val singleHandle = if (first.nextHandle != null) first.nextHandle else second.prevHandle
        getCurvePoints(first.position, singleHandle?.position!!, second.position)


    // Linear
    } else {
        arrayListOf(first.position, second.position)
    }
}



class App: PApplet() {
    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        frameRate(60f)
        rectMode(CENTER)
    }

    override fun draw() {
        background(128)

        stroke(255f, 0f, 0f)
        for (points in curvePoints.values) {
            for (i in 0..points.size - 2) {
                line(points[i].x, points[i].y, points[i+1].x, points[i+1].y)
            }
        }

        stroke(0f)
        for (anchor in anchors) {
            with(anchor.prevHandle?.position) {
                if (this != null) {
                    line(x, y, anchor.position.x, anchor.position.y)
                    ellipse(x, y, 5f, 5f)
                }
            }

            with(anchor.nextHandle?.position) {
                if (this != null) {
                    line(x, y, anchor.position.x, anchor.position.y)
                    ellipse(x, y, 5f, 5f)
                }
            }

            rect(anchor.position.x, anchor.position.y, 5f, 5f)
        }
    }

    override fun mousePressed(event: MouseEvent) {
        beganDragging = false

        val anchor = Anchor(event.getPosition())
        anchors.add(anchor)
        currentAnchor = anchor

        if (anchors.size >= 2) {
            regenCurvePoints(anchors[anchors.size-2], currentAnchor!!)
        }
    }

    override fun mouseDragged(event: MouseEvent) {
        if (!beganDragging) {
            currentAnchor?.apply {
                prevHandle = Handle(currentAnchor!!)
                nextHandle = Handle(currentAnchor!!)
            }
        }

        val cursorToAnchor = PVector.sub(currentAnchor?.position, event.getPosition())
        val prevHandlePosition = PVector.add(currentAnchor?.position, cursorToAnchor)

        currentAnchor?.apply {
            nextHandle?.position?.set(event.getPosition())
            prevHandle?.position?.set(prevHandlePosition)
        }

        if (anchors.size >= 2) {
            regenCurvePoints(anchors[anchors.size-2], currentAnchor!!)
        }

        beganDragging = true
    }
}

fun main(args: Array<String>) {
    PApplet.main(App::class.java)
}