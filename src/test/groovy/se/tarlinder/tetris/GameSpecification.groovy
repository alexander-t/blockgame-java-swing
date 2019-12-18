package se.tarlinder.tetris

import spock.lang.Specification

class GameSpecification extends Specification {
    def "Single block falls to bottom of a 3x3 board"() {
        given:
        def game = new Game()

        when:
        game.addBlock()
        game.tick()
        game.tick()

        then:
        game.board == [
                [2, 0, 0, 0, 2],
                [2, 0, 0, 0, 2],
                [2, 0, 1, 0, 2],
                [2, 2, 2, 2, 2]]
    }

    def "Two blocks fall to bottom of a 3x3 board"() {
        given:
        def game = new Game()

        when:
        game.addBlock()
        game.tick()
        game.tick()
        game.tick()
        game.tick()

        then:
        game.board == [
                [2, 0, 0, 0, 2],
                [2, 0, 1, 0, 2],
                [2, 0, 1, 0, 2],
                [2, 2, 2, 2, 2]]
    }

    def "Losing by filling a vertical line"() {
        given:
        def game = new Game()

        when:
        game.addBlock()
        game.tick()
        game.tick()
        game.tick()
        game.tick()
        game.tick()
        game.tick()

        then:
        thrown IllegalStateException
    }
}
