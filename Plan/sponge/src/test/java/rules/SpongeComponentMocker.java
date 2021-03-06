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
package rules;

import com.djrapitops.plan.DaggerPlanSpongeComponent;
import com.djrapitops.plan.PlanPlugin;
import com.djrapitops.plan.PlanSponge;
import com.djrapitops.plan.PlanSpongeComponent;
import com.djrapitops.plan.system.PlanSystem;
import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;
import utilities.mocks.PlanSpongeMocker;

public class SpongeComponentMocker extends ExternalResource implements ComponentMocker {

    private final TemporaryFolder testFolder;

    private PlanSponge planMock;
    private PlanSpongeComponent component;

    public SpongeComponentMocker(TemporaryFolder testFolder) {
        this.testFolder = testFolder;
    }

    @Override
    protected void before() throws Throwable {
        PlanSpongeMocker mocker = PlanSpongeMocker.setUp()
                .withDataFolder(testFolder.newFolder())
                .withResourceFetchingFromJar()
                .withGame();
        planMock = mocker.getPlanMock();
        component = DaggerPlanSpongeComponent.builder().plan(planMock).build();
    }

    public PlanPlugin getPlanMock() {
        return planMock;
    }

    public PlanSystem getPlanSystem() {
        return component.system();
    }

}
