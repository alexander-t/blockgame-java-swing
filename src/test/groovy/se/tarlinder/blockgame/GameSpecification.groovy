package se.tarlinder.blockgame

import spock.lang.Specification

class GameSpecification extends Specification {
    def "Single block falls to bottom of a 4x3 board"() {
        given:
        def game = new Game(4, 3)

        when:
        game.addBlock()
        game.tick()

        then:
        game.board == [
                [2, 0, 0, 0, 0, 2],
                [2, 0, 1, 1, 0, 2],
                [2, 0, 1, 1, 0, 2],
                [2, 2, 2, 2, 2, 2]]
    }

    def "Two blocks fall to bottom of a 4x3 board"() {
        given:
        def game = new Game(4, 7)

        when:
        game.addBlock()
        // First block
        5.times {
            game.tick()
        }
        // Second block
        4.times {
            game.tick()
        }
        // Third block appears
        game.tick()

        then:
        game.board == [
                [2, 0, 1, 1, 0, 2],
                [2, 0, 1, 1, 0, 2],
                [2, 0, 0, 0, 0, 2],
                [2, 0, 1, 1, 0, 2],
                [2, 0, 1, 1, 0, 2],
                [2, 0, 1, 1, 0, 2],
                [2, 0, 1, 1, 0, 2],
                [2, 2, 2, 2, 2, 2]]
    }

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
/*
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
    */
}
