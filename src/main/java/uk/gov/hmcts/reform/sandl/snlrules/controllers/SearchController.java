package uk.gov.hmcts.reform.sandl.snlrules.controllers;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.sandl.snlrules.model.SessionProposition;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsServiceFactory;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    DroolsServiceFactory droolsServiceFactory;

    @GetMapping(path = "/search")
    public ResponseEntity<SessionProposition> searchPossibleSessions() {
        DroolsService droolsService = droolsServiceFactory.getInstance("Search");

        searchEngine(droolsService);


        return ResponseEntity.ok(new SessionProposition());
    }

    public List<SessionProposition> searchEngine(DroolsService droolsService) {

        KieSession session = droolsService.getRulesSession();

        session.getQueryResults();

    }
}
