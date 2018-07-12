package persistent;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * User definition.
 */
public class User {
    private String id;
    private String name;
    private String login;
    private String email;
    private Timestamp createDate;
    private String password;
    private String country;
    private String city;
    private Role role;

    public User(String name, String login, String email, String password, Role role) {
        this(UUID.randomUUID().toString(), name, login, password, email, role, new Timestamp(System.currentTimeMillis()));
    }

    public User(String id, String name, String login, String password, String email, Role role, Timestamp created) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.createDate = created;
        this.role = role;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
