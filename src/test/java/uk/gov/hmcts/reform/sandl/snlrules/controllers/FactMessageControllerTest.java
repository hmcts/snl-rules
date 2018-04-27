package uk.gov.hmcts.reform.sandl.snlrules.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactModification;
import uk.gov.hmcts.reform.sandl.snlrules.messages.FactMessageHandlerFactory;
import uk.gov.hmcts.reform.sandl.snlrules.messages.commands.InsertFactCommand;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FactMessageController.class)
public class FactMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FactMessageHandlerFactory factMessageHandlerFactory;

    @MockBean
    private InsertFactCommand insertFactCommand;

    @Before
    public void setupMock() {
        when(insertFactCommand.execute(any()))
            .thenReturn(new ArrayList<FactModification>());
        when(factMessageHandlerFactory.create("insert-judge"))
            .thenReturn(insertFactCommand);
    }

    @Test
    public void should_the_endpoint_works() throws Exception {

        String msg = "{\t\"type\": \"insert-judge\",\n"
            + "\t\"data\": \"{\\\"id\\\": \\\"j1\\\",\\\"name\\\": \\\"John Smith\\\"}\"}";

        MvcResult response = mockMvc.perform(post("/msg")
            .contentType(APPLICATION_JSON_UTF8)
            .content(msg))
            .andExpect(status().isOk()).andReturn();

        assertThat(response.getResponse().getContentAsString()).isEqualToIgnoringCase("[]");
    }
}
