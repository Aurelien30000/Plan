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
package com.djrapitops.plan.db.tasks;

import com.djrapitops.plan.api.exceptions.database.DBOpException;
import com.djrapitops.plan.db.Database;
import com.djrapitops.plan.db.access.Query;
import com.djrapitops.plan.db.access.QueryStatement;
import com.djrapitops.plan.db.access.transactions.commands.RemovePlayerTransaction;
import com.djrapitops.plan.db.access.transactions.init.RemoveDuplicateUserInfoTransaction;
import com.djrapitops.plan.db.access.transactions.init.RemoveOldSampledDataTransaction;
import com.djrapitops.plan.db.sql.tables.SessionsTable;
import com.djrapitops.plan.extension.implementation.storage.transactions.results.RemoveUnsatisfiedConditionalResultsTransaction;
import com.djrapitops.plan.system.database.DBSystem;
import com.djrapitops.plan.system.info.server.ServerInfo;
import com.djrapitops.plan.system.locale.Locale;
import com.djrapitops.plan.system.locale.lang.PluginLang;
import com.djrapitops.plan.system.settings.config.PlanConfig;
import com.djrapitops.plan.system.settings.paths.TimeSettings;
import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.console.PluginLogger;
import com.djrapitops.plugin.logging.error.ErrorHandler;
import com.djrapitops.plugin.task.AbsRunnable;
import com.google.common.annotations.VisibleForTesting;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Task for cleaning the active database.
 *
 * @author Rsl1122
 */
@Singleton
public class DBCleanTask extends AbsRunnable {

    private final Locale locale;
    private final DBSystem dbSystem;
    private final PlanConfig config;
    private final ServerInfo serverInfo;
    private final PluginLogger logger;
    private final ErrorHandler errorHandler;

    @Inject
    public DBCleanTask(
            PlanConfig config,
            Locale locale,
            DBSystem dbSystem,
            ServerInfo serverInfo,
            PluginLogger logger,
            ErrorHandler errorHandler
    ) {
        this.locale = locale;

        this.dbSystem = dbSystem;
        this.config = config;
        this.serverInfo = serverInfo;
        this.logger = logger;
        this.errorHandler = errorHandler;
    }

    @Override
    public void run() {
        Database database = dbSystem.getDatabase();
        try {
            if (database.getState() != Database.State.CLOSED) {
                database.executeTransaction(new RemoveOldSampledDataTransaction(serverInfo.getServerUUID()));
                database.executeTransaction(new RemoveDuplicateUserInfoTransaction());
                database.executeTransaction(new RemoveUnsatisfiedConditionalResultsTransaction());
                int removed = cleanOldPlayers(database);
                if (removed > 0) {
                    logger.info(locale.getString(PluginLang.DB_NOTIFY_CLEAN, removed));
                }
            }
        } catch (DBOpException e) {
            errorHandler.log(L.ERROR, this.getClass(), e);
            cancel();
        }
    }

    @VisibleForTesting
    public int cleanOldPlayers(Database database) {
        long now = System.currentTimeMillis();
        long keepActiveAfter = now - config.get(TimeSettings.KEEP_INACTIVE_PLAYERS);

        List<UUID> inactivePlayers = database.query(fetchInactivePlayerUUIDs(keepActiveAfter));
        for (UUID uuid : inactivePlayers) {
            database.executeTransaction(new RemovePlayerTransaction(uuid));
        }
        return inactivePlayers.size();
    }

    private Query<List<UUID>> fetchInactivePlayerUUIDs(long keepActiveAfter) {
        String sql = "SELECT uuid, last_seen FROM (SELECT" +
                " MAX(" + SessionsTable.SESSION_END + ") as last_seen, " + SessionsTable.USER_UUID +
                " FROM " + SessionsTable.TABLE_NAME +
                " GROUP BY " + SessionsTable.USER_UUID + ") as q1" +
                " WHERE last_seen < ?";
        return new QueryStatement<List<UUID>>(sql, 20000) {

            @Override
            public void prepare(PreparedStatement statement) throws SQLException {
                statement.setLong(1, keepActiveAfter);
            }

            @Override
            public List<UUID> processResults(ResultSet set) throws SQLException {
                List<UUID> inactiveUUIDs = new ArrayList<>();
                while (set.next()) {
                    inactiveUUIDs.add(UUID.fromString(set.getString("uuid")));
                }
                return inactiveUUIDs;
            }
        };
    }
}