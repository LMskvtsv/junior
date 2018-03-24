package ru.job4j.chessboard;

class OccupiedWayException extends RuntimeException {
    OccupiedWayException(String msg) {
        super(msg);
    }
}
