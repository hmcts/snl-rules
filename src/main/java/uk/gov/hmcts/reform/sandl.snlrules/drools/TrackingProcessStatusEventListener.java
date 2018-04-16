package uk.gov.hmcts.reform.sandl.snlrules.drools;

import org.drools.core.event.DefaultProcessEventListener;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;

public class TrackingProcessStatusEventListener extends DefaultProcessEventListener {

    @Override
    public void afterNodeLeft(ProcessNodeLeftEvent event) {
    }

    @Override
    public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent event) {
    }

    @Override
    public void afterProcessStarted(ProcessStartedEvent event) {
    }

    @Override
    public void afterVariableChanged(ProcessVariableChangedEvent event) {
    }

    @Override
    public void beforeNodeLeft(ProcessNodeLeftEvent event) {
    }

    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
    }

    @Override
    public void beforeProcessCompleted(ProcessCompletedEvent event) {
    }

    @Override
    public void beforeProcessStarted(ProcessStartedEvent event) {
    }

    @Override
    public void beforeVariableChanged(ProcessVariableChangedEvent event) {
    }
}
