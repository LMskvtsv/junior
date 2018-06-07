package threads;

import javafx.scene.shape.Rectangle;

public class RectangleMove implements Runnable {
    private final Rectangle rect;

    public RectangleMove(Rectangle rect) {
        this.rect = rect;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            int rawLimit = 290;
            while (rect.getX() < rawLimit) {
                this.rect.setX(this.rect.getX() + 1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    return;
                }
            }
            while (rect.getX() > 1) {
                this.rect.setX(this.rect.getX() - 1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}
