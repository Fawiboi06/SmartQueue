package model;

public class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role){
        this.username=username;
        this.password=password;
        this.role=role;
    }

    public String getUsername(){
        return username;
    }

    public boolean checkPassword(String input){
        return password != null && password.equals(input);
    }

    public String getRole(){
        return role;
    }

    @Override
    public String toString(){
        return username + " (" + role + ")";
    }
}
