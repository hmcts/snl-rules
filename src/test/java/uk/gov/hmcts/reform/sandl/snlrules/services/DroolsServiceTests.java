package uk.gov.hmcts.reform.sandl.snlrules.services;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.sandl.snlrules.model.reload.ReloadStatus;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class DroolsServiceTests {
    @MockBean
    private KieSession kieSession;

    private DroolsService droolsService = new DroolsService("Sessions");

    @Before
    public void before() {
        droolsService.setRulesSession(kieSession);
    }

    @Test
    public void getReloadStatus_returns_reloadStatus() {
        OffsetDateTime start = OffsetDateTime.now();
        OffsetDateTime finish = start.plusSeconds(25);
        ReloadStatus reloadStatus = new ReloadStatus(start, finish);
        List<Object> objects = new ArrayList<>();
        objects.add(reloadStatus);

        doReturn(objects).when(kieSession).getObjects(any());

        val status = droolsService.getReloadStatus();

        assertThat(status).isEqualTo(reloadStatus);
    }

    @Test
    public void getReloadStatus_returns_no_reloadStatus() {
        List<Object> objects = new ArrayList<>();
        doReturn(objects).when(kieSession).getObjects(any());

        val status = droolsService.getReloadStatus();

        assertThat(status).isNull();
    }
}
