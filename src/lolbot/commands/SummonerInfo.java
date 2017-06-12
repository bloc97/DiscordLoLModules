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
import lolbot.LoLCommand;
import net.bloc97.riot.cache.CachedRiotApi;
import net.bloc97.riot.cache.database.ChampionMasteryDatabase;
import net.rithms.riot.api.endpoints.champion_mastery.dto.ChampionMastery;
import net.rithms.riot.api.endpoints.league.dto.LeaguePosition;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class SummonerInfo extends LoLCommand {
    public SummonerInfo() {
        super(LoLCommandType.SEARCHSUMMONERNAME, "summonerinfo", "summoner", "si");
    }
    @Override
    public boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c, CachedRiotApi api) {
        String nameSearch = c.getTokensString();
        Summoner summoner = api.Summoner.getSummonerByName(nameSearch);
        
        c.next();
        EmbedObject embed = new EmbedObject();
        embed.color = 6732543;
        String profileUrl = "http://matchhistory.na.leagueoflegends.com/en/#match-history/NA1/" + summoner.getAccountId();
        String profilePicUrl = "http://ddragon.leagueoflegends.com/cdn/" + api.StaticData.getDataLatestVersion() + "/img/profileicon/" + summoner.getProfileIconId() + ".png";
        embed.author = new EmbedObject.AuthorObject(summoner.getName(), profileUrl, "", "");
        embed.thumbnail = new EmbedObject.ThumbnailObject(profilePicUrl, "", 48, 48);
        embed.description = "Level " + summoner.getSummonerLevel();
        embed.footer = TextFormatter.getSummonerEmbedFooter(summoner.getId(), summoner.getAccountId(), summoner.getRevisionDate());

        LinkedList<EmbedObject.EmbedFieldObject> fieldList = new LinkedList<>();

        
        fieldList.add(new EmbedObject.EmbedFieldObject("Top Champions: ", getTopChampions(api, summoner.getId(), 4), true));
        fieldList.add(new EmbedObject.EmbedFieldObject("Ranked Stats: ", getLeagues(api, summoner.getId(), 4), true));
        //fieldList.add(new EmbedObject.EmbedFieldObject("Level: ", "" + summoner.getSummonerLevel(), true));
        
        embed.fields = fieldList.toArray(new EmbedObject.EmbedFieldObject[0]);
        e.getMessage().getChannel().sendMessage(embed);
        return true;
    }
    
    public static String getTopChampions(CachedRiotApi api, long id, int n) {
        //List<ChampionMastery> cms = api.ChampionMastery.getChampionMasteriesBySummoner(id);
        List<ChampionMastery> cms = api.ChampionMastery.getChampionMasteriesBySummoner(id);
        api.ChampionMastery.sortChampionMasteries(cms, ChampionMasteryDatabase.CompareMethod.LEVEL, false);
        if (cms == null || cms.size() < 1) {
            return "None";
        }
        String topChampions = "";
        int i = 0;
        for (ChampionMastery cm : cms) {
            if (i >= n) {
                break;
            }
            topChampions = topChampions + "**[" + cm.getChampionLevel() + "]** " + api.StaticData.getDataChampion(cm.getChampionId()).getName() + ": " + cm.getChampionPoints() + "\n";
            i++;
        }
        return topChampions;
    }
    public static String getTopChampionsAtLeastUntilLevel(CachedRiotApi api, long id, int n, int level) {
        //List<ChampionMastery> cms = api.ChampionMastery.getChampionMasteriesBySummoner(id);
        List<ChampionMastery> cms = api.ChampionMastery.getChampionMasteriesBySummoner(id);
        api.ChampionMastery.sortChampionMasteries(cms, ChampionMasteryDatabase.CompareMethod.LEVEL, false);
        if (cms == null || cms.size() < 1) {
            return "None";
        }
        String topChampions = "";
        int i = 0;
        for (ChampionMastery cm : cms) {
            if (cm.getChampionLevel() < level && i >= n) {
                break;
            }
            topChampions = topChampions + "**[" + cm.getChampionLevel() + "]** " + api.StaticData.getDataChampion(cm.getChampionId()).getName() + ": " + cm.getChampionPoints() + "\n";
            i++;
        }
        return topChampions;
    }
    public static String getLeagues(CachedRiotApi api, long id, int n) {
        Set<LeaguePosition> lps = api.League.getLeaguePositionsBySummoner(id);
        if (lps == null || lps.size() < 1) {
            return "None";
        }
        String leaguePositions = "";
        int i = 0;
        for (LeaguePosition lp : lps) {
            if (i >= n) {
                break;
            }
            leaguePositions = leaguePositions + formatCapitalUnderscore(lp.getQueueType()) + ": **" + lp.getTier() + " " + lp.getRank() + "**\n";// + " (" + lp.getLeagueName() + ")";
            i++;
        }
        return leaguePositions;
    }
}
