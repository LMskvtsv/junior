package persistent;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * User definition.
 */
public class User {
    String id;
    String name;
    String login;
    String email;
    Timestamp createDate;

    public User(String name, String login, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = new Timestamp(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return String.format("User{id='%s', name='%s', login='%s', email='%s', createDate='%s'}",
                id, name, login, email, createDate.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
