package uk.gov.hmcts.reform.sandl.snlrules.model;

public enum ProblemTypes {
    Session_does_not_have_a_judge_available,
    Session_does_not_have_a_judge_4_weeks_or_nearer_before_start,
    Session_does_not_have_a_room_4_weeks_or_nearer_before_start,
    Hearing_case_type_does_not_match_the_session_case_type,
    Double_booking_of_judge_includes_any_overlapping,
    Double_booking_of_room_includes_any_overlapping
}
