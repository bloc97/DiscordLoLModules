package lolbot.commands;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import container.UserCommand;
import helpers.TextFormatter;
import lolbot.LoLCommand;
import net.bloc97.riot.cache.CachedRiotApi;
import net.rithms.riot.api.endpoints.static_data.dto.Champion;
import net.rithms.riot.api.endpoints.static_data.dto.Item;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class Help extends LoLCommand {
    public Help() {
        super(LoLCommandType.NULL, "help", "commands");
    }
    @Override
    public boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c, CachedRiotApi api) {
        String helpString = "";
        
        helpString += "```js\n";
        helpString += "Service Status.\n";
        helpString += "!lol s\n\n";
        helpString += "Summoner basic info.\n";
        helpString += "!lol si <Summoner Name>\n\n";
        helpString += "Summoner extended info.\n";
        helpString += "!lol sei <Summoner Name>\n\n";
        helpString += "Summoner match history.\n";
        helpString += "!lol mh <Summoner Name> [Page]\n\n";
        helpString += "Summoner activity, if in game, returns the current game info.\n";
        helpString += "!lol sa <Summoner Name>\n\n";
        helpString += "Gets a random featured game.\n";
        helpString += "!lol fg\n\n";
        helpString += "Champion info.\n";
        helpString += "!lol ci <Champion Name>\n\n";
        helpString += "Item info.\n";
        helpString += "!lol ii <Item Name>\n\n";
        helpString += "Note: !lol is optional when speaking in a channel with the name \"leagueoflegends\" or when PMing the bot. You can directly call the commands.\n";
        helpString += "eg. !sei test\n";
        helpString += "```";
        client.getOrCreatePMChannel(e.getAuthor()).sendMessage(helpString);
        return true;
    }
    
}
