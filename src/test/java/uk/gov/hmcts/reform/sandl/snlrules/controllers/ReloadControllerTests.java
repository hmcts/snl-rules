package uk.gov.hmcts.reform.sandl.snlrules.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.reform.sandl.snlrules.model.reload.ReloadStatus;
import uk.gov.hmcts.reform.sandl.snlrules.security.S2SAuthenticationService;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsServiceFactory;

import java.time.OffsetDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReloadController.class)
public class ReloadControllerTests {
    @MockBean
    @SuppressWarnings("PMD.UnusedPrivateField")
    S2SAuthenticationService s2SAuthenticationService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DroolsServiceFactory droolsServiceFactory;
    @MockBean
    private DroolsService droolsService;

    private ReloadStatus reloadStatus;

    @Before
    public void setup() {
        OffsetDateTime start = OffsetDateTime.now();
        OffsetDateTime finish = start.plusSeconds(25);
        reloadStatus = new ReloadStatus(start, finish);

        when(droolsServiceFactory.getInstance(any())).thenReturn(droolsService);
        when(s2SAuthenticationService.validateToken(any())).thenReturn(true);
    }

    @Test
    public void status_should_return_valid_status() throws Exception {
        when(droolsService.getReloadStatus()).thenReturn(reloadStatus);

        mockMvc.perform(get(
            "/reload/status")
            .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("startedAt")));
    }

    @Test
    public void status_should_return_no_status() throws Exception {
        when(droolsService.getReloadStatus()).thenReturn(null);

        mockMvc.perform(get(
            "/reload/status")
            .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
    }
}
