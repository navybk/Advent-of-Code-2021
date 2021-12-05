package day2

enum class Direction(val value: String) {
    FORWARD("forward"),
    DOWN("down"),
    UP("up");

    companion object {
        fun fromString(input: String): Direction =
            values().find {
                it.value == input
            }!!
    }
}
