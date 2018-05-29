package list;

class NodeCycling<T> {
    T value;
    NodeCycling<T> next;

    public NodeCycling(T value) {
        this.value = value;
    }

    /**
     * This method implements Floyd's turtle and hair algorithm.
     *
     * @param node - first node in a sequence.
     * @return true if there is a cycle.
     */
    public static boolean isCyclingExists(NodeCycling node) {
        boolean result = false;
        NodeCycling turtle = node;
        NodeCycling hair = node;
        while (hair != null && hair.next != null) {
            turtle = turtle.next;
            hair = hair.next.next;
            if (turtle == hair) {
                result = true;
                break;
            }
        }
        return result;
    }
}
