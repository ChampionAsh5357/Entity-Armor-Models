package io.github.championash5357.entityarmormodels.data.client;

import io.github.championash5357.entityarmormodels.client.EntityArmorModels;
import io.github.championash5357.entityarmormodels.client.util.TranslationStrings;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class EAMLanguageProvider extends LanguageProvider {

	public EAMLanguageProvider(DataGenerator gen, String locale) {
		super(gen, EntityArmorModels.ID, locale);
	}

	@Override
	protected void addTranslations() {
		String locale = this.getName().replace("Languages: ", "");
		switch(locale) {
		case "en_us":
			add(TranslationStrings.NOTIFICATION_OUTDATED, "%s is currently outdated.");
			add(TranslationStrings.NOTIFICATION_CURRENT, "The current version is %s.");
			add(TranslationStrings.NOTIFICATION_CHANGELOG, "Changelog: ");
			break;
		default:
			break;
		}
	}

}
