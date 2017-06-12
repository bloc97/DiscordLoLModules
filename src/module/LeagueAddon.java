/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module;

import addon.Addon;
import container.UserCommand;
import java.util.Arrays;
import java.util.List;
import net.bloc97.riot.cache.CachedRiotApi;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public abstract class LeagueAddon implements Addon {
    
    public enum LeagueAddonType {
        NULL, INFO, SEARCHSUMMONERNAME, SEARCHCHAMPIONNAME, SEARCHITEMNAME, SEARCHSUMMONERNAMEWITHPAGE
    }
    public final List<String> triggerVerbs;
    public final LeagueAddonType type;
    public LeagueAddon(LeagueAddonType type, String... triggerVerbs) {
        this.triggerVerbs = Arrays.asList(triggerVerbs);
        this.type = type;
    }
    public LeagueAddonType getType() {
        return type;
    }
    @Override
    public boolean isTrigger(IDiscordClient client, Event e) {
        throw new IllegalStateException("Cannot parse command without RiotApi object.");
    }
    @Override
    public final boolean trigger(IDiscordClient client, Event e) {
        throw new IllegalStateException("Cannot parse command without RiotApi object.");
    }
    public boolean isTrigger(String verb) {
        return (triggerVerbs.contains(verb));
    }
    public abstract boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c, CachedRiotApi db);
}
