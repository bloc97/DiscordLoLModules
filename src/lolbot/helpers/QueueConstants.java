/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot.helpers;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 * @author bowen
 */
public class QueueConstants {
    
        public final Dictionary<Integer, String> fullDictionary = new Hashtable<>();
        public final Dictionary<Integer, String> titleDictionary = new Hashtable<>();
        public final Dictionary<Integer, String> descDictionary = new Hashtable<>();
        
        public QueueConstants() {
            putFullDictionary();
            putTitleDictionary();
            putDescDictionary();
        }
        
        public boolean isRanked(int queueId) {
            switch (queueId) {
                case 4:
                case 6:
                case 9:
                case 41:
                case 42:
                case 410:
                case 420:
                case 440:
                    return true;
                default:
                    return false;
            }
        }
        
        private void putDescDictionary() {
            descDictionary.put(0, "");
            descDictionary.put(8, "");
            descDictionary.put(2, "Blind Pick");
            descDictionary.put(14, "Draft Pick");
            descDictionary.put(4, "Solo");

            descDictionary.put(6, "Premade");//Deprecated

            descDictionary.put(9, "Twisted Treeline");

            descDictionary.put(41, "Team"); //Deprecated
            descDictionary.put(42, "Team");
            descDictionary.put(16, "Blind Pick");
            descDictionary.put(17, "Draft Pick");

            descDictionary.put(7, "Summoner's Rift"); //Deprecated
            descDictionary.put(25, "Dominion");
            descDictionary.put(31, "Summoner's Rift");
            descDictionary.put(32, "Summoner's Rift");
            descDictionary.put(33, "Summoner's Rift");

            descDictionary.put(52, "Twisted Treeline");
            descDictionary.put(61, "");
            descDictionary.put(65, "");
            descDictionary.put(70, "");
            descDictionary.put(72, "");
            descDictionary.put(73, "");
            descDictionary.put(75, "Summoner's Rift");
            descDictionary.put(76, "");
            descDictionary.put(78, "Mirror");


            descDictionary.put(83, "Ultra Rapid Fire");
            descDictionary.put(91, "Rank 1");
            descDictionary.put(92, "Rank 2");
            descDictionary.put(93, "Rank 5");
            descDictionary.put(96, "");
            descDictionary.put(98, "Twisted Treeline");
            descDictionary.put(100, "");

            descDictionary.put(300, "");
            descDictionary.put(310, "");
            descDictionary.put(313, "");
            descDictionary.put(315, "");
            descDictionary.put(317, "");
            descDictionary.put(318, "Ultra Rapid Fire");
            descDictionary.put(325, "Summoner's Rift");
            descDictionary.put(400, "Draft Pick");
            descDictionary.put(410, "Draft Pick"); //Deprecated
            descDictionary.put(420, "Solo");
            descDictionary.put(440, "Summoner's Rift");
            descDictionary.put(600, "");
            descDictionary.put(610, "");
        }
        
