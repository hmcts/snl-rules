package uk.gov.hmcts.reform.sandl.snlrules.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.sandl.snlrules.config.DroolsConfiguration;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(DroolsServiceFactory.class)
public class DroolsServiceFactoryTests {
    @MockBean
    private DroolsConfiguration droolsConfiguration;

    @Autowired
    private DroolsServiceFactory droolsServiceFactory;

    @Before
    public void before() {
        when(droolsConfiguration.getRulesDefinition()).thenReturn("Sessions");
    }

    @Test
    public void should_return_instance_of_drools_engine_when_rules_provided() {
        assertThat(droolsServiceFactory.getInstance("Search")).isInstanceOfAny(DroolsService.class);
    }

    @Test
    public void should_return_default_instance_of_drools_engine_when_rules_not_provided() {
        assertThat(droolsServiceFactory.getInstance(null)).isInstanceOfAny(DroolsService.class);
    }
}
