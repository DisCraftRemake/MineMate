package me.Rl242Dev.CommandHandler.Discord.Shop;

import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.Classes.Utils.Coin;
import me.Rl242Dev.Classes.Items.Ressource.Harvest.Carrot;
import me.Rl242Dev.Classes.Items.Ressource.Harvest.Potato;
import me.Rl242Dev.Classes.Items.Ressource.Harvest.SugarCane;
import me.Rl242Dev.Classes.Items.Ressource.Harvest.Wheat;
import me.Rl242Dev.Classes.Items.Ressource.Ores.*;
import me.Rl242Dev.Classes.Items.Ressource.Resources;
import me.Rl242Dev.MineMate;
import me.Rl242Dev.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/*

@A = Rl242Dev
@U = Shop
@E = Class for the SellHandler, used when users sell resources

 */

@SuppressWarnings("ReassignedVariable")
public class SellHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User user = event.getAuthor();
        String uuid = user.getId();
        if (user.equals(event.getJDA().getSelfUser())){
            return;
        }

        Message message = event.getMessage();
        MessageChannelUnion channel = event.getChannel();

        List<String> args = Arrays.asList(message.getContentRaw().split(" "));

        Player player = new Player(uuid);

        if(args.contains(".sell")){
            if(MineMate.debug){
                MineMate.getLogger().appendLogger(player.getUuid()+" Issued .sell");
                MineMate.getLogger().send();
            }

            if(args.size() != 2){
                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("<@");
                stringBuilder.append(user.getId());
                stringBuilder.append(">");
                stringBuilder.append(" ➔ You must specify an ore or sell all : .sell [Resource] | .sell all | <#"+MineMate.getConfigManager().getString("help")+">");

                embedBuilder.setColor(Color.green);
                embedBuilder.setTitle(Coin.getEmojiID() + " Sell Action");
                embedBuilder.setDescription(stringBuilder.toString());

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
            }else{

                String ore = args.get(1).toUpperCase();

                if(args.get(1).equals("all")){
                    try {
                        Map<Resources, Integer> resources = MineMate.getInstance().getDatabaseManager().getResourcesFromUUID(uuid);

                        int GeneratedMoney = 0;

                        GeneratedMoney = GeneratedMoney + resources.get(Resources.WHEAT) * Wheat.getPrice();
                        GeneratedMoney = GeneratedMoney + resources.get(Resources.POTATO) * Potato.getPrice();
                        GeneratedMoney = GeneratedMoney + resources.get(Resources.CARROT) * Carrot.getPrice();
                        GeneratedMoney = GeneratedMoney + resources.get(Resources.SUGARCANE) * SugarCane.getPrice();

                        GeneratedMoney = GeneratedMoney + resources.get(Resources.STONE) * Stone.getPrice();
                        GeneratedMoney = GeneratedMoney + resources.get(Resources.COAL) * Coal.getPrice();
                        GeneratedMoney = GeneratedMoney + resources.get(Resources.IRON) * Iron.getPrice();
                        GeneratedMoney = GeneratedMoney + resources.get(Resources.GOLD) * Gold.getPrice();
                        GeneratedMoney = GeneratedMoney + resources.get(Resources.DIAMOND) * Diamond.getPrice();
                        GeneratedMoney = GeneratedMoney + resources.get(Resources.OBSIDIAN) * Obsidian.getPrice();

                        if(GeneratedMoney == 0){
                            EmbedBuilder embedBuilder = new EmbedBuilder();
                            StringBuilder description = new StringBuilder();

                            description.append("<@");
                            description.append(user.getId());
                            description.append(">");
                            description.append(" ➔ You had nothing in your inventory that could be sold");

                            embedBuilder.setColor(Color.green);
                            embedBuilder.setTitle(Coin.getEmojiID() + " Sell Action");
                            embedBuilder.setDescription(description.toString());

                            embedBuilder.setTimestamp(Instant.now());
                            embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                            channel.sendMessageEmbeds(embedBuilder.build()).queue();

                            return;
                        }

                        MineMate.getInstance().getDatabaseManager().addToBalanceFromUUID(uuid, GeneratedMoney);
                        MineMate.getInstance().getDatabaseManager().resetResourcesFromUUID(uuid);

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        StringBuilder description = new StringBuilder();

                        description.append("<@");
                        description.append(user.getId());
                        description.append(">");
                        description.append(" ➔ You have sold all your inventory for : ");
                        description.append(Utils.IntToString(GeneratedMoney)).append(" ");
                        description.append(Coin.getEmojiID());

                        embedBuilder.setColor(Color.green);
                        embedBuilder.setTitle(Coin.getEmojiID() + " Sell Action");
                        embedBuilder.setDescription(description.toString());

                        embedBuilder.setTimestamp(Instant.now());
                        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                        channel.sendMessageEmbeds(embedBuilder.build()).queue();
                    }catch (IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
                if(Utils.ResourcesEnumContainsString(ore)){
                    int price = ResourceUtils.getPriceFromString(args.get(1));
                    int quantity = MineMate.getInstance().getDatabaseManager().getResourceQuantityFromString(uuid, args.get(1));

                    MineMate.getInstance().getDatabaseManager().resetResourceFromString(uuid, ore.toLowerCase());
                    MineMate.getInstance().getDatabaseManager().addToBalanceFromUUID(uuid, price*quantity);

                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(user.getId());
                    description.append(">");
                    description.append(" ➔ You have sold ").append(Utils.IntToString(quantity)).append(" ").append(args.get(1).toLowerCase()).append(" for : ");
                    description.append(Utils.IntToString(price*quantity)).append(" ");
                    description.append(Coin.getEmojiID());

                    embedBuilder.setTitle(Coin.getEmojiID() + " Sell Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                }else {
                    if (args.get(1).toLowerCase().equals("all")){
                        return;
                    }
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append("<@");
                    stringBuilder.append(user.getId());
                    stringBuilder.append(">");
                    stringBuilder.append(" ➔ The resource you specified doesn't exist");

                    embedBuilder.setTitle(Coin.getEmojiID() + " Sell Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(stringBuilder.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                }
            }
        }
    }
}
