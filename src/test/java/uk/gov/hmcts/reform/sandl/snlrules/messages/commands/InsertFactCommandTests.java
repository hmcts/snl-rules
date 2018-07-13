package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.sandl.snlrules.model.Room;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class InsertFactCommandTests {
    private static final String roomFactJson =
        "{\"id\": \"8fb73f6a-f961-47fd-b08b-e1675e516d2d\",\"name\": \"The Biggest Room\"}";

    private static final String incorrectFactJson =
        "{\"idIsIncorrect\": \"8fb73f6a-f961-47fd-b08b-e1675e516d2d\",\"nameWrong\": \"The Biggest Room\"}";

    @Mock
    private DroolsService droolsService;

    @Mock
    private KieSession kieSession;

    @InjectMocks
    private InsertFactCommand insertFactCommand;

    @Before
    public void setup() {
        doNothing().when(droolsService).clearFactModifications();
        when(kieSession.insert(any())).thenReturn(null);
        when(kieSession.fireAllRules()).thenReturn(0);
        when(droolsService.getRulesSession()).thenReturn(kieSession);
        when(droolsService.getFactModifications()).thenReturn(new ArrayList<>());
    }

    @Test
    public void should_work_with_correct_type_and_data() {
        insertFactCommand.setFactType(Room.class);
        assertThat(insertFactCommand.execute(droolsService, roomFactJson)).isEmpty();
    }

    @Test(expected = RuntimeException.class)
    public void should_fail_when_json_is_incorrect() {
        insertFactCommand.setFactType(Room.class);
        assertThat(insertFactCommand.execute(droolsService, incorrectFactJson)).isEmpty();
    }
}
