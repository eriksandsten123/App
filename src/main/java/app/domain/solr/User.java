package app.domain.solr;

import org.apache.solr.client.solrj.beans.Field;

public class User {
    @Field
    private String id;
    @Field
    private String name;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}