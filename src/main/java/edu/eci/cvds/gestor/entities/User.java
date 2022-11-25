package edu.eci.cvds.gestor.entities;

public class User {
    private String name;
    private String email;
    private String password;
    private char role;
    private int license;
    private String career;

    public User(){
        super();
    }

    public User(int license, String name, String email, String password, char role, String career) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.license = license;
        this.career = career;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getRole() {
        return role;
    }

    public void setRole(char role) {
        this.role = role;
    }

    public int getLicense() {
        return license;
    }

    public void setLicense(int license) {
        this.license = license;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    @Override
    public String toString(){
        return "Review{" +
                "license=" + license +
                ", name='" + name + '\'' +
                ", email=" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role + '\'' +
                '}';
    }
}
