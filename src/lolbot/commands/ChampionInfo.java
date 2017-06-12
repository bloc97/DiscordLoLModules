package lolbot.commands;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import container.UserCommand;
import helpers.TextFormatter;
import java.util.LinkedList;
import lolbot.LoLCommand;
import net.bloc97.riot.cache.CachedRiotApi;
import net.rithms.riot.api.endpoints.static_data.dto.Champion;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.api.internal.json.objects.EmbedObject.EmbedFieldObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class ChampionInfo extends LoLCommand {
    public ChampionInfo() {
        super(LoLCommandType.SEARCHCHAMPIONNAME, "championinfo", "ci");
    }
    @Override
    public boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c, CachedRiotApi api) {
        String nameSearch = c.getTokensString();
        Champion champion = api.StaticData.searchDataChampion(nameSearch);
        
        if (champion == null) {
            champion = api.StaticData.searchDataChampionClosest(nameSearch);
            e.getMessage().getChannel().sendMessage("Could not find champion, did you mean: " + champion.getName() + "?");
        }
        
        EmbedObject embed = new EmbedObject();
        embed.color = 6732543;
        
        String championPicUrl = "http://ddragon.leagueoflegends.com/cdn/" + api.StaticData.getDataLatestVersion() + "/img/champion/" + champion.getImage().getFull();
        String championUrl = "http://gameinfo.na.leagueoflegends.com/en/game-info/champions/" + champion.getKey().toLowerCase();
        String championWikiUrl = "http://leagueoflegends.wikia.com/wiki/" + champion.getName();
        embed.author = new EmbedObject.AuthorObject(champion.getName(), championUrl, "", "");
        embed.thumbnail = new EmbedObject.ThumbnailObject(championPicUrl, null, champion.getImage().getH(), champion.getImage().getW());
        embed.description = champion.getTitle();
        embed.footer = new EmbedObject.FooterObject("ID: " + champion.getId(), null, null);
        
        LinkedList<EmbedFieldObject> fieldList = new LinkedList<>();
        
        fieldList.add(new EmbedFieldObject("\uFEFF", getChampionInfoBars(champion), true));
        
        embed.fields = fieldList.toArray(new EmbedFieldObject[0]);
        
        //embed.url = championWikiUrl;
        
        /*
        ChampionList championList = db.getDataChampions();
        String finalString = "";
        for (Map.Entry<String, Champion> championEntry : championList.getData().entrySet()) {
            finalString += championEntry.getValue().getName() + " " + championEntry.getKey() + " ";
        }*/
        e.getMessage().getChannel().sendMessage(embed);
        return true;
    }
    private String getChampionInfoBars(Champion champion) {
        String finalString = "";
        finalString += getBar(champion.getInfo().getAttack()) + " **Attack**\n";
        finalString += getBar(champion.getInfo().getDefense()) + " **Defence**\n";
        finalString += getBar(champion.getInfo().getMagic()) + " **Magic**\n";
        finalString += getBar(champion.getInfo().getDifficulty()) + " **Difficulty**\n";
        return finalString;
    }
    private String getBar(int n) {
        char lightShade = '\u2591';
        char darkShade = '\u2593';
        
        String finalString = "";
        for (int i=0; i<10; i++) {
            if (i < n) {
                finalString += darkShade;
            } else {
                finalString += lightShade;
            }
        }
        return finalString;
    }
    
}
