package uk.gov.hmcts.reform.sandl.snlrules.controllers;

import org.kie.api.runtime.ClassObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.sandl.snlrules.model.HearingPart;
import uk.gov.hmcts.reform.sandl.snlrules.model.Judge;
import uk.gov.hmcts.reform.sandl.snlrules.model.Problem;
import uk.gov.hmcts.reform.sandl.snlrules.model.Room;
import uk.gov.hmcts.reform.sandl.snlrules.model.Session;
import uk.gov.hmcts.reform.sandl.snlrules.model.SessionType;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Day;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Hour;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Minute;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Month;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Year;
import uk.gov.hmcts.reform.sandl.snlrules.model.reload.ReloadStatus;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsServiceFactory;

import java.util.Collection;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("export")
public class DataExportController {
    @Autowired
    private DroolsServiceFactory droolsServiceFactory;

    @GetMapping(path = "/html")
    public ResponseEntity<String> state(
        @RequestParam(value = "rulesDefinition", required = false) String rulesDefinition) {
        DroolsService droolsService = droolsServiceFactory.getInstance(rulesDefinition);
        return ok(toPlainHtml(droolsService));
    }

    @GetMapping(path = "/counts")
    public ResponseEntity<String> counts(
        @RequestParam(value = "rulesDefinition", required = false) String rulesDefinition) {
        DroolsService droolsService = droolsServiceFactory.getInstance(rulesDefinition);
        return ok(toCountsPlainHtml(droolsService));
    }

    private String toPlainHtml(DroolsService droolsService) {
        StringBuilder builder = new StringBuilder();

        listFacts(builder, droolsService, Problem.class, "PROBLEM");
        listFacts(builder, droolsService, Room.class, "ROOM");
        listFacts(builder, droolsService, Judge.class, "JUDGE");
        listFacts(builder, droolsService, Session.class, "SESSION");
        listFacts(builder, droolsService, HearingPart.class, "HEARING PART");
        listFacts(builder, droolsService, SessionType.class, "SESSION TYPE");
        listFacts(builder, droolsService, Year.class, "YEAR");
        listFacts(builder, droolsService, Month.class, "MONTH");
        listFacts(builder, droolsService, Day.class, "DAY");
        listFacts(builder, droolsService, Hour.class, "HOUR");
        listFacts(builder, droolsService, Minute.class, "MINUTE");
        listFacts(builder, droolsService, ReloadStatus.class, "LOADED");

        return builder.toString();
    }

    private void listFacts(StringBuilder builder, DroolsService droolsService, Class filter, String name) {
        builder.append("<br><br>=== " + name + " ===");
        Collection<? extends Object> objs = droolsService.getRulesSession().getObjects(new ClassObjectFilter(filter));
        for (Object obj : objs) {
            builder.append("<br>     " + obj.toString());
        }
    }

    private String toCountsPlainHtml(DroolsService droolsService) {
        StringBuilder builder = new StringBuilder();

        countFacts(builder, droolsService, Problem.class, "PROBLEM");
        countFacts(builder, droolsService, Room.class, "ROOM");
        countFacts(builder, droolsService, Judge.class, "JUDGE");
        countFacts(builder, droolsService, Session.class, "SESSION");
        countFacts(builder, droolsService, HearingPart.class, "HEARING PART");
        countFacts(builder, droolsService, SessionType.class, "SESSION TYPE");
        countFacts(builder, droolsService, Year.class, "YEAR");
        countFacts(builder, droolsService, Month.class, "MONTH");
        countFacts(builder, droolsService, Day.class, "DAY");
        countFacts(builder, droolsService, Hour.class, "HOUR");
        countFacts(builder, droolsService, Minute.class, "MINUTE");
        countFacts(builder, droolsService, ReloadStatus.class, "LOADED");

        return builder.toString();
    }

    private void countFacts(StringBuilder builder, DroolsService droolsService, Class filter, String name) {
        Collection<? extends Object> objs = droolsService.getRulesSession().getObjects(new ClassObjectFilter(filter));
        builder.append(objs.size() + " " + name + "<br>");
    }
}
