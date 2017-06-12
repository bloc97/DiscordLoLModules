package lolbot.commands;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import container.UserCommand;
import helpers.MapSort;
import helpers.TextFormatter;
import static helpers.TextFormatter.formatCapitalUnderscore;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lolbot.LoLCommand;
import net.bloc97.riot.cache.CachedRiotApi;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.api.internal.json.objects.EmbedObject.EmbedFieldObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class SummonerExtendedInfo extends LoLCommand {
    public SummonerExtendedInfo() {
        super(LoLCommandType.SEARCHSUMMONERNAME, "summonerextendedinfo", "sei");
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

        LinkedList<EmbedFieldObject> fieldList = new LinkedList<>();

        MatchList rml = api.Match.getRecentMatchListByAccountId(summoner.getAccountId());
        MatchList ml = api.Match.getRankedMatchListByAccountId(summoner.getAccountId());
        List<MatchReference> rmr = rml.getMatches();
        List<MatchReference> mr = ml.getMatches();

        fieldList.add(new EmbedFieldObject("Recent Champions: ", getMostUsedChampions(api, rmr, 4), true));
        fieldList.add(new EmbedFieldObject("(Ranked): ", getMostUsedChampions(api, mr, 4), true));
        fieldList.add(new EmbedFieldObject("Recent Roles: ", getPreferredRole(rmr, 2), true));
        fieldList.add(new EmbedFieldObject("(Ranked): ", getPreferredRole(mr, 2), true));
        fieldList.add(new EmbedFieldObject("Recent Lanes: ", getPreferredLane(rmr, 2), true));
        fieldList.add(new EmbedFieldObject("(Ranked): ", getPreferredLane(mr, 2), true));

        embed.fields = fieldList.toArray(new EmbedFieldObject[0]);
        e.getMessage().getChannel().sendMessage(embed);
        return true;
    }
    public String getMostUsedChampions(CachedRiotApi api, List<MatchReference> mr, int n) {
        
        double mrLength = mr.size();
        Map<Long, Integer> championCount = new HashMap<>();

        for (MatchReference m : mr) {
            long champion = m.getChampion();
            if (championCount.containsKey(champion)) {
                championCount.put(champion, championCount.get(champion)+1);
            } else {
                championCount.put(champion, 1);
            }
        }
        championCount = MapSort.sortByValueDescending(championCount);
        Map.Entry<Long, Integer>[] championCountArray = championCount.entrySet().toArray(new Map.Entry[0]);

        String mostUsedChampions = "";
        int range = Math.min(championCount.size(), n);
        for (int i=0; i<range; i++) {
            int value = championCountArray[i].getValue();
            if (value < 2) {
                continue;
            }
            mostUsedChampions = mostUsedChampions + "(" + (int)(value*100/mrLength) + "%) " + api.StaticData.getDataChampion((championCountArray[i].getKey()).intValue()).getName() + "\n";
        }
        if (mostUsedChampions.length() < 1) {
            return "None";
        }
        
        return mostUsedChampions;
    }
    public String getPreferredLane(List<MatchReference> mr, int n) {
        
        double mrLength = mr.size();
        Map<String, Integer> laneCount = new HashMap<>();

        for (MatchReference m : mr) {
            String lane = m.getLane();
            if (laneCount.containsKey(lane)) {
                laneCount.put(lane, laneCount.get(lane)+1);
            } else {
                laneCount.put(lane, 1);
            }
        }
        laneCount = MapSort.sortByValueDescending(laneCount);
        Map.Entry<String, Integer>[] laneCountArray = laneCount.entrySet().toArray(new Map.Entry[0]);

        String mostPlayedLanes = "";
        int range = Math.min(laneCount.size(), n);
        for (int i=0; i<range; i++) {
            int value = laneCountArray[i].getValue();
            if (value < 2) {
                continue;
            }
            mostPlayedLanes = mostPlayedLanes + "("+ (int)(value*100/mrLength) + "%) " + formatCapitalUnderscore(laneCountArray[i].getKey()) + "\n";
        }
        if (mostPlayedLanes.length() < 1) {
            return "None";
        }
        
        return mostPlayedLanes;
    }
    public String getPreferredRole(List<MatchReference> mr, int n) {
        
        double mrLength = mr.size();
        Map<String, Integer> roleCount = new HashMap<>();

        for (MatchReference m : mr) {
            String role = m.getRole();
            if (roleCount.containsKey(role)) {
                roleCount.put(role, roleCount.get(role)+1);
            } else {
                roleCount.put(role, 1);
            }
        }
        roleCount = MapSort.sortByValueDescending(roleCount);
        Map.Entry<String, Integer>[] roleCountArray = roleCount.entrySet().toArray(new Map.Entry[0]);

        String mostPlayedRoles = "";
        int range = Math.min(roleCount.size(), n);
        for (int i=0; i<range; i++) {
            int value = roleCountArray[i].getValue();
            if (value < 2) {
                continue;
            }
            mostPlayedRoles = mostPlayedRoles + "(" + (int)(value*100/mrLength) + "%) " + formatCapitalUnderscore(roleCountArray[i].getKey()) + "\n";
        }
        if (mostPlayedRoles.length() < 1) {
            return "None";
        }
        
        return mostPlayedRoles;
    }
    
}
