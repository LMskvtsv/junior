package ru.job4j.chessboard;

class ImpossibleMoveException extends RuntimeException {
     ImpossibleMoveException(String message) {
        super(message);
    }
}
