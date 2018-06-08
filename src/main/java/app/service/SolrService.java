package app.service;

import app.domain.solr.User;
import app.repository.UserRepository;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SolrService {
    private final String COLLECTION_NAME = "app_collection";

    private SolrClient solrClient;
    private UserRepository userRepository;

    @Autowired
    public void SolrService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void indexUserProfiles() throws Exception {
        final User user1 = new User();
        final User user2 = new User();

        user1.setId("1");
        user1.setName("Erik");
        user2.setId("2");
        user2.setName("David");

        final UpdateResponse response = solrClient.addBean(COLLECTION_NAME, user1);
        final UpdateResponse response2 = solrClient.addBean(COLLECTION_NAME, user2);

        // Indexed documents must be committed
        solrClient.commit("app_collection");
    }

    public List<User> getAllIndexed() throws Exception {
        SolrQuery query = new SolrQuery("*:*");
        query.setFields("id", "name");
        final QueryResponse response = solrClient.query(COLLECTION_NAME, query);
        List<User> users = response.getBeans(User.class);
        return users;
    }

    public List<User> search(final String queryString) throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery("name:*" + queryString + "*");

        query.setFields("id", "name");

        filterLoggedInUser(query);

        final QueryResponse response = solrClient.query(COLLECTION_NAME, query);
        List<User> searchResults = response.getBeans(User.class);

        return searchResults;
    }

    private void filterLoggedInUser(final SolrQuery query) {
        // Do not include logged in user in the search result
    }

    @Bean
    public SolrClient solrClient() {
        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html
        // Single node Solr client
        String urlString = "http://localhost:8983/solr";

        solrClient = new HttpSolrClient.Builder(urlString)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();

        return solrClient;
    }
}