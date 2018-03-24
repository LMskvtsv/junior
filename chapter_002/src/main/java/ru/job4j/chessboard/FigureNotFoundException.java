package ru.job4j.chessboard;

class FigureNotFoundException extends RuntimeException {
    FigureNotFoundException(String msg) {
        super(msg);
    }
}
