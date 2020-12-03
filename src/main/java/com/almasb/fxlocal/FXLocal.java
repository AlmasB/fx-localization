/*
 * fx-localization - JavaFX Localization Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxlocal;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Provides the user with the ability to localize a given String key to a String or StringBinding
 * in the requested Locale.
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public final class FXLocal {

    private final ObjectProperty<Locale> selectedLocale;

    private final Map<Locale, Map<String, String>> localeData = new HashMap<>();

    /**
     * Construct using the default Locale provided by JVM.
     */
    public FXLocal() {
        this(Locale.getDefault());
    }

    /**
     * Construct and select default locale for FXLocal.
     */
    public FXLocal(Locale defaultSelectedLocale) {
        selectedLocale = new SimpleObjectProperty<>(defaultSelectedLocale);
    }

    public ObjectProperty<Locale> selectedLocaleProperty() {
        return selectedLocale;
    }

    public Locale getSelectedLocale() {
        return selectedLocale.get();
    }

    public void setSelectedLocale(Locale selectedLocale) {
        this.selectedLocale.set(selectedLocale);
    }

    /**
     * @return a new StringBinding for a given [key] whose value automatically changes to the correct localization
     * when the selected locale is modified
     */
    public StringBinding localizedStringBinding(String key) {
        return Bindings.createStringBinding(() -> getLocalizedString(key), selectedLocale);
    }

    /**
     * @return a localized String for a given [key] in the selected Locale
     */
    public String getLocalizedString(String key) {
        return getLocalizedString(key, getSelectedLocale());
    }

    /**
     * @return a localized String for a given [key] in the given [Locale]
     */
    public String getLocalizedString(String key, Locale locale) {
        if (!localeData.containsKey(locale))
            return "MISSING_LOCALE!";

        return localeData.get(locale).getOrDefault(key, "MISSING_KEY!");
    }

    /**
     * Populate with localization data for a given Locale.
     *
     * @param locale the locale for which the data is being added.
     * @param bundle a map of key-value pairs, where K is the key to localize, V is the localization in given Locale
     */
    public void addLocaleData(Locale locale, ResourceBundle bundle) {
        var map = localeData.getOrDefault(locale, new HashMap<>());

        bundle.keySet().forEach(key -> map.put(key, bundle.getString(key)));

        localeData.put(locale, map);
    }

    /**
     * Populate with localization data for a given Locale.
     *
     * @param locale the locale for which the data is being added.
     * @param data a map of key-value pairs, where K is the key to localize, V is the localization in given Locale
     */
    public void addLocaleData(Locale locale, Map<String, String> data) {
        var map = localeData.getOrDefault(locale, new HashMap<>());
        map.putAll(data);

        localeData.put(locale, map);
    }
}
