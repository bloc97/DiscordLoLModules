/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module;

import addon.Addon;
import dbot.Module;
import container.UserCommand;
import static helpers.TextFormatter.formatNounOutput;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lolbot.LoLBotApi;
import lolbot.LoLCommand;
import module.LeagueAddon.LeagueAddonType;
import net.bloc97.riot.cache.CachedRiotApi;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.util.RateLimitException;

/**
 *
 * @author bowen
 */
public class LeagueModule extends Module {
    private CachedRiotApi rApi;
    
    public LeagueModule(IDiscordClient botClient, Addon... addons) {
        super(botClient, addons);
        
        Path path = FileSystems.getDefault().getPath("rapi.key");
        String rApiKey = "";
        try {
            rApiKey = Files.readAllLines(path).get(0);
        } catch (IOException ex) {
            Logger.getLogger(LoLBotApi.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("No valid API key!");
        }
        
        rApi = new CachedRiotApi(rApiKey, Platform.NA);
        this.botClient = botClient;
    }
    
    
    public LeagueAddonType getType(String verb) {
        for (Addon a : addons) {
            LeagueAddon la = (LeagueAddon)a;
            if (la.isTrigger(verb)) {
                return la.getType();
            }
        }
        return LeagueAddonType.NULL;
    }
    
    @Override
    public void onEvent(Event e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onReady(ReadyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMessage(MessageReceivedEvent e, UserCommand c) {
        
        IMessage m = e.getMessage();
        String verb = c.get();
        LeagueAddonType type = getType(verb);
        if (type != LeagueAddonType.NULL) {
            m.addReaction(":hammer_and_wrench:");
        }
        
        switch (type) {
            case SEARCHSUMMONERNAME:
                String nameSearch = c.getNext();
                if (!rApi.Summoner.summonerNameExists(nameSearch)) {
                    m.reply("Sorry, Summoner *" + formatNounOutput(nameSearch) + "* cound not be found.", null);
                    return;
                }
                break;
            case NULL:
                break;
            
        }
        boolean isException = false;
        c.next();
        for (Addon addon : addons) {
            LeagueAddon laddon = (LeagueAddon)addon;
            if (laddon.isTrigger(verb)) {
                try {
                    laddon.trigger(botClient, e, c, rApi);
                } catch (Exception ex) {
                    isException = true;
                    System.out.println(ex);
                }
                break;
            }
        }
        List<IReaction> reactions = m.getReactions();
        for (IReaction reaction : reactions) {
            if (reaction.getClient().equals(botClient)) {
                try {
                    m.removeReaction(reaction);
                } catch (RateLimitException ex) {
                    try {
                        Thread.sleep(ex.getRetryDelay()+100);
                    } catch (InterruptedException iex) {
                        Logger.getLogger(LoLBotApi.class.getName()).log(Level.SEVERE, null, iex);
                    }
                    m.removeReaction(reaction);
                }
            }
        }
        if (isException) {
            m.addReaction(":x:");
        }
        
    }
    
}
