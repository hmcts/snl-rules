package uk.gov.hmcts.reform.sandl.snlrules.drools;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RulesMatchEventListener implements AgendaEventListener {
    private static final Logger logger = LoggerFactory.getLogger(RulesMatchEventListener.class);

    @Override
    public void matchCreated(MatchCreatedEvent event) {
        logger.trace("Rule match: {}", event.getMatch().getRule().getName());
    }

    @Override
    public void matchCancelled(MatchCancelledEvent event) {
        // not used
    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        // not used
    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        // not used
    }

    @Override
    public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
        // not used
    }

    @Override
    public void agendaGroupPushed(AgendaGroupPushedEvent event) {
        // not used
    }

    @Override
    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
        // not used
    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
        // not used
    }

    @Override
    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
        // not used
    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
        // not used
    }
}
