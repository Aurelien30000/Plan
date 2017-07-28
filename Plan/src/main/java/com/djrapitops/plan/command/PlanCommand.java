package main.java.com.djrapitops.plan.command;

import com.djrapitops.plugin.command.CommandType;
import com.djrapitops.plugin.command.TreeCommand;
import com.djrapitops.plugin.command.defaultcmds.StatusCommand;
import com.djrapitops.plugin.settings.ColorScheme;
import com.djrapitops.plugin.settings.DefaultMessages;
import main.java.com.djrapitops.plan.Permissions;
import main.java.com.djrapitops.plan.Plan;
import main.java.com.djrapitops.plan.command.commands.*;

/**
 * TreeCommand for the /plan command, and all subcommands.
 * <p>
 * Uses the Abstract Plugin Framework for easier command management.
 *
 * @author Rsl1122
 * @since 1.0.0
 */
public class PlanCommand extends TreeCommand<Plan> {

    /**
     * CommandExecutor class Constructor.
     * <p>
     * Initializes Subcommands
     *
     * @param plugin Current instance of Plan
     */
    public PlanCommand(Plan plugin) {
        super(plugin, "plan", CommandType.CONSOLE, "", "", "plan");
        super.setDefaultCommand("inspect");
        setHelp(plugin);
    }

    private void setHelp(Plan plugin) {
        ColorScheme colorScheme = plugin.getColorScheme();

        String ball = DefaultMessages.BALL.toString();

        String mCol = colorScheme.getMainColor();
        String sCol = colorScheme.getSecondaryColor();
        String tCol = colorScheme.getTertiaryColor();

        String[] help = new String[]{
                mCol + "/plan - Main Command",
                tCol + "  Used to access all SubCommands & help",
                sCol + "  /plan - List subcommands",
                sCol + "  /plan <subcommand> ? - in depth help"
        };
    }

    @Override
    public void addCommands() {
        commands.add(new InspectCommand(plugin));
        commands.add(new QuickInspectCommand(plugin));
        commands.add(new AnalyzeCommand(plugin));
        commands.add(new QuickAnalyzeCommand(plugin));
        commands.add(new SearchCommand(plugin));
        commands.add(new InfoCommand(plugin));
        commands.add(new ReloadCommand(plugin));
        commands.add(new ManageCommand(plugin));
        commands.add(new StatusCommand<>(plugin, Permissions.MANAGE.getPermission()));
        if (plugin.getUiServer().isEnabled()) {
            commands.add(new ListCommand(plugin));
            RegisterCommand registerCommand = new RegisterCommand(plugin);
            commands.add(registerCommand);
            commands.add(new WebUserCommand(plugin, registerCommand));
        }
    }
}
