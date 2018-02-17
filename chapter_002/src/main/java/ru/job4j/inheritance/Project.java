package ru.job4j.inheritance;

public class Project {

    private boolean isClosed = false;

    public boolean isClosed() {
        return isClosed;
    }

    private String requirements;

    public String getRequirements() {
        return requirements;
    }

    public void close(Construction construction) {
        if (!isClosed) {
            if (construction != null) {
                isClosed = true;
            }
        }
    }
}
