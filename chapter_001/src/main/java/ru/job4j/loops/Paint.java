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
        int n = 1;
        for (int i = 0; i < h; i++) {
            int z = i;
            while (z < h - 1) {
                builder.append(" ");
                z++;
            }

            for (int j = 0; j < i + n; j++) {
                builder.append("^");
            }
            n = n + 1;

            int x = i;
            while (x < h - 1) {
                builder.append(" ");
                x++;
            }
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }
}
