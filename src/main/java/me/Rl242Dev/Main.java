package me.Rl242Dev;

import me.Rl242Dev.CommandHandler.DS.StartHandler;
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
        JDA bot = JDABuilder.createLight("", GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("Getting build"))
                .build();

        bot.addEventListener(new MineHandler());
        bot.addEventListener(new StartHandler());
    }
}