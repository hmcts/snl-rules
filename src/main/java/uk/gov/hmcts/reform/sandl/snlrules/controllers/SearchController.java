package uk.gov.hmcts.reform.sandl.snlrules.controllers;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.sandl.snlrules.model.BookableJudge;
import uk.gov.hmcts.reform.sandl.snlrules.model.BookableRoom;
import uk.gov.hmcts.reform.sandl.snlrules.model.SessionProposition;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsServiceFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static uk.gov.hmcts.reform.sandl.snlrules.utils.DateTimeUtils.offsetDateTimeOf;

@RestController
public class SearchController {

    @Autowired
    DroolsServiceFactory droolsServiceFactory;

    @GetMapping(path = "/search")
    public ResponseEntity<List<SessionProposition>> searchPossibleSessions(
        @RequestParam(value = "from") String from,
        @RequestParam(value = "to") String to,
        @RequestParam(value = "duration") int duration,
        @RequestParam(value = "judge", required = false) String judgeId,
        @RequestParam(value = "room", required = false) String roomId) {
        DroolsService droolsService = droolsServiceFactory.getInstance("Search");

        return ok(searchEngine(droolsService, Duration.ofSeconds(duration),
            offsetDateTimeOf(from), offsetDateTimeOf(to), judgeId, roomId));
    }

    public List<SessionProposition> searchEngine(DroolsService droolsService, Duration duration,
                             OffsetDateTime from, OffsetDateTime to, String judgeId, String roomId) {

        KieSession session = droolsService.getRulesSession();

        List<SessionProposition> results = new ArrayList<>();

        QueryResults queryResults = session.getQueryResults("JudgeAndRoomAvailable",
            judgeId, roomId, duration, from, to);

        for (QueryResultsRow row : queryResults) {
            OffsetDateTime bookableStart = (OffsetDateTime) row.get("$bookableStart");
            OffsetDateTime bookableEnd = (OffsetDateTime) row.get("$bookableEnd");

            BookableJudge bj = (BookableJudge) row.get("$jb");
            BookableRoom rb = (BookableRoom) row.get("$rb");

            results.add(new SessionProposition(bj.getJudgeId(), rb.getRoomId(), bookableStart, bookableEnd));
        }

        return results;
    }
}
