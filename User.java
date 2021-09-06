package application;

public class User {
    //kullanýcý verileri
    String age,id, name, surname, password;

    public void setId(String id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String name) {
        this.name = name;
    }

    public void setType(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public User(String id, String password, String name, String surname, String age) {
        this.id = id;
        this.password = password;
        this.name =name;
        this.surname = surname;
        this.age = age;
    }
}