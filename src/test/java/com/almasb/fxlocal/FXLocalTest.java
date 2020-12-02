/*
 * fx-localization - JavaFX Localization Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxlocal;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class FXLocalTest {

    private FXLocal local;

    @BeforeEach
    public void setUp() {
        local = new FXLocal(Locale.ENGLISH);
    }

    @Test
    @DisplayName("getLocalizedString() returns MISSING_LOCALE! if no locale found")
    public void test_getLocalizedString1() {
        assertThat(local.getLocalizedString("bla-bla"), is("MISSING_LOCALE!"));
    }

    @Test
    @DisplayName("getLocalizedString() returns MISSING_KEY! if locale present but no key found")
    public void test_getLocalizedString2() {
        local.addLocaleData(Locale.ENGLISH, Map.of());

        assertThat(local.getLocalizedString("bla-bla"), is("MISSING_KEY!"));
    }

    @Test
    @DisplayName("getLocalizedString() returns correct value for selected locale")
    public void test_getLocalizedString3() {
        local.addLocaleData(Locale.ENGLISH, Map.of("someKey", "someValue"));

        assertThat(local.getLocalizedString("someKey"), is("someValue"));
    }

    @Test
    @DisplayName("getLocalizedString() returns correct value for given locale")
    public void test_getLocalizedString4() {
        local.addLocaleData(Locale.FRENCH, Map.of("someKey", "someValue"));

        assertThat(local.getLocalizedString("someKey", Locale.FRENCH), is("someValue"));
    }
}
