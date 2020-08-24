package io.security.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Token {

    private String email;
    private String token;
    private Date last_used;

    @Id
    @GeneratedValue
    private String series;

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public Date getLast_used() {
        return last_used;
    }

    public String getSeries() {
        return series;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLast_used(Date last_used) {
        this.last_used = last_used;
    }

    public void setSeries(String series) {
        this.series = series;
    }
}
