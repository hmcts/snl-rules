package uk.gov.hmcts.reform.sandl.snlrules.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterators;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.reform.sandl.snlrules.model.BookableJudge;
import uk.gov.hmcts.reform.sandl.snlrules.model.BookableRoom;
import uk.gov.hmcts.reform.sandl.snlrules.security.S2SAuthenticationService;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsServiceFactory;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @MockBean
    @SuppressWarnings("PMD.UnusedPrivateField")
    S2SAuthenticationService s2SAuthenticationService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DroolsServiceFactory droolsServiceFactory;
    @MockBean
    private DroolsService droolsService;
    @MockBean
    private KieSession kieSession;
    @MockBean
    private QueryResults queryResults;
    private ObjectMapper objectMapper;

    @Before
    public void setupMock() {
        objectMapper = new ObjectMapper();
        when(kieSession.getQueryResults(any(), any(), any(), any(), any(), any())).thenReturn(queryResults);
        when(droolsService.getRulesSession()).thenReturn(kieSession);
        when(droolsServiceFactory.getInstance("Search")).thenReturn(droolsService);
    }

    @Test
    public void should_return_0_result() throws Exception {
        List<QueryResultsRow> queryResultsRows = new ArrayList<>();
        when(queryResults.spliterator()).thenReturn(queryResultsRows.spliterator());

        MvcResult response = mockMvc.perform(get(
            "/search?from=2018-06-20 10:00&to=2018-06-22 12:00"
                + "&durationInSeconds=600"
                + "&judge=b5bc80ec-8306-4f0f-8c6e-af218bb116c2&room=30bcf571-45ca-4528-9d05-ce51b5e3fcde")
            .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk()).andReturn();

        assertThat(response.getResponse().getContentAsString()).isEqualToIgnoringCase("[]");
    }

    @Test
    public void should_return_first_100_result() throws Exception {
        List<QueryResultsRow> queryResultsRows = new ArrayList<>();
        for (int i = 0; i < 112; i++) {
            QueryResultsRow qrr = Mockito.mock(QueryResultsRow.class);
            when(qrr.get("$bookableStartId")).thenReturn(OffsetDateTime.now());
            when(qrr.get("$bookableEnd")).thenReturn(OffsetDateTime.now());
            when(qrr.get("$jb")).thenReturn(new BookableJudge(UUID.randomUUID().toString(),//NOPMD
                OffsetDateTime.now(), Duration.ofMinutes(30)));
            when(qrr.get("$rb")).thenReturn(new BookableRoom(UUID.randomUUID().toString(),//NOPMD
                OffsetDateTime.now(), Duration.ofMinutes(30)));
            queryResultsRows.add(qrr);
        }

        when(queryResults.spliterator()).thenReturn(queryResultsRows.spliterator());

        MvcResult response = mockMvc.perform(get("/search?from=2018-06-20 10:00&to=2018-06-22 12:00"
            + "&durationInSeconds=600"
            + "&judge=b5bc80ec-8306-4f0f-8c6e-af218bb116c2&room=30bcf571-45ca-4528-9d05-ce51b5e3fcde")
            .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk()).andReturn();

        JsonNode nodes = objectMapper.readTree(response.getResponse().getContentAsString());

        assertThat(Iterators.size(nodes.elements())).isEqualTo(100);
    }
}
