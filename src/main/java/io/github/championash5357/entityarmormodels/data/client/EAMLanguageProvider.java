/*
 * Entity Armor Models
 * Copyright (C) 2020 ChampionAsh5357
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation version 3.0 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
