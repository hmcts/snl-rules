package uk.gov.hmcts.reform.sandl.snlrules.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import uk.gov.hmcts.reform.sandl.snlrules.config.DroolsConfiguration;

import java.util.HashMap;
import java.util.Map;

@Service
@ApplicationScope
public class DroolsServiceFactory {

    private static final Logger logger = LoggerFactory.getLogger(DroolsServiceFactory.class);
    private final Map<String, DroolsService> droolsServices = new HashMap<>();

    @Autowired
    private DroolsConfiguration droolsConfiguration;

    public DroolsService getInstance(String rulesDefinitionName) {
        String rulesDefinition = rulesDefinitionName == null
            ? droolsConfiguration.getRulesDefinition() : rulesDefinitionName;

        if (!droolsServices.containsKey(rulesDefinition)) {
            DroolsService droolsService = new DroolsService(rulesDefinition);
            droolsService.init();

            droolsServices.put(rulesDefinition, droolsService);

            logger.debug("Created drools service for rules definition: {}", rulesDefinition);
        }

        return droolsServices.get(rulesDefinition);
    }
}
