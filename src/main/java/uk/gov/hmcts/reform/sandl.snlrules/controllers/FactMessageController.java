package uk.gov.hmcts.reform.sandl.snlrules.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.sandl.snlrules.messages.FactMessage;
import uk.gov.hmcts.reform.sandl.snlrules.messages.FactMessageHandlerFactory;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class FactMessageController {

    @Autowired
    private FactMessageHandlerFactory factMessageHandlerFactory;

    @RequestMapping(value = "/msg", method = RequestMethod.PUT)
    public ResponseEntity<String> msgw(@RequestBody FactMessage factMessage) {

        factMessageHandlerFactory.create(factMessage.getType()).execute(factMessage.getData());

        return ok("DONE");
    }
}
