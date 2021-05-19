package teledon.model;

public class Voluntar extends Entity<Long> {

    private String username;
    private String password;


    public Voluntar(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Voluntar{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }



    public Voluntar() {

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
