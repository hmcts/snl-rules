package uk.gov.hmcts.reform.sandl.snlrules.messages;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.sandl.snlrules.exception.FactCommandException;
import uk.gov.hmcts.reform.sandl.snlrules.messages.commands.InsertFactCommand;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(FactMessageHandlerFactory.class)
public class FactMessageHandlerFactoryTests {

    @MockBean
    private InsertFactCommand insertFactCommand;//NOPMD

    @Autowired
    private FactMessageHandlerFactory factMessageHandlerFactory;

    @Test
    public void should_return_valid_command_when_known_command_and_fact() {
        assertThat(factMessageHandlerFactory.create("insert-judge")).isInstanceOfAny(InsertFactCommand.class);
    }

    @Test(expected = FactCommandException.class)
    public void should_throw_exception_when_invalid_command_and_known_fact() {
        assertThat(factMessageHandlerFactory.create("dosomething-judge")).isInstanceOfAny(InsertFactCommand.class);
    }

    @Test(expected = FactCommandException.class)
    public void should_throw_exception_when_valid_command_and_invalid_fact() {
        assertThat(factMessageHandlerFactory.create("update-something")).isInstanceOfAny(InsertFactCommand.class);
    }

    @Test(expected = FactCommandException.class)
    public void should_throw_exception_when_invalid_command_and_invalid_fact() {
        assertThat(factMessageHandlerFactory.create("dosomething-something")).isInstanceOfAny(InsertFactCommand.class);
    }

    @Test(expected = FactCommandException.class)
    public void should_throw_exception_when_nocommand_or_fact() {
        assertThat(factMessageHandlerFactory.create("dosomething")).isInstanceOfAny(InsertFactCommand.class);
    }

    @Test(expected = FactCommandException.class)
    public void should_throw_exception_when_empty() {
        assertThat(factMessageHandlerFactory.create("")).isInstanceOfAny(InsertFactCommand.class);
    }
}