        private void putTitleDictionary() {
            titleDictionary.put(0, "Custom");
            titleDictionary.put(8, "Normal 3v3");
            titleDictionary.put(2, "Normal 5v5");
            titleDictionary.put(14, "Normal 5v5");
            titleDictionary.put(4, "Ranked 5v5");

            titleDictionary.put(6, "Ranked 5v5");//Deprecated

            titleDictionary.put(9, "Ranked Flex");

            titleDictionary.put(41, "Ranked 3v3"); //Deprecated
            titleDictionary.put(42, "Ranked 5v5");
            titleDictionary.put(16, "Dominion 5v5");
            titleDictionary.put(17, "Dominion 5v5");

            titleDictionary.put(7, "Coop"); //Deprecated
            titleDictionary.put(25, "Coop");
            titleDictionary.put(31, "Coop (Intro)");
            titleDictionary.put(32, "Coop (Beginner)");
            titleDictionary.put(33, "Coop (Intermediate)");

            titleDictionary.put(52, "Coop");
            titleDictionary.put(61, "Team Builder");
            titleDictionary.put(65, "ARAM");
            titleDictionary.put(70, "One for All");
            titleDictionary.put(72, "Showdown 1v1");
            titleDictionary.put(73, "Showdown 2v2");
            titleDictionary.put(75, "Hexakill 6v6");
            titleDictionary.put(76, "Ultra Rapid Fire");
            titleDictionary.put(78, "One for All");


            titleDictionary.put(83, "Coop");
            titleDictionary.put(91, "Doom Bots");
            titleDictionary.put(92, "Doom Bots");
            titleDictionary.put(93, "Doom Bots");
            titleDictionary.put(96, "Ascension");
            titleDictionary.put(98, "Hexakill 6v6");
            titleDictionary.put(100, "Butcher's Bridge");

            titleDictionary.put(300, "King Poro");
            titleDictionary.put(310, "Nemesis");
            titleDictionary.put(313, "Black Market Brawlers");
            titleDictionary.put(315, "Nexus Siege");
            titleDictionary.put(317, "Definitely not Dominion");
            titleDictionary.put(318, "Random");
            titleDictionary.put(325, "Random");
            titleDictionary.put(400, "Normal 5v5");
            titleDictionary.put(410, "Ranked 5v5"); //Deprecated
            titleDictionary.put(420, "Ranked Team Builder");
            titleDictionary.put(440, "Ranked Flex");
            titleDictionary.put(600, "Blood Hunt Assassin");
            titleDictionary.put(610, "Darkstar");
        }
        
        
        private void putFullDictionary() {
            fullDictionary.put(0, "Custom");
            fullDictionary.put(8, "Normal 3v3");
            fullDictionary.put(2, "Normal 5v5 (Blind)");
            fullDictionary.put(14, "Normal 5v5 (Draft)");
            fullDictionary.put(4, "Ranked 5v5 (Solo)");

            fullDictionary.put(6, "Ranked 5v5 (Premade)");//Deprecated

            fullDictionary.put(9, "Ranked Flex Treeline");

            fullDictionary.put(41, "Ranked 3v3 (Team)"); //Deprecated
            fullDictionary.put(42, "Ranked 5v5 (Team)");
            fullDictionary.put(16, "Dominion 5v5 (Blind)");
            fullDictionary.put(17, "Dominion 5v5 (Draft)");

            fullDictionary.put(7, "Bot 5v5"); //Deprecated
            fullDictionary.put(25, "Dominion Coop");
            fullDictionary.put(31, "Rift Coop (Intro)");
            fullDictionary.put(32, "Rift Coop (Beginner)");
            fullDictionary.put(33, "Rift Coop (Intermediate)");

            fullDictionary.put(52, "Treeline Coop");
            fullDictionary.put(61, "Team Builder");
            fullDictionary.put(65, "ARAM");
            fullDictionary.put(70, "One for All");
            fullDictionary.put(72, "Showdown 1v1");
            fullDictionary.put(73, "Showdown 2v2");
            fullDictionary.put(75, "Rift 6v6");
            fullDictionary.put(76, "Ultra Rapid Fire");
            fullDictionary.put(78, "One for All (Mirror)");


            fullDictionary.put(83, "Ultra Rapid Fire Coop");
            fullDictionary.put(91, "Doom Bots 1");
            fullDictionary.put(92, "Doom Bots 2");
            fullDictionary.put(93, "Doom Bots 5");
            fullDictionary.put(96, "Ascension");
            fullDictionary.put(98, "Treeline 6v6");
            fullDictionary.put(100, "Butcher's Bridge");

            fullDictionary.put(300, "King Poro");
            fullDictionary.put(310, "Nemesis");
            fullDictionary.put(313, "Black Market Brawlers");
            fullDictionary.put(315, "Nexus Siege");
            fullDictionary.put(317, "Definitely not Dominion");
            fullDictionary.put(318, "Random URF");
            fullDictionary.put(325, "Random Rift");
            fullDictionary.put(400, "Normal 5v5 (Draft)");
            fullDictionary.put(410, "Ranked 5v5 (Draft)"); //Deprecated
            fullDictionary.put(420, "Ranked Solo (Team)");
            fullDictionary.put(440, "Ranked Flex Rift");
            fullDictionary.put(600, "Blood Hunt Assassin");
            fullDictionary.put(610, "Darkstar");
        }
        
        
}
