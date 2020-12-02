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
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public final class FXLocal {

    private final ObjectProperty<Locale> selectedLocale;

    private final Map<Locale, Map<String, String>> localeData = new HashMap<>();

    public FXLocal() {
        this(Locale.getDefault());
    }

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

    public StringBinding localizedStringBinding(String key) {
        return Bindings.createStringBinding(() -> getLocalizedString(key), selectedLocale);
    }

    public String getLocalizedString(String key) {
        return getLocalizedString(key, getSelectedLocale());
    }

    public String getLocalizedString(String key, Locale locale) {
        if (!localeData.containsKey(locale))
            return "MISSING_LOCALE!";

        return localeData.get(locale).getOrDefault(key, "MISSING_KEY!");
    }

    public void addLocaleData(Locale locale, ResourceBundle bundle) {
        var map = localeData.getOrDefault(locale, new HashMap<>());

        bundle.keySet().forEach(key -> map.put(key, bundle.getString(key)));

        localeData.put(locale, map);
    }

    public void addLocaleData(Locale locale, Map<String, String> data) {
        var map = localeData.getOrDefault(locale, new HashMap<>());
        map.putAll(data);

        localeData.put(locale, map);
    }
}
