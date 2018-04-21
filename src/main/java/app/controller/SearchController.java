package app.controller;

import app.domain.User;
import app.service.SolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    private SolrService solrService;

    @Autowired
    public SearchController(final SolrService solrService) {
        this.solrService = solrService;
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "query", required = true) String query, final Model model) throws Exception {
        // Trim the search query string
        query = query.trim();
        final List<User> searchResults = solrService.search(query);
        model.addAttribute("results", searchResults);

        return "search-results";
    }
}