package main.java.com.djrapitops.plan.utilities;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import main.java.com.djrapitops.plan.Phrase;
import main.java.com.djrapitops.plan.Plan;
import main.java.com.djrapitops.plan.Settings;
import main.java.com.djrapitops.plan.data.AnalysisData;
import main.java.com.djrapitops.plan.data.UserData;
import main.java.com.djrapitops.plan.ui.Html;
import main.java.com.djrapitops.plan.ui.graphs.PlayerActivityGraphCreator;
import main.java.com.djrapitops.plan.ui.tables.SortableKillsTableCreator;
import main.java.com.djrapitops.plan.ui.tables.SortableSessionTableCreator;
import org.bukkit.GameMode;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

/**
 *
 * @author Rsl1122
 */
public class PlaceholderUtils {

    /**
     * Gets the HashMap that is used to replace placeholders in Analysis.
     *
     * @param data AnalysisData used to replace the placeholders with
     * @return HashMap that contains string for each placeholder.
     */
    public static HashMap<String, String> getAnalysisReplaceRules(AnalysisData data) {
        HashMap<String, String> replaceMap = new HashMap<>();
        replaceMap.put("%gm0%", (int) (data.getGm0Perc() * 100) + "%");
        replaceMap.put("%gm1%", (int) (data.getGm1Perc() * 100) + "%");
        replaceMap.put("%gm2%", (int) (data.getGm2Perc() * 100) + "%");
        replaceMap.put("%gm3%", (int) (data.getGm3Perc() * 100) + "%");
        replaceMap.put("%active%", "" + data.getActive());
        replaceMap.put("%banned%", "" + data.getBanned());
        replaceMap.put("%inactive%", "" + data.getInactive());
        replaceMap.put("%joinleaver%", "" + data.getJoinleaver());
        replaceMap.put("%activitytotal%", "" + data.getTotal());
        replaceMap.put("%npday%", data.getNewPlayersDay() + "");
        replaceMap.put("%npweek%", data.getNewPlayersWeek() + "");
        replaceMap.put("%npmonth%", data.getNewPlayersMonth() + "");
        replaceMap.put("%commanduse%", HtmlUtils.removeXSS(data.getCommandUseListHtml()));
        replaceMap.put("%totalcommands%", data.getTotalCommands() + "");
        replaceMap.put("%avgage%", (data.getAverageAge() != -1) ? "" + data.getAverageAge() : Phrase.DEM_UNKNOWN + "");
        replaceMap.put("%avgplaytime%", FormatUtils.formatTimeAmount("" + data.getAveragePlayTime()));
        replaceMap.put("%totalplaytime%", FormatUtils.formatTimeAmount("" + data.getTotalPlayTime()));
        replaceMap.put("%ops%", "" + data.getOps());
        replaceMap.put("%refresh%", FormatUtils.formatTimeAmountSinceString("" + data.getRefreshDate(), new Date()));
        replaceMap.put("%totallogins%", "" + data.getTotalLoginTimes());
        replaceMap.put("%top20mostactive%", data.getTop20ActivePlayers());
        replaceMap.put("%recentlogins%", data.getRecentPlayers());
        replaceMap.put("%deaths%", data.getTotaldeaths() + "");
        replaceMap.put("%playerkills%", data.getTotalkills() + "");
        replaceMap.put("%mobkills%", data.getTotalmobkills() + "");
        Plan plugin = getPlugin(Plan.class);
        replaceMap.put("%version%", plugin.getDescription().getVersion());
        replaceMap.put("%planlite%", "");
        replaceMap.put("%sortabletable%", data.getSortablePlayersTable());
        replaceMap.put("%dataday%", data.getPlayersDataArray()[0]);
        replaceMap.put("%labelsday%", data.getPlayersDataArray()[1]);
        replaceMap.put("%dataweek%", data.getPlayersDataArray()[2]);
        replaceMap.put("%labelsweek%", data.getPlayersDataArray()[3]);
        replaceMap.put("%datamonth%", data.getPlayersDataArray()[4]);
        replaceMap.put("%labelsmonth%", data.getPlayersDataArray()[5]);
        replaceMap.put("%playersgraphcolor%", Settings.HCOLOR_ACT_ONL + "");
        replaceMap.put("%playersgraphfill%", Settings.HCOLOR_ACT_ONL_FILL + "");
        String[] activityLabels = new String[]{
            "\"" + Html.GRAPH_ACTIVE.parse() + "\"",
            "\"" + Html.GRAPH_INACTIVE.parse() + "\"",
            "\"" + Html.GRAPH_UNKNOWN.parse() + "\"",
            "\"" + Html.GRAPH_BANNED.parse() + "\""
        };
        replaceMap.put("%labelsactivity%", Arrays.toString(activityLabels));
        String[] activityData = new String[]{data.getActive() + "", data.getInactive() + "", data.getJoinleaver() + "", data.getBanned() + ""};
        replaceMap.put("%activitydata%", Arrays.toString(activityData));
        replaceMap.put("%activitycolors%", "\"#" + Settings.HCOLOR_ACTP_ACT
                + "\",\"#" + Settings.HCOLOR_ACTP_INA + "\",\"#" + Settings.HCOLOR_ACTP_JON + "\",\"#" + Settings.HCOLOR_ACTP_BAN + "\"");
        replaceMap.put("%activecol%", Settings.HCOLOR_ACTP_ACT + "");
        replaceMap.put("%inactivecol%", Settings.HCOLOR_ACTP_INA + "");
        replaceMap.put("%joinleavecol%", Settings.HCOLOR_ACTP_JON + "");
        replaceMap.put("%bancol%", Settings.HCOLOR_ACTP_BAN + "");
        replaceMap.put("%gm0col%", Settings.HCOLOR_GMP_0 + "");
        replaceMap.put("%gm1col%", Settings.HCOLOR_GMP_1 + "");
        replaceMap.put("%gm2col%", Settings.HCOLOR_GMP_2 + "");
        replaceMap.put("%gm3col%", Settings.HCOLOR_GMP_3 + "");
        String[] gmData = new String[]{
            (data.getGm0Perc() * 100) + "",
            (data.getGm1Perc() * 100) + "",
            (data.getGm2Perc() * 100) + "",
            (data.getGm3Perc() * 100) + ""
        };
        replaceMap.put("%gmdata%", Arrays.toString(gmData));
        replaceMap.put("%gmlabels%", "[\"Survival\", \"Creative\", \"Adventure\", \"Spectator\"]");
        replaceMap.put("%gmcolors%", "\"#" + Settings.HCOLOR_GMP_0 + "\",\"#" + Settings.HCOLOR_GMP_1
                + "\",\"#" + Settings.HCOLOR_GMP_2 + "\",\"#" + Settings.HCOLOR_GMP_3 + "\"");
        replaceMap.put("%genderdata%", Arrays.toString(data.getGenderData()));
        replaceMap.put("%gendermale%", data.getGenderData()[0]+"");
        replaceMap.put("%genderfemale%", data.getGenderData()[1]+"");
        replaceMap.put("%genderlabels%", "[\"Male\", \"Female\", \"Unknown\"]");
        replaceMap.put("%gendercolors%", "\"#" + Settings.HCOLOR_GENP_M + "\",\"#" + Settings.HCOLOR_GENP_F
                + "\",\"#" + Settings.HCOLOR_GENP_U + "\"");
        replaceMap.put("%genderfcolor%", "#" +Settings.HCOLOR_GENP_F);
        replaceMap.put("%gendermcolor%", "#" +Settings.HCOLOR_GENP_M);
        replaceMap.putAll(plugin.getHookHandler().getAdditionalAnalysisReplaceRules());
        replaceMap.put("%sessionaverage%", FormatUtils.formatTimeAmount(data.getSessionAverage() + ""));
        replaceMap.put("%geomapcountries%", data.getGeomapCountries());
        replaceMap.put("%geomapz%", data.getGeomapZ());
        replaceMap.put("%geomapcodes%", data.getGeomapCodes());
        return replaceMap;
    }

    /**
     * Gets the HashMap that is used to replace placeholders.
     *
     * @param data UserData used to replace the placeholders with
     * @return HashMap that contains string for each placeholder.
     * @throws java.io.FileNotFoundException if planliteplayer.html is not found
     */
    public static HashMap<String, String> getInspectReplaceRules(UserData data) throws FileNotFoundException {
        HashMap<String, String> replaceMap = new HashMap<>();
        boolean showIPandUUID = Settings.SECURITY_IP_UUID.isTrue();
        UUID uuid = data.getUuid();
        replaceMap.put("%uuid%", (showIPandUUID ? "" + uuid : Html.HIDDEN.parse()));
        replaceMap.put("%lastseen%", FormatUtils.formatTimeStamp("" + data.getLastPlayed()));
        replaceMap.put("%logintimes%", "" + data.getLoginTimes());
        replaceMap.put("%geoloc%", data.getDemData().getGeoLocation());
        boolean isActive = AnalysisUtils.isActive(data.getLastPlayed(), data.getPlayTime(), data.getLoginTimes());
        replaceMap.put("%active%", isActive ? Html.ACTIVE.parse() : Html.INACTIVE.parse());
        int age = data.getDemData().getAge();
        replaceMap.put("%age%", (age != -1) ? "" + age : Phrase.DEM_UNKNOWN + "");
        replaceMap.put("%gender%", "" + data.getDemData().getGender().name().toLowerCase());
        HashMap<GameMode, Long> gmTimes = data.getGmTimes();
        long gmThree;
        try {
            Long gm3 = gmTimes.get(GameMode.SPECTATOR);
            if (gm3 == null) {
                gm3 = (long) 0;
            }
            gmThree = gm3;
        } catch (NoSuchFieldError | NullPointerException e) {
            gmThree = 0;
        }
        long[] gmData = new long[]{
            (gmTimes.get(GameMode.SURVIVAL) != null ? gmTimes.get(GameMode.SURVIVAL):0L),
            (gmTimes.get(GameMode.CREATIVE) != null ? gmTimes.get(GameMode.CREATIVE):0L),
            (gmTimes.get(GameMode.ADVENTURE) != null ? gmTimes.get(GameMode.ADVENTURE):0L),
            gmThree
        };
        long total = gmData[0] + gmData[1] + gmData[2] + gmData[3];
        replaceMap.put("%gm0%", FormatUtils.formatTimeAmount("" + gmData[0]));
        replaceMap.put("%gm1%", FormatUtils.formatTimeAmount("" + gmData[1]));
        replaceMap.put("%gm2%", FormatUtils.formatTimeAmount("" + gmData[2]));
        replaceMap.put("%gm3%", FormatUtils.formatTimeAmount("" + gmData[3]));
        replaceMap.put("%gmdata%", Arrays.toString(gmData));
        replaceMap.put("%gmlabels%", "[\"Survival\", \"Creative\", \"Adventure\", \"Spectator\"]");
        replaceMap.put("%gmcolors%", "\"#" + Settings.HCOLOR_GMP_0 + "\",\"#" + Settings.HCOLOR_GMP_1
                + "\",\"#" + Settings.HCOLOR_GMP_2 + "\",\"#" + Settings.HCOLOR_GMP_3 + "\"");
        replaceMap.put("%gmtotal%", FormatUtils.formatTimeAmount("" + total));
        replaceMap.put("%ips%", (showIPandUUID ? data.getIps().toString() : Html.HIDDEN.parse()));
        replaceMap.put("%nicknames%", HtmlUtils.removeXSS(FormatUtils.swapColorsToSpan(data.getNicknames().toString())));
        replaceMap.put("%name%", data.getName());
        replaceMap.put("%registered%", FormatUtils.formatTimeStamp("" + data.getRegistered()));
        replaceMap.put("%timeskicked%", "" + data.getTimesKicked());
        replaceMap.put("%playtime%", FormatUtils.formatTimeAmount("" + data.getPlayTime()));
        replaceMap.put("%banned%", data.isBanned() ? Html.BANNED.parse() : "");
        replaceMap.put("%op%", data.isOp() ? Html.OPERATOR.parse() : "");
        replaceMap.put("%isonline%", (data.isOnline()) ? Html.ONLINE.parse() : Html.OFFLINE.parse());
        int deaths = data.getDeaths();
        replaceMap.put("%deaths%", deaths + "");
        replaceMap.put("%playerkills%", data.getPlayerKills().size() + "");
        replaceMap.put("%mobkills%", data.getMobKills() + "");
        replaceMap.put("%sessionstable%", SortableSessionTableCreator.createSortedSessionDataTable10(data.getSessions()));
        replaceMap.put("%sessionaverage%", FormatUtils.formatTimeAmount(AnalysisUtils.average(AnalysisUtils.transformSessionDataToLengths(data.getSessions())) + ""));
        replaceMap.put("%killstable%", SortableKillsTableCreator.createSortedSessionDataTable10(data.getPlayerKills()));
        Plan plugin = getPlugin(Plan.class);
        replaceMap.put("%version%", plugin.getDescription().getVersion());
        replaceMap.put("%planlite%", "");
        String[] playersDataArray = PlayerActivityGraphCreator.generateDataArray(data.getSessions(), (long) 604800 * 1000);
        replaceMap.put("%dataweek%", playersDataArray[0].replace(plugin.getHandler().getMaxPlayers() + "]", "2]"));
        replaceMap.put("%labelsweek%", playersDataArray[1]);
        replaceMap.put("%playersgraphcolor%", Settings.HCOLOR_ACT_ONL + "");
        replaceMap.put("%playersgraphfill%", Settings.HCOLOR_ACT_ONL_FILL + "");
        replaceMap.put("%gm0col%", Settings.HCOLOR_GMP_0 + "");
        replaceMap.put("%gm1col%", Settings.HCOLOR_GMP_1 + "");
        replaceMap.put("%gm2col%", Settings.HCOLOR_GMP_2 + "");
        replaceMap.put("%gm3col%", Settings.HCOLOR_GMP_3 + "");
        replaceMap.put("%inaccuratedatawarning%", (new Date().getTime() - data.getRegistered() < 180000) ? Html.WARN_INACCURATE.parse() : "");
        replaceMap.putAll(plugin.getHookHandler().getAdditionalInspectReplaceRules(uuid));
        return replaceMap;
    }
}
