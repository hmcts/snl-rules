package uk.gov.hmcts.reform.sandl.snlrules.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.sandl.snlrules.model.Problem;
import uk.gov.hmcts.reform.sandl.snlrules.model.ProblemReference;
import uk.gov.hmcts.reform.sandl.snlrules.model.ProblemSeverities;
import uk.gov.hmcts.reform.sandl.snlrules.model.ProblemTypes;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
public class ProblemTests {

    @Test
    public void should_set_the_same_id_when_passing_the_same_arg_to_constructor() {
        ProblemTypes problemType = ProblemTypes.Listing_policy_violation;
        ProblemSeverities problemSeverities = ProblemSeverities.Warning;
        String message = "Test Msg";
        ProblemReference problemReference = new ProblemReference("someFactId","fact", "some desc");

        Problem problemA = new Problem(problemType, problemSeverities, message, problemReference);
        Problem problemB = new Problem(problemType, problemSeverities, message, problemReference);

        assertThat(problemA.getId()).isEqualTo(problemB.getId());
    }
}
