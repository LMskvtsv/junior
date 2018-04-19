package list;

import java.util.List;

public class ConvertList2Array {

    public int[][] toArray(List<Integer> list, int rows) {

        int size = list.size();
        int left = size % rows;
        int verticals;
        if (left == 0) {
            verticals = size / rows;
        } else {
            verticals = size / rows + 1;
        }
        int[][] result = new int[rows][verticals];

        int listCounter = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < verticals; j++) {
                if (listCounter < list.size()) {
                    result[i][j] = list.get(listCounter++);
                }
            }
        }
        return result;
    }
}
