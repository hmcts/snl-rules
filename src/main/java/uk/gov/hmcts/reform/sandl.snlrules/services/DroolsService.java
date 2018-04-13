package uk.gov.hmcts.reform.sandl.snlrules.services;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;


@Service
@ApplicationScope
public class DroolsService {
    private final KieSession rulesSession;

    public DroolsService() {
        // load up the knowledge base
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        rulesSession = kieContainer.newKieSession("ksession-rules");
    }

    public KieSession getRulesSession() {
        return rulesSession;
    }
}
