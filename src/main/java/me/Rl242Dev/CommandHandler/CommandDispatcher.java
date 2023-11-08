package me.Rl242Dev.CommandHandler;

import me.Rl242Dev.MineMate;
import me.Rl242Dev.Utils;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.Classes.Utils.CustomEmbedBuilder;
import me.Rl242Dev.CommandHandler.Discord.CaseHandler;
import me.Rl242Dev.CommandHandler.Discord.LevelHandler;
import me.Rl242Dev.CommandHandler.Discord.StartHandler;
import me.Rl242Dev.CommandHandler.Discord.UtilsHandler;
import me.Rl242Dev.CommandHandler.Discord.Shop.BuyHandler;
import me.Rl242Dev.CommandHandler.Discord.Shop.SellHandler;
import me.Rl242Dev.CommandHandler.Discord.Shop.ShopDisplayHandler;
import me.Rl242Dev.CommandHandler.Minecraft.Actions.HarvestHandler;
import me.Rl242Dev.CommandHandler.Minecraft.Actions.MineHandler;
import me.Rl242Dev.CommandHandler.Mutliplayer.MiningContest;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandDispatcher extends ListenerAdapter {
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String message = event.getMessage().getContentRaw();
        if (event.getAuthor().equals(event.getJDA().getSelfUser())) {
            return;
        }
        if(!MineMate.getInstance().getDatabaseManager().userExist(event.getAuthor().getId())){
            event.getChannel().sendMessageEmbeds(Utils.StartErrorEmbed(event.getAuthor().getId()).build()).queue();
            return;
        }

        Player player = new Player(event.getAuthor().getId());

        if(message.startsWith(".buy")){
            if(BuyHandler.handle(event)){
                return;
            }else{
                MineMate.getLogger().appendLogger("[ERROR] BuyHandler.class returned false");
                MineMate.getLogger().send();
                return;
            }
        }
        if(message.startsWith(".sell")){
            if(SellHandler.handle(event)){
                return;
            }else{
                MineMate.getLogger().appendLogger("[ERROR] SellHandler.class returned false");
                MineMate.getLogger().send();
                return;
            }
        }
        if(message.startsWith(".shop")){
            if(ShopDisplayHandler.handle(event)){
                return;
            }else{
                MineMate.getLogger().appendLogger("[ERROR] ShopDisplayHandler.class returned false");
                MineMate.getLogger().send();
                return;                
            }
        }
        if(message.equalsIgnoreCase(".case open") && MineMate.getConfigManager().getBoolean("modules.cases")){
            if(CaseHandler.handle(event)){
                return;
            }else{
                MineMate.getLogger().appendLogger("[ERROR] CaseHandler.class returned false");
                MineMate.getLogger().send();
                return;
            }
        }
        if(message.startsWith(".clan")){
            if(message.startsWith(".clan")){ //Change to ClanHandler.handle(event) when fixed
                return;
            }else{
                MineMate.getLogger().appendLogger("[ERROR] ClanHandler.class returned false");
                MineMate.getLogger().send();
                return;
            }
        }
        if(message.startsWith(".level") || message.startsWith(".prestige")){
            if(LevelHandler.handle(event)){
                return;
            }else{
                MineMate.getLogger().appendLogger("[ERROR] LevelHandler.class returned false");
                MineMate.getLogger().send();
                return;
            }
        }
        if(message.equalsIgnoreCase(".start")){
            if(StartHandler.handle(event)){
                return;
            }else{
                MineMate.getLogger().appendLogger("[ERROR] StartHandler.class returned false");
                MineMate.getLogger().send();
                return;                
            }
        }
        if(message.equalsIgnoreCase(".bal") || message.equalsIgnoreCase(".balance") || message.equalsIgnoreCase(".help") || message.equalsIgnoreCase(".profil") || message.equalsIgnoreCase(".leaderboard")){
            if(UtilsHandler.handle(event)){
                return;
            }else{
                MineMate.getLogger().appendLogger("[ERROR] UtilsHandler.class returned false");
                MineMate.getLogger().send();
                return;                     
            }
        }
        if(message.equalsIgnoreCase(".miningContest") && MineMate.getConfigManager().getBoolean("modules.miningContest")){
            if(MiningContest.handle(event)){
                return;
            }else{
                MineMate.getLogger().appendLogger("[ERROR] MiningContest.class returned false");
                MineMate.getLogger().send();
                return;    
            }
        }
        if(message.equalsIgnoreCase(".harvest") || message.equalsIgnoreCase(".h")){
            if(HarvestHandler.handle(event)){
                return;
            }else{
                MineMate.getLogger().appendLogger("[ERROR] HarvestHandler.class returned false");
                MineMate.getLogger().send();
                return;    
            }
        }
        if(message.equalsIgnoreCase(".mine") || message.equalsIgnoreCase(".m")){
            if(MineHandler.handle(event)){
                return;
            }else{
                MineMate.getLogger().appendLogger("[ERROR] MineHandler.class returned false");
                MineMate.getLogger().send();
                return;                   
            }
        }
        if(message.startsWith(".")){
            CustomEmbedBuilder embed = new CustomEmbedBuilder("🔧 Command Action", player, "This command is not handled");
            event.getChannel().sendMessageEmbeds(embed.toEmbed().build()).queue();
            return;
        }
    }
}
