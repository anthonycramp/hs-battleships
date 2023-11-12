fun main() {
    val input = readln()
    val n = readln().toInt()

    println("Symbol # $n of the string \"$input\" is '${input[n-1]}'")
}