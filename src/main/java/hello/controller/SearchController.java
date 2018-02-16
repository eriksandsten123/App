package hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
    @PostMapping("/search")
    public String search(@RequestParam(name = "query", required = true) final String query) {
        System.out.println("Search query: " + query);
        return query;
    }
}
