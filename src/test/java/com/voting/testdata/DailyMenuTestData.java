package com.voting.testdata;

import com.voting.model.DailyMenu;
import static org.assertj.core.api.Assertions.assertThat;

public class DailyMenuTestData {
    public static void assertMatch(DailyMenu actual, DailyMenu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,  "dishes");
    }
}
