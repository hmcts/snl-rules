package uk.gov.hmcts.reform.sandl.snlrules.controllers;

import org.kie.api.runtime.ClassObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.sandl.snlrules.model.Availability;
import uk.gov.hmcts.reform.sandl.snlrules.model.Issue;
import uk.gov.hmcts.reform.sandl.snlrules.model.Judge;
import uk.gov.hmcts.reform.sandl.snlrules.model.Session;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.util.Collection;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class DataExportController {

    @Autowired
    private DroolsService droolsService;

    @RequestMapping("/exporthtml")
    public ResponseEntity<String> state() {
        return ok(toPlainHtml());
    }

    private String toPlainHtml() {
        StringBuilder builder = new StringBuilder();

        listFacts(builder, Availability.class, "AVAILABILITY");
        listFacts(builder, Judge.class, "JUDGE");
        listFacts(builder, Session.class, "SESSION");
        listFacts(builder, Issue.class, "ISSUE");

        return builder.toString();
    }

    private void listFacts(StringBuilder builder, Class filter, String name) {
        builder.append("<br><br>=== " + name + " ===");
        Collection<? extends Object> objs = droolsService.getRulesSession().getObjects(new ClassObjectFilter(filter));
        for (Object obj: objs) {
            builder.append("<br>     " + obj.toString());
        }
    }
}
