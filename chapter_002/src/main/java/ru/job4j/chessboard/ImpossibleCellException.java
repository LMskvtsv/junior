package ru.job4j.chessboard;

class ImpossibleCellException extends RuntimeException {
    ImpossibleCellException(String msg) {
        super(msg);
    }
}
