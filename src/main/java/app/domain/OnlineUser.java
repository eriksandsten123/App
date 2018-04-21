package app.domain;

import java.util.Objects;

public class OnlineUser implements Comparable {
    private Long id;
    private String username;

    public OnlineUser(final Long id, final String username) {
        setId(id);
        setUsername(username);
    }

    public OnlineUser() {

    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnlineUser that = (OnlineUser) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{\"id\":" + id + ",\"username\":\"" + username + "\"}";
    }

    @Override
    public int compareTo(final Object o) {
        final OnlineUser that = (OnlineUser) o;
        return Long.compare(id, that.id);
    }
}
