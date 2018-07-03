package servlet;

import java.util.LinkedHashMap;
import java.util.function.Function;

public class DispatchAction {

    private final LinkedHashMap<Function<String, Boolean>, Function<String, Action>> dispatch = new LinkedHashMap<>();

    /**
     * Initialization with existing possible values of 'action' tag.
     * @return
     */
    public DispatchAction init() {
        this.dispatch.put(
                action -> action.equals("add"),
                action -> Action.ADD
        );
        this.dispatch.put(
                action -> action.equals("update"),
                action -> Action.UPDATE
        );
        this.dispatch.put(
                action -> action.equals("delete"),
                action -> Action.DELETE
        );
        return this;
    }


    /**
     * Check action for request by value of 'action' tag.
     *
     * @param actionTag tag
     * @return true if access are allowed
     */
    public Action getAction(String actionTag) {
        for (Function<String, Boolean> predict: this.dispatch.keySet()) {
            if (predict.apply(actionTag)) {
                return this.dispatch.get(predict).apply(actionTag);
            }
        }
        throw new IllegalStateException("Could not found a handle for action");
    }
}
