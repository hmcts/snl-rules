package uk.gov.hmcts.reform.sandl.snlrules.drools;

import org.drools.core.event.DefaultRuleRuntimeEventListener;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FactsChangedEventListener extends DefaultRuleRuntimeEventListener {
    private static final Logger logger = LoggerFactory.getLogger(FactsChangedEventListener.class);

    private final List<FactModification> factModifications =
        Collections.synchronizedList(new ArrayList<FactModification>());

    @Override
    public void objectInserted(final ObjectInsertedEvent event) {
        logger.debug("INSERTED: " + event.getObject());
        factModifications.add(new FactModification(null, (Fact) event.getObject()));
    }

    @Override
    public void objectUpdated(final ObjectUpdatedEvent event) {
        logger.debug("UPDATED OLD: " + event.getOldObject());
        logger.debug("UPDATED NEW: " + event.getObject());
        factModifications.add(new FactModification((Fact) event.getOldObject(), (Fact) event.getObject()));
    }

    @Override
    public void objectDeleted(final ObjectDeletedEvent event) {
        logger.debug("DELETED OLD: " + event.getOldObject());
        factModifications.add(new FactModification((Fact) event.getOldObject(), null));
    }


    public void clear() {
        factModifications.clear();
    }

    public List<FactModification> getFactModifications() {
        // Make a copy so it does not interfere with other calls
        // when optimiser keeps the reference to the object and mixes up
        // results for the calling classes
        return new ArrayList<>(factModifications);
    }

}
