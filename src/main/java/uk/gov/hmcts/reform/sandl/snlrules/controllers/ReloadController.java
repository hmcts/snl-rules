package uk.gov.hmcts.reform.sandl.snlrules.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.sandl.snlrules.model.reload.ReloadStatus;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsServiceFactory;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "reload")
public class ReloadController {
    @Autowired
    private DroolsServiceFactory droolsServiceFactory;

    @GetMapping(value = "/status")
    public ResponseEntity<ReloadStatus> status(
        @RequestParam(value = "rulesDefinition", required = false) String rulesDefinition) {

        DroolsService droolsService = droolsServiceFactory.getInstance(rulesDefinition);
        return ok(droolsService.getReloadStatus());
    }
}
