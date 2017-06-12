/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot;

import dbot.Command;
import container.UserCommand;
import java.util.Arrays;
import java.util.List;
import net.bloc97.riot.cache.CachedRiotApi;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public abstract class LoLCommand implements Command {
    public enum LoLCommandType {
        NULL, INFO, SEARCHSUMMONERNAME, SEARCHCHAMPIONNAME, SEARCHITEMNAME, SEARCHSUMMONERNAMEWITHPAGE
    }
    public final List<String> triggerVerbs;
    public final LoLCommandType type;
    public LoLCommand(LoLCommandType type, String... triggerVerbs) {
        this.triggerVerbs = Arrays.asList(triggerVerbs);
        this.type = type;
    }
    public LoLCommandType getType() {
        return type;
    }
    @Override
    public boolean isTrigger(String verb) {
        return (triggerVerbs.contains(verb));
    }
    @Override
    public final boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c) {
        throw new IllegalStateException("Cannot parse command without RiotApi object.");
    }
    public abstract boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c, CachedRiotApi db);
}
