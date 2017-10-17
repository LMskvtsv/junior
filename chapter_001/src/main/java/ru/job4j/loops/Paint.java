package ru.job4j.loops;

/**
 * Class for board printing.
 */
public class Paint {

    /**
     * Paints pyramid.
     *
     * @param h - height of pyramid
     * @return String for console output.
     */
    public String pyramid(int h) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= h; i++) {
            for (int j = 0; j < h + (i - 1); j++) {
                if (j < h - i) {
                    builder.append(" ");
                } else {
                    builder.append("^");
                }
            }
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }
}
