package praktikum;

public class LoginUser {
    private String email;
    private String password;

    public LoginUser(String email, String password){
        this.email = email;
        this.password = password;
    }

    public LoginUser(){

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

    public static LoginUser from(CreateUsers createUsers){
        return new LoginUser(createUsers.getEmail(), createUsers.getPassword());
    }
}
