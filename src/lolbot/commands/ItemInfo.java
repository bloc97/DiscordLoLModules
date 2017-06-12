package lolbot.commands;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import container.UserCommand;
import lolbot.LoLCommand;
import net.bloc97.riot.cache.CachedRiotApi;
import net.rithms.riot.api.endpoints.static_data.dto.Item;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class ItemInfo extends LoLCommand {
    public ItemInfo() {
        super(LoLCommandType.SEARCHITEMNAME, "iteminfo", "ii");
    }
    @Override
    public boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c, CachedRiotApi api) {
        String nameSearch = c.getTokensString();
        Item item = api.StaticData.searchDataItemClosest(nameSearch);
        
        EmbedObject embed = new EmbedObject();
        embed.color = 6732543;
        
        String itemPicUrl = "http://ddragon.leagueoflegends.com/cdn/" + api.StaticData.getDataLatestVersion() + "/img/item/" + item.getImage().getFull();
        String itemUrl = "http://gameinfo.na.leagueoflegends.com/en/game-info/items/";
        String itemWikiUrl = "http://leagueoflegends.wikia.com/wiki/" + item.getName().replace(' ', '_');
        embed.author = new EmbedObject.AuthorObject(item.getName(), itemWikiUrl, "", "");
        embed.thumbnail = new EmbedObject.ThumbnailObject(itemPicUrl, null, item.getImage().getH(), item.getImage().getW());
        embed.description = item.getPlaintext();
        embed.footer = new EmbedObject.FooterObject("ID: " + item.getId(), null, null);
        
        e.getMessage().getChannel().sendMessage(embed);
        return true;
    }
    
}
