fun doubleFormat(value: Double, width: Int, precision: Int): String {
    // write your code here
    return "%$width.${precision}f".format(value)
}