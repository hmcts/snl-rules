package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.sandl.snlrules.model.Room;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UpsertFactCommandTests {
    private static final String roomFactJson =
        "{\"id\": \"1cd8a55f-f7f6-4983-acf6-4a3a1afe8040\",\"name\": \"The Biggest Room\"}";

    @Mock
    private DroolsService droolsService;

    @Mock
    private KieSession kieSession;

    @InjectMocks
    private UpsertFactCommand upsertFactCommand;

    @Before
    public void setup() {
        doNothing().when(droolsService).clearFactModifications();
        doNothing().when(kieSession).update(any(), any());
        when(kieSession.fireAllRules()).thenReturn(0);
        when(droolsService.getRulesSession()).thenReturn(kieSession);
        when(droolsService.getFactModifications()).thenReturn(new ArrayList<>());
    }

    @Test
    public void should_update_when_fact_found() {
        FactHandle factHandle = mock(FactHandle.class);
        when(kieSession.getFactHandle(any())).thenReturn(factHandle);
        upsertFactCommand.setFactType(Room.class);
        assertThat(upsertFactCommand.execute(droolsService, roomFactJson)).isEmpty();
        verify(kieSession).update(Mockito.eq(factHandle), any());
    }

    @Test
    public void should_insert_when_fact_not_found() {
        when(kieSession.getFactHandle(any())).thenReturn(null);
        upsertFactCommand.setFactType(Room.class);
        upsertFactCommand.execute(droolsService, roomFactJson);
        verify(kieSession).insert(any());
    }
}
