package persistent;

public class Role {

    public static final int ADMIN_ID = 1;
    public static final int USER_ID = 2;

    private int id;

    public Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
