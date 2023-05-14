package me.Rl242Dev;

import me.Rl242Dev.classes.CommandHandler.MC.Mine;
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
        JDA bot = JDABuilder.createLight("MTEwNTg3NDEzNzcwMTk0MTMyMQ.GU7mHJ.rGfZPIg23IqXzFjZRpJuaZSvH5wxuMoRwvWce0", GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("Apprendre"))
                .build();

        bot.addEventListener(new Mine());
    }
}