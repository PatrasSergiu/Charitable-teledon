package teledon.network.dto;

import java.io.Serializable;

public class VoluntarDTO implements Serializable {


    private String username;
    private String password;

    public VoluntarDTO(String id) {
        this(id,"");
    }

    public VoluntarDTO(String id, String passwd) {
        this.username = id;
        this.password = passwd;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "VoluntarDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}

