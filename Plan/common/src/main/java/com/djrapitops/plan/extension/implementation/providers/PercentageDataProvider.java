/*
 *  This file is part of Player Analytics (Plan).
 *
 *  Plan is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License v3 as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Plan is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Plan. If not, see <https://www.gnu.org/licenses/>.
 */
package com.djrapitops.plan.extension.implementation.providers;

import com.djrapitops.plan.extension.annotation.Conditional;
import com.djrapitops.plan.extension.annotation.PercentageProvider;
import com.djrapitops.plan.extension.icon.Icon;
import com.djrapitops.plan.extension.implementation.ProviderInformation;

import java.lang.reflect.Method;

/**
 * Represents a DataExtension API method annotated with {@link PercentageProvider} annotation.
 * <p>
 * Used to obtain data to place in the database.
 *
 * @author Rsl1122
 */
public class PercentageDataProvider extends DataProvider<Double> {

    private PercentageDataProvider(ProviderInformation providerInformation, MethodWrapper<Double> methodWrapper) {
        super(providerInformation, methodWrapper);
    }

    public static void placeToDataProviders(
            DataProviders dataProviders, Method method, PercentageProvider annotation,
            Conditional condition, String tab, String pluginName
    ) {
        MethodWrapper<Double> methodWrapper = new MethodWrapper<>(method, Double.class);
        Icon providerIcon = new Icon(annotation.iconFamily(), annotation.iconName(), annotation.iconColor());

        ProviderInformation providerInformation = new ProviderInformation(
                pluginName, method.getName(), annotation.text(), annotation.description(), providerIcon, annotation.priority(), tab, condition
        );

        dataProviders.put(new PercentageDataProvider(providerInformation, methodWrapper));
    }
}