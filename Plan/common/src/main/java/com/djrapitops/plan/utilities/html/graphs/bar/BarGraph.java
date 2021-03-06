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
package com.djrapitops.plan.utilities.html.graphs.bar;

import com.djrapitops.plan.utilities.html.graphs.HighChart;
import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class BarGraph implements HighChart {

    private final List<Bar> bars;

    public BarGraph(List<Bar> bars) {
        this.bars = bars;
    }

    public String toHighChartsCategories() {
        TextStringBuilder categories = new TextStringBuilder("[");
        categories.appendWithSeparators(bars.stream().map(bar -> "'" + bar.getLabel() + "'").iterator(), ",");
        return categories.append("]").toString();
    }

    @Override
    public String toHighChartsSeries() {
        TextStringBuilder series = new TextStringBuilder("[");
        series.appendWithSeparators(bars.stream().map(Bar::getValue).iterator(), ",");
        return series.append("]").toString();
    }
}
