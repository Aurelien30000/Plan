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
package com.djrapitops.plan.extension.implementation.results;

import com.djrapitops.plan.api.PlanAPI;
import com.djrapitops.plan.utilities.html.Html;

/**
 * Represents double data returned by a DoubleProvider or PercentageProvider method.
 *
 * @author Rsl1122
 */
public class ExtensionStringData implements ExtensionData {

    private final ExtensionDescriptive descriptive;
    private final boolean playerName;
    private final String value;

    public ExtensionStringData(ExtensionDescriptive descriptive, boolean playerName, String value) {
        this.descriptive = descriptive;
        this.playerName = playerName;
        this.value = value;
    }

    public ExtensionDescriptive getDescriptive() {
        return descriptive;
    }

    public String getFormattedValue() {
        String withColors = Html.swapColorCodesToSpan(value);
        return !playerName ? withColors : Html.LINK.parse(PlanAPI.getInstance().getPlayerInspectPageLink(value), withColors);
    }
}