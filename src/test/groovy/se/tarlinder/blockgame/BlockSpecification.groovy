package se.tarlinder.blockgame

import spock.lang.Specification

class BlockSpecification extends Specification {
    def "Block can be dropped if nothing is under it"() {
        given:
        int[][] board = [[0, 0], [0, 0], [0, 0]]
        def block = new Block(0, 0, board, Block.O)
        expect:
        block.canDrop()
    }

    def "Block can't be dropped if there's something under it"() {
        given:
        int[][] board = [[0, 0], [0, 0], [2, 2]]
        def block = new Block(0, 0, board, Block.O)
        expect:
        !block.canDrop()
    }

    def "Placing two Zs next to each other"() {
        given:
        int[][] board = [[0, 0, 0, 0, 0], [0, 0, 0, 0, 0], [2, 2, 2, 2, 2]]
        int[][] expectedBoard = [
                [0, 12, 12, 12, 12],
                [12, 12, 12, 12, 0],
                [2, 2, 2, 2, 2]]
        when:
        new Block(2, 0, board, Block.S)
        new Block(0, 0, board, Block.S)
        then:
        board == expectedBoard
    }

}
