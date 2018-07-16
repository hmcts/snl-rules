package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.sandl.snlrules.exception.FactCommandException;
import uk.gov.hmcts.reform.sandl.snlrules.model.Room;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UpdateFactCommandTests {
    private static final String roomFactJson =
        "{\"id\": \"585bc653-14ee-4b5d-99b5-1c010ee07604\",\"name\": \"The Biggest Room\"}";

    @Mock
    private DroolsService droolsService;

    @Mock
    private KieSession kieSession;

    @InjectMocks
    private UpdateFactCommand updateFactCommand;

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
        updateFactCommand.setFactType(Room.class);
        assertThat(updateFactCommand.execute(droolsService, roomFactJson)).isEmpty();
    }

    @Test(expected = FactCommandException.class)
    public void should_not_when_fact_not_found() {
        when(kieSession.getFactHandle(any())).thenReturn(null);
        updateFactCommand.setFactType(Room.class);
        updateFactCommand.execute(droolsService, roomFactJson);
    }
}
