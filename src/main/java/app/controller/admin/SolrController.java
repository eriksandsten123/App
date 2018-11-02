package app.controller.admin;

import app.domain.solr.User;
import app.service.SolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SolrController {
    private SolrService solrService;

    @Autowired
    public SolrController(final SolrService solrService) {
        this.solrService = solrService;
    }

    @GetMapping("/admin/solr/index")
    @ResponseBody
    public String indexUserProfiles() throws Exception {
        solrService.indexUserProfiles();
        return "OK";
    }

    @RequestMapping(value = "/admin/solr/apa")
    @ResponseBody
    public List<User> getAllUsers() throws Exception {
        return solrService.getAllIndexed();
    }
}