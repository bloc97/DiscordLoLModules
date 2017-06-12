package lolbot.commands;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import container.UserCommand;
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
public class SummonerActivity extends LoLCommand {
    
    QueueConstants qConstants = new QueueConstants();
    
    public SummonerActivity() {
        super(LoLCommandType.SEARCHSUMMONERNAME, "summoneractivity", "summonergame", "summonercurrent", "spectate", "sa", "sg", "sc");
    }
    @Override
    public boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c, CachedRiotApi api) {
        String nameSearch = c.getTokensString();
        Summoner summoner = api.Summoner.getSummonerByName(nameSearch);
        
        CurrentGameInfo cgi = api.Spectator.getActiveGameBySummoner(summoner.getId());
        
        if (cgi == null) {
            e.getChannel().sendMessage(summoner.getName() + " is currently " + "*Not in Game*", null);
            return false;
        }
        
        EmbedObject embed = new EmbedObject();
        int queueType = cgi.getGameQueueConfigId();
        embed.title = qConstants.titleDictionary.get(queueType);
        embed.description = qConstants.descDictionary.get(queueType);

        LinkedList<EmbedFieldObject> fieldList = new LinkedList<>();

        fieldList.add(new EmbedFieldObject("Team 1: ", getCurrentParticipant(cgi, 100, api), true));
        fieldList.add(new EmbedFieldObject("Team 2: ", getCurrentParticipant(cgi, 200, api), true));

        embed.fields = fieldList.toArray(new EmbedFieldObject[0]);

        String timeInString = "";

        long runningTimeInSecs = cgi.getGameLength();

        long minutes = TimeUnit.SECONDS.toMinutes(runningTimeInSecs);

        if (minutes < 1) {
            //timeInString = (runningTimeInSecs > 1) ? runningTimeInSecs + " Seconds" : runningTimeInSecs + " Second";
            timeInString = (runningTimeInSecs > 1) ? runningTimeInSecs + " Seconds" : "Champion selection";
        } else {
            timeInString = (minutes > 1) ? minutes + " Minutes" : minutes + " Minute";
        }

        embed.footer = new FooterObject("Current Duration: " + timeInString, "", "");

        e.getChannel().sendMessage(summoner.getName() + " is currently " + "**IN GAME**", embed);
        return true;
    }
    private String getCurrentParticipant(CurrentGameInfo cgi, int teamId, CachedRiotApi api) {
        String teamParticipants = "";
        for (CurrentGameParticipant cgp : cgi.getParticipants()) {
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
