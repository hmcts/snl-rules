package uk.gov.hmcts.reform.sandl.snlrules.drools;

import org.drools.core.event.DefaultRuleRuntimeEventListener;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FactsChangedEventListener extends DefaultRuleRuntimeEventListener {
    private static final Logger logger = LoggerFactory.getLogger(FactsChangedEventListener.class);

    @Override
    public void objectInserted(final ObjectInsertedEvent event) {
        logger.debug("INSERTED: " + event.getObject());
    }

    @Override
    public void objectUpdated(final ObjectUpdatedEvent event) {
        logger.debug("UPDATED OLD: " + event.getOldObject());
        logger.debug("UPDATED NEW: " + event.getObject());
    }

    @Override
    public void objectDeleted(final ObjectDeletedEvent event) {
        logger.debug("DELETED OLD: " + event.getOldObject());
    }
}
