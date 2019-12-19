package se.tarlinder.tetris

import spock.lang.Specification

class BlockSpecification extends Specification {
    def "Block can be dropped if nothing is under it"() {
        given:
        int[][] board = [[0, 0], [0, 0], [0, 0]]
        def block = new Block(0, 0, board)
        expect:
        block.canDrop()
    }

    def "Block can't be dropped if there's something under it"() {
        given:
        int[][] board = [[0, 0], [0, 0], [2, 2]]
        def block = new Block(0, 0, board)
        expect:
        !block.canDrop()
    }

}
