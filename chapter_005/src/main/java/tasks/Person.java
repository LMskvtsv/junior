package tasks;

public class Person {
    private String name = "";
    private long enter;
    private long leave;

    public String appendName(int s){
        StringBuilder builder = new StringBuilder(name);
        builder.append(s);
        name = builder.toString();
        return name;
    }

    public String getName() {
        return name;
    }

    public long getEnter() {
        return enter;
    }

    public long getLeave() {
        return leave;
    }

    public void setEnter(long enter) {
        this.enter = enter;
    }

    public void setLeave(long leave) {
        this.leave = leave;
    }
}
