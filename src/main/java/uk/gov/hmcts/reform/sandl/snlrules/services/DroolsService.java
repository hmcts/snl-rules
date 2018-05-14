package uk.gov.hmcts.reform.sandl.snlrules.services;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactModification;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactsChangedEventListener;
import uk.gov.hmcts.reform.sandl.snlrules.drools.RulesMatchEventListener;

import java.util.List;

public class DroolsService {
    private static final Logger logger = LoggerFactory.getLogger(DroolsService.class);

    private final String rulesDefinition;
    private FactsChangedEventListener factsChangedEventListener;
    private KieSession rulesSession;

    public DroolsService(String rulesDefinition) {
        this.rulesDefinition = rulesDefinition;
    }

    public void init() {
        logger.info("Starting drools service, container and session for {}", rulesDefinition);
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        rulesSession = kieContainer.newKieSession(rulesDefinition);

        if (rulesSession == null) {
            throw new RuntimeException(
                String.format("Drools engine with rules %s cannot be created.", rulesDefinition));
        }

        factsChangedEventListener = new FactsChangedEventListener();

        rulesSession.addEventListener(factsChangedEventListener);
        rulesSession.addEventListener(new RulesMatchEventListener());
    }

    public KieSession getRulesSession() {
        return rulesSession;
    }

    public List<FactModification> getFactModifications() {
        return factsChangedEventListener.getFactModifications();
    }

    public void clearFactModifications() {
        factsChangedEventListener.clear();
    }
}
