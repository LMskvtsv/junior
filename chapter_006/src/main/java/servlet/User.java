package servlet;

public class User {
    String name;
    String surname;
    String email;
    String sex;

    public User(String name, String login, String email, String sex) {
        this.name = name;
        this.surname = login;
        this.email = email;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getSex() {
        return sex;
    }
}
