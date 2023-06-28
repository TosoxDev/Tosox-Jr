package de.tosoxdev.tosoxjr.commands;

import de.tosoxdev.tosoxjr.GenericManagerBase;
import de.tosoxdev.tosoxjr.commands.cat.CatCmd;
import de.tosoxdev.tosoxjr.commands.csstats.CSStatsCmd;
import de.tosoxdev.tosoxjr.commands.help.HelpCmd;
import de.tosoxdev.tosoxjr.commands.list.ListCmd;
import de.tosoxdev.tosoxjr.commands.ping.PingCmd;
import de.tosoxdev.tosoxjr.commands.quote.QuoteCmd;
import de.tosoxdev.tosoxjr.commands.say.SayCmd;
import de.tosoxdev.tosoxjr.utils.Constants;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandManager extends GenericManagerBase<CommandBase, MessageReceivedEvent> {
    public CommandManager() {
        addElement(new PingCmd());
        addElement(new SayCmd());
        addElement(new HelpCmd());
        addElement(new ListCmd());
        addElement(new QuoteCmd());
        addElement(new CSStatsCmd());
        addElement(new CatCmd());
    }

    @Override
    public void handle(MessageReceivedEvent event) {
        // Remove prefix, split arguments
        String[] split = event.getMessage().getContentDisplay().substring(Constants.BOT_PREFIX.length()).split(" ");
        String command = split[0].toLowerCase();
        CommandBase cmd = getElement(command);

        event.getChannel().sendTyping().queue();

        if (cmd == null) {
            event.getChannel().sendMessage("Seems like I don't know this command").queue();
            return;
        }

        cmd.handle(event);
    }
}
