package uk.gov.hmcts.reform.sandl.snlrules.controllers;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        @RequestParam(value = "durationInSeconds") int duration,
        @RequestParam(value = "judge", required = false) String judgeId,
        @RequestParam(value = "room", required = false) String roomId) {
        DroolsService droolsService = droolsServiceFactory.getInstance("Search");

        return ok(searchEngine(droolsService, Duration.ofSeconds(duration),
            offsetDateTimeOf(from), offsetDateTimeOf(to), judgeId, roomId));
    }

    private List<SessionProposition> searchEngine(DroolsService droolsService, Duration duration,
                             OffsetDateTime from, OffsetDateTime to, String judgeId, String roomId) {

        KieSession session = droolsService.getRulesSession();

        QueryResults queryResults = session.getQueryResults("JudgeAndRoomAvailable",
            judgeId, roomId, duration, from, to);

        return StreamSupport.stream(queryResults.spliterator(), false)
            .sorted((row1, row2) -> {
                OffsetDateTime bookableStart1 = (OffsetDateTime) row1.get("$bookableStart");
                OffsetDateTime bookableStart2 = (OffsetDateTime) row2.get("$bookableStart");

                return bookableStart1.compareTo(bookableStart2);
            }).limit(100).map(row -> {
                OffsetDateTime bookableStart = (OffsetDateTime) row.get("$bookableStart");
                OffsetDateTime bookableEnd = (OffsetDateTime) row.get("$bookableEnd");

                BookableJudge bj = (BookableJudge) row.get("$jb");
                BookableRoom rb = (BookableRoom) row.get("$rb");

                return new SessionProposition(bj.getJudgeId(), rb.getRoomId(), bookableStart, bookableEnd);
            })
            .collect(Collectors.toList());
    }
}
