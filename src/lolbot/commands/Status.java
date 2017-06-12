package lolbot.commands;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import container.UserCommand;
import helpers.TextFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import lolbot.LoLCommand;
import net.bloc97.riot.cache.CachedRiotApi;
import net.rithms.riot.api.endpoints.lol_status.dto.Service;
import net.rithms.riot.api.endpoints.lol_status.dto.ShardStatus;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.api.internal.json.objects.EmbedObject.EmbedFieldObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class Status extends LoLCommand {
    public Status() {
        super(LoLCommandType.INFO, "status", "s");
    }
    @Override
    public boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c, CachedRiotApi api) {
        EmbedObject embed = new EmbedObject();
        embed.color = 6732543;
        
        ShardStatus status = api.LolStatus.getShardData();
        
        String statusPicUrl = "https://vignette1.wikia.nocookie.net/leagueoflegends/images/1/12/League_of_Legends_Icon.png";
        String statusUrl = "http://status.leagueoflegends.com/#na";
        embed.author = new EmbedObject.AuthorObject("Service Status", statusUrl, null, null);
        embed.thumbnail = new EmbedObject.ThumbnailObject(statusPicUrl, null, 48, 48);
        embed.description = status.getName();
        embed.footer = new EmbedObject.FooterObject(status.getHostname(), null, null);
        
        LinkedList<EmbedFieldObject> fieldList = new LinkedList<>();
        List<Service> services = status.getServices();
        for (Service service : services) {
            String serviceName = (service.getName().equals("League client update")) ? "Update" : service.getName();
            boolean isOnline = (service.getStatus().equals("online"));
            
            String serviceStatus;
            if (isOnline && service.getIncidents().isEmpty()) {
                serviceStatus = ":white_check_mark:";
            } else if (isOnline) {
                int incidents = service.getIncidents().size();
                serviceStatus = ":warning: (" + incidents + ((incidents == 1) ?  " Incident)" : " Incidents)");
            } else {
                serviceStatus = service.getStatus();
            }
            
            fieldList.add(new EmbedFieldObject(serviceName, serviceStatus, true));
        }
        
        embed.fields = fieldList.toArray(new EmbedFieldObject[0]);
        
        e.getMessage().getChannel().sendMessage(embed);
        return true;
    }
    
}
