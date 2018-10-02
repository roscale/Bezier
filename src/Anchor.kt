import processing.core.PVector

class Anchor(val position: PVector) {
    var prevHandle: Handle? = null
    var nextHandle: Handle? = null
}