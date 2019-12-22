package se.tarlinder.blockgame

import spock.lang.Specification

class GameSpecification extends Specification {

    def "Collapse bottom row"() {
        given:
        def game = new Game(1, 1)
        game.board = (int[][]) [[1, 0, 0], [1, 1, 0], [1, 1, 1]]
        when:
        game.collapse(2)
        then:
        game.board == [
                [0, 0, 0],
                [1, 0, 0],
                [1, 1, 0]]
    }
}
