package com.qcadoo.plugins.customTranslations.internal.hooks;

import static com.qcadoo.customTranslation.constants.CustomTranslationFields.KEY;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.LOCALE;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.PLUGIN_IDENTIFIER;
import static com.qcadoo.plugins.customTranslations.constants.CustomTranslationFieldsCTM.PLUGIN_NAME;
import static com.qcadoo.plugins.customTranslations.constants.CustomTranslationFieldsCTM.PROPERTIES_TRANSLATION;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.plugin.api.Plugin;
import com.qcadoo.plugin.api.PluginAccessor;
import com.qcadoo.plugin.api.PluginInformation;

public class CustomTranslationModelHooksCTMTest {

    private CustomTranslationModelHooksCTM customTranslationModelHooksCTM;

    @Mock
    private PluginAccessor pluginAccessor;

    @Mock
    private TranslationService translationService;

    @Mock
    private DataDefinition customTranslationDD;

    @Mock
    private Entity customTranslation;

    @Mock
    private Plugin plugin;

    @Mock
    private PluginInformation pluginInformation;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        customTranslationModelHooksCTM = new CustomTranslationModelHooksCTM();

        ReflectionTestUtils.setField(customTranslationModelHooksCTM, "pluginAccessor", pluginAccessor);
        ReflectionTestUtils.setField(customTranslationModelHooksCTM, "translationService", translationService);
    }

    @Test
    public void shouldUpdateCustomTranslationDataWhenDataIsNull() {
        // given
        given(customTranslation.getStringField(PLUGIN_IDENTIFIER)).willReturn(null);
        given(customTranslation.getStringField(KEY)).willReturn(null);
        given(customTranslation.getStringField(LOCALE)).willReturn(null);

        // when
        customTranslationModelHooksCTM.updateCustomTranslationData(customTranslationDD, customTranslation);

        // then
        verify(customTranslation).setField(PLUGIN_NAME, null);
        verify(customTranslation).setField(PROPERTIES_TRANSLATION, null);
    }

    @Test
    public void shouldUpdateCustomTranslationDataWhenDataIsntNull() {
        // given
        String pluginIdentifier = "plugin";
        String key = "key";
        String locale = "pl";

        String pluginName = "plugin";
        String propertiesTranslation = "translation";

        given(customTranslation.getStringField(PLUGIN_IDENTIFIER)).willReturn(pluginIdentifier);
        given(customTranslation.getStringField(KEY)).willReturn(key);
        given(customTranslation.getStringField(LOCALE)).willReturn(locale);

        given(pluginAccessor.getPlugin(pluginIdentifier)).willReturn(plugin);
        given(plugin.getPluginInformation()).willReturn(pluginInformation);
        given(pluginInformation.getName()).willReturn(pluginName);

        given(translationService.translate(key, new Locale(locale))).willReturn(propertiesTranslation);

        // when
        customTranslationModelHooksCTM.updateCustomTranslationData(customTranslationDD, customTranslation);

        // then
        verify(customTranslation).setField(PLUGIN_NAME, pluginName);
        verify(customTranslation).setField(PROPERTIES_TRANSLATION, propertiesTranslation);
    }

}