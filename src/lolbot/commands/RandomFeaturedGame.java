package lolbot.commands;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import container.UserCommand;
import helpers.Random;
import helpers.TextFormatter;
import static helpers.TextFormatter.formatCapitalUnderscore;
import static helpers.TextFormatter.formatNounOutput;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lolbot.LoLCommand;
import lolbot.helpers.QueueConstants;
import net.bloc97.riot.cache.CachedRiotApi;
import net.bloc97.riot.cache.database.ChampionMasteryDatabase;
import net.rithms.riot.api.endpoints.champion_mastery.dto.ChampionMastery;
import net.rithms.riot.api.endpoints.league.dto.LeaguePosition;
import net.rithms.riot.api.endpoints.spectator.dto.CurrentGameInfo;
import net.rithms.riot.api.endpoints.spectator.dto.CurrentGameParticipant;
import net.rithms.riot.api.endpoints.spectator.dto.FeaturedGameInfo;
import net.rithms.riot.api.endpoints.spectator.dto.Participant;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.api.internal.json.objects.EmbedObject.EmbedFieldObject;
import sx.blah.discord.api.internal.json.objects.EmbedObject.FooterObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class RandomFeaturedGame extends LoLCommand {
    
    QueueConstants qConstants = new QueueConstants();
    
    public RandomFeaturedGame() {
        super(LoLCommandType.INFO, "randomfeaturedgame", "randomgame", "featuredgame", "rfg", "rg", "fg");
    }
    @Override
    public boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c, CachedRiotApi api) {
        
        List<FeaturedGameInfo> featuredGames = api.Spectator.getFeaturedGames().getGameList();
        
        FeaturedGameInfo fgi = featuredGames.get(Random.randomListIndex(featuredGames));
        
        EmbedObject embed = new EmbedObject();
        int queueType = fgi.getGameQueueConfigId();
        embed.title = qConstants.titleDictionary.get(queueType);
        embed.description = qConstants.descDictionary.get(queueType);
        
        LinkedList<EmbedFieldObject> fieldList = new LinkedList<>();
        
        fieldList.add(new EmbedFieldObject("Team 1: ", getCurrentParticipant(fgi, 100, api), true));
        fieldList.add(new EmbedFieldObject("Team 2: ", getCurrentParticipant(fgi, 200, api), true));
        
        embed.fields = fieldList.toArray(new EmbedFieldObject[0]);
        
        String timeInString = "";
        
        long runningTimeInSecs = fgi.getGameLength();
        
        long minutes = TimeUnit.SECONDS.toMinutes(runningTimeInSecs);
        
        if (minutes < 1) {
            //timeInString = (runningTimeInSecs > 1) ? runningTimeInSecs + " Seconds" : runningTimeInSecs + " Second";
            timeInString = (runningTimeInSecs > 1) ? runningTimeInSecs + " Seconds" : "Champion selection";
        } else {
            timeInString = (minutes > 1) ? minutes + " Minutes" : minutes + " Minute";
        }
        
        embed.footer = new FooterObject("Current Duration: " + timeInString, "", "");
        
        e.getChannel().sendMessage(embed);
        return true;
    }
    private String getCurrentParticipant(FeaturedGameInfo fgi, int teamId, CachedRiotApi api) {
        String teamParticipants = "";
        for (Participant cgp : fgi.getParticipants()) {
            if (cgp.getTeamId()== teamId) {
                teamParticipants += api.StaticData.getDataChampion(cgp.getChampionId()).getName() + " (" + cgp.getSummonerName() + ")\n";
            }
        }
        if (teamParticipants.length() < 1) {
            return "None";
        }
        return teamParticipants;
    }
}
