package uk.gov.hmcts.reform.sandl.snlrules.services;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import uk.gov.hmcts.reform.sandl.snlrules.config.DroolsConfiguration;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactsChangedEventListener;

import javax.annotation.PostConstruct;

@Service
@ApplicationScope
public class DroolsService {
    private static final Logger logger = LoggerFactory.getLogger(DroolsService.class);

    @Autowired
    private DroolsConfiguration droolsConfiguration;

    private KieSession rulesSession;

    @PostConstruct
    public void init() {
        logger.info("Starting drools service, container and session for {}", droolsConfiguration.getRulesKSession());
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        rulesSession = kieContainer.newKieSession(droolsConfiguration.getRulesKSession());

        rulesSession.addEventListener(new FactsChangedEventListener());
    }

    public KieSession getRulesSession() {
        return rulesSession;
    }
}
