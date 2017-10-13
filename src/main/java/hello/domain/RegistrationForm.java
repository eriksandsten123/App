package hello.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationForm {

    @NotNull
    @Size(min=2, max=30)
    private String username;

    @NotNull
    @Size(min=2, max=30)
    private String password;

    @NotNull
    @Size(min=2, max=30)
    private String repeatPassword;

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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}