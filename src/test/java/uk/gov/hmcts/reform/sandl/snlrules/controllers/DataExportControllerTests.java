package uk.gov.hmcts.reform.sandl.snlrules.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.reform.sandl.snlrules.JwtTokenHelper;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Hour;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Minute;
import uk.gov.hmcts.reform.sandl.snlrules.security.S2SAuthenticationService;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsServiceFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DataExportController.class)
public class DataExportControllerTests {
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

    @Before
    public void setupMock() {
        when(droolsService.getRulesSession()).thenReturn(kieSession);
        when(droolsServiceFactory.getInstance(any())).thenReturn(droolsService);
        when(s2SAuthenticationService.validateToken(any())).thenReturn(true);
    }

    @Test
    public void should_return_counts() throws Exception {
        List<Object> objects = new ArrayList<>();
        doReturn(objects).when(kieSession).getObjects(any());

        MvcResult response = mockMvc.perform(get(
            "/exportcounts")
            .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk()).andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("PROBLEM");
    }

    @Test
    public void should_return_objects() throws Exception {
        Hour h = new Hour(10);
        Minute m = new Minute(11);
        List<Object> objects = new ArrayList<>();
        objects.add(h);
        objects.add(m);
        doReturn(objects).when(kieSession).getObjects(any());

        MvcResult response = mockMvc.perform(get(
            "/exporthtml")
            .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk()).andReturn();

        assertThat(response.getResponse().getContentAsString()).contains(h.toString());
        assertThat(response.getResponse().getContentAsString()).contains(m.toString());
    }
}
