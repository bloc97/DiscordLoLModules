
import java.util.Date;
import sx.blah.discord.api.internal.json.objects.EmbedObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bowen
 */
public abstract class Formatter {
    
    public static EmbedObject.FooterObject getSummonerEmbedFooter(long summonerId, long accountId, long lastDate) {
        return new EmbedObject.FooterObject(summonerId + " | " + accountId + "\u2003\u2003" + "Last Activity: " + new Date(lastDate).toString(), "", "");
    }
}
