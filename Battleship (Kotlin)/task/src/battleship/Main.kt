package battleship

import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

const val EMPTY_LOCATION: String = "~"
const val OCCUPIED_LOCATION: String = "O"

class IncorrectShipSize(message: String): Exception(message)

class BoardLocation(val row: Char, val col: Int)

class Board {
    val board: MutableList<MutableList<String>> = MutableList(10) { MutableList(10) { EMPTY_LOCATION} }

    fun printBoard() {
        println("  ${(1..10).toMutableList().joinToString(" ")}")
        for (rowIndex in this.board.indices) {
            println("${'A' + rowIndex} ${this.board[rowIndex].joinToString(" ")}")
        }
    }

    private fun addShip(loc1: BoardLocation, loc2: BoardLocation, shipSize: Int) {
        val rowDelta = abs(loc1.row - loc2.row)
        val colDelta = abs(loc1.col - loc2.col)
        val requestedShipSize = max(rowDelta, colDelta)

        if (requestedShipSize != (shipSize - 1)) {
            throw IncorrectShipSize("Expected $shipSize, specified $requestedShipSize")
        }

        val rowAligned = (rowDelta == 0) && (colDelta == (shipSize - 1))
        val colAligned = (rowDelta == (shipSize - 1)) && (colDelta == 0)

        if (!rowAligned && !colAligned) {
            return
        }

        if (rowAligned) {
            val rowIndex = loc1.row - 'A'

            // pre-check that the ship can go here
            for (colIndex in (min(loc1.col, loc2.col)..max(loc1.col, loc2.col))) {
                if (this.board[rowIndex][colIndex - 1] == OCCUPIED_LOCATION) {
                    println("Error! Wrong ship location! Try again:")
                    return
                }
            }

            // passed check, let's place the ship
            for (colIndex in (min(loc1.col, loc2.col)..max(loc1.col, loc2.col))) {
                this.board[rowIndex][colIndex - 1] = OCCUPIED_LOCATION
            }
        } else {
            // has to be column aligned
            val colIndex = loc1.col - 1
            val rowRange = min(loc1.row - 'A', loc2.row - 'A')..max(loc1.row-'A', loc2.row - 'A')

            // pre-check that the ship can go here
            for (rowIndex in rowRange) {
                if (this.board[rowIndex][colIndex ] == OCCUPIED_LOCATION) {
                    println("Error! Wrong ship location! Try again:")
                    return
                }
            }

            // passed check, let's place the ship
            for (rowIndex in rowRange) {
                this.board[rowIndex][colIndex ] = OCCUPIED_LOCATION
            }
        }
    }

    fun addAircraftCarrier(loc1: BoardLocation, loc2: BoardLocation) {
        val AIRCRAFT_CARRIER_SIZE = 5
        try {
            this.addShip(loc1, loc2, AIRCRAFT_CARRIER_SIZE)
        } catch (e: IncorrectShipSize) {
            println("Error! Wrong length of the Aircraft Carrier! Try again:")
        }
    }

    fun addBattleship(loc1: BoardLocation, loc2: BoardLocation) {
        val BATTLESHIP_SIZE = 4
        try {
            this.addShip(loc1, loc2, BATTLESHIP_SIZE)
        } catch (e: IncorrectShipSize) {
            println("Error! Wrong length of the Battleship! Try again:")
        }
    }

    fun addSubmarine(loc1: BoardLocation, loc2: BoardLocation) {
        val SUBMARINE_SIZE = 3
        try {
            this.addShip(loc1, loc2, SUBMARINE_SIZE)
        } catch (e: IncorrectShipSize) {
            println("Error! Wrong length of the Submarine! Try again:")
        }
    }
}

class Game {
    val board = Board()

    private fun readLocationsFromStdin(): Pair<BoardLocation, BoardLocation> {
        return try {
            val (c1, c2) = readln().split(" ")
            val c1Row = c1[0]
            val c1Col = c1.drop(1).toInt()
            val c2Row = c2[0]
            val c2Col = c2.drop(1).toInt()
            BoardLocation(c1Row, c1Col) to BoardLocation(c2Row, c2Col)
        } catch (e: Exception) {
            throw e
        }
    }

    fun play() {
        this.board.printBoard()

        println("Enter the coordinates of the Aircraft Carrier (5 cells):")
        try {
            val (loc1, loc2) = this.readLocationsFromStdin()
            board.addAircraftCarrier(loc1, loc2)
        } catch (e: Exception) {
            println("Error")
        }

        this.board.printBoard()

        println("Enter the coordinates of the Battleship (4 cells):")
        try {
            val (loc1, loc2) = this.readLocationsFromStdin()
            board.addBattleship(loc1, loc2)
        } catch (e: Exception) {
            println("Error")
        }

        this.board.printBoard()

        println("Enter the coordinates of the Submarine (3 cells):")
        try {
            val (loc1, loc2) = this.readLocationsFromStdin()
            board.addSubmarine(loc1, loc2)
        } catch (e: Exception) {
            println("Error")
        }

        this.board.printBoard()
    }
}

fun main() {
    val game = Game()
    game.play()
}