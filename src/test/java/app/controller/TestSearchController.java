package app.controller;

import app.service.SolrService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class TestSearchController {
    private MockMvc mockMvc;
    private SearchController searchController;
    private SolrService solrService;

    @Before
    public void setup() {
        solrService = Mockito.mock(SolrService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new SearchController(solrService)).build();
    }

    @Test
    public void testSearch_trimmedQueryString() throws Exception {
        final String queryString = "Sökterm";

        mockMvc.perform(get("/search").param("query", "  " + queryString + "  "))
                .andExpect(status().isOk()).andExpect(view().name("search-results"));

        Mockito.verify(solrService).search(queryString);
    }

    @Test
    public void testSearch_emptyQueryString() throws Exception {
        mockMvc.perform(get("/search").param("query", ""))
                .andExpect(status().isOk()).andExpect(view().name("search-results"));

        Mockito.verify(solrService).search("");
    }
}