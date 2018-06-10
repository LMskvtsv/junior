package threads;

import java.util.Objects;

public class Base {

    int version = 0;
    int id;
    String info;

    public Base(int id, String info) {
        this.id = id;
        this.info = info;
    }

    public void setInfo(String info) {
        this.info = info;
        version++;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Base base = (Base) o;
        return id == base.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Base{"
                + "version="
                + version
                + ", id="
                + id
                + ", info='"
                + info
                + '\''
                + '}';
    }
}
