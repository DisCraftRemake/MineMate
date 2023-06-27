package me.Rl242Dev;

import me.Rl242Dev.CommandHandler.DS.LevelHandler;
import me.Rl242Dev.CommandHandler.DS.Shop.SellHandler;
import me.Rl242Dev.CommandHandler.DS.Shop.ShopDisplayHandler;
import me.Rl242Dev.CommandHandler.DS.StartHandler;
import me.Rl242Dev.CommandHandler.DS.UtilsHandler;
import me.Rl242Dev.CommandHandler.MC.Actions.HarvestHandler;
import me.Rl242Dev.CommandHandler.MC.Actions.MineHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

/*

Comments :

@A : Author
@U : Usage
@E : Explanation


 */

/*

@A = Rl242Dev
@U = Main
@E = Main code of the bot

 */

public class Main {

    public static void main(String[] args) {
        JDA bot = JDABuilder.createLight("MTEwNTg3NDEzNzcwMTk0MTMyMQ.Grf8eV.imEi-Y6MC1nDnpfEshYYXn9o-fP-AV8RkPMv_Y", GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("Getting build"))
                .build();

        bot.addEventListener(new MineHandler());
        bot.addEventListener(new StartHandler());
        bot.addEventListener(new HarvestHandler());
        bot.addEventListener(new UtilsHandler());
        bot.addEventListener(new SellHandler());
        bot.addEventListener(new ShopDisplayHandler());
        bot.addEventListener(new LevelHandler());
    }
}