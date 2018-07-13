package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.sandl.snlrules.model.Room;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DeleteFactCommandTests {
    private static final String roomFactJson =
        "{\"id\": \"b81c5ef4-2992-4b61-895f-6f19d9a93740\",\"name\": \"The Biggest Room\"}";

    @Mock
    private DroolsService droolsService;

    @Mock
    private KieSession kieSession;

    @InjectMocks
    private DeleteFactCommand deleteFactCommand;

    @Before
    public void setup() {
        doNothing().when(droolsService).clearFactModifications();
        doNothing().when(kieSession).update(any(), any());
        when(kieSession.fireAllRules()).thenReturn(0);
        when(droolsService.getRulesSession()).thenReturn(kieSession);
        when(droolsService.getFactModifications()).thenReturn(new ArrayList<>());
    }

    @Test
    public void should_work_when_fact_found() {
        FactHandle factHandle = mock(FactHandle.class);
        when(kieSession.getFactHandle(any())).thenReturn(factHandle);
        deleteFactCommand.setFactType(Room.class);
        assertThat(deleteFactCommand.execute(droolsService, roomFactJson)).isEmpty();
    }

    @Test(expected = RuntimeException.class)
    public void should_not_when_fact_not_found() {
        when(kieSession.getFactHandle(any())).thenReturn(null);
        deleteFactCommand.setFactType(Room.class);
        deleteFactCommand.execute(droolsService, roomFactJson);
    }
}
