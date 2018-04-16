package uk.gov.hmcts.reform.sandl.snlrules.services;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import uk.gov.hmcts.reform.sandl.snlrules.drools.TrackingFactsChangedEventListener;
import uk.gov.hmcts.reform.sandl.snlrules.drools.TrackingProcessStatusEventListener;
import uk.gov.hmcts.reform.sandl.snlrules.drools.TrackingRulesFiredEventListener;


@Service
@ApplicationScope
public class DroolsService {
    private final KieSession rulesSession;
    private final TrackingRulesFiredEventListener trackingRulesFiredEventListener;
    private final TrackingFactsChangedEventListener trackingFactsChangedEventListener;
    private final TrackingProcessStatusEventListener trackingProcessStatusEventListener;

    public DroolsService() {
        // load up the knowledge base
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        rulesSession = kieContainer.newKieSession("ksession-rules");

        trackingRulesFiredEventListener = new TrackingRulesFiredEventListener();
        trackingFactsChangedEventListener = new TrackingFactsChangedEventListener();
        trackingProcessStatusEventListener = new TrackingProcessStatusEventListener();
        rulesSession.addEventListener(trackingRulesFiredEventListener);
        rulesSession.addEventListener(trackingFactsChangedEventListener);
        rulesSession.addEventListener(trackingProcessStatusEventListener);

        /*
        PackageBuilder packageBuilder = new PackageBuilder();
        InputStream resourceAsStream = getClass().getResourceAsStream(ruleFile);
        Reader reader = new InputStreamReader(resourceAsStream);
        packageBuilder.addPackageFromDrl(reader);
        org.drools.core.rule.Package rulesPackage = packageBuilder.getPackage();
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage(rulesPackage);
        WorkingMemory workingMemory = ruleBase.newStatefulSession();
        AgendaEventListener employeeRuleTracktListener = new TrackingRuleFiredEventListener();
        workingMemory.addEventListener(employeeRuleTracktListener);
         */
    }

    public KieSession getRulesSession() {
        return rulesSession;
    }

    public TrackingRulesFiredEventListener getTrackingRulesFiredEventListener() {
        return trackingRulesFiredEventListener;
    }

    public TrackingFactsChangedEventListener getTrackingFactsChangedEventListener() {
        return trackingFactsChangedEventListener;
    }

    public TrackingProcessStatusEventListener getTrackingProcessStatusEventListener() {
        return trackingProcessStatusEventListener;
    }
}
