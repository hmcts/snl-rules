package uk.gov.hmcts.reform.sandl.snlrules.drools;

import org.drools.core.event.DefaultAgendaEventListener;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;

public class TrackingRulesFiredEventListener extends DefaultAgendaEventListener {

    @Override
    public void matchCreated(MatchCreatedEvent event) {
        // intentionally left blank
    }

    @Override
    public void matchCancelled(MatchCancelledEvent event) {
        // intentionally left blank
    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        // intentionally left blank
    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        // intentionally left blank
    }

    @Override
    public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
        // intentionally left blank
    }

    @Override
    public void agendaGroupPushed(AgendaGroupPushedEvent event) {
        // intentionally left blank
    }

    @Override
    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
        // intentionally left blank
    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
        // intentionally left blank
    }

    @Override
    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
        // intentionally left blank
    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
        // intentionally left blank
    }
}
