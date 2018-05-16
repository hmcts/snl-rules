package uk.gov.hmcts.reform.sandl.snlrules.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactModification;
import uk.gov.hmcts.reform.sandl.snlrules.messages.FactMessage;
import uk.gov.hmcts.reform.sandl.snlrules.messages.FactMessageHandlerFactory;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsServiceFactory;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class FactMessageController {

    @Autowired
    private FactMessageHandlerFactory factMessageHandlerFactory;

    @Autowired
    private DroolsServiceFactory droolsServiceFactory;

    @RequestMapping(value = "/msg", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<FactModification>> handleMessage(
        @RequestParam(value = "rulesDefinition", required = false) String rulesDefinition,
        @RequestBody FactMessage factMessage) {


        DroolsService droolsService = droolsServiceFactory.getInstance(rulesDefinition);

        synchronized (this) {
            return ok(factMessageHandlerFactory
                .create(factMessage.getType())
                .execute(droolsService, factMessage.getData()));
        }
    }


}
