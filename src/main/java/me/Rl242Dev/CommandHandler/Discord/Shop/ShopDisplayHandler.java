package me.Rl242Dev.CommandHandler.Discord.Shop;

import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.Classes.Utils.Coin;
import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Items.Ressource.Type;
import me.Rl242Dev.Classes.Utils.Emoji;
import me.Rl242Dev.MineMate;
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

/*

@A = Rl242Dev
@U = Shop
@E = Class for ShopDisplayHandler

 */

public class ShopDisplayHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User user = event.getAuthor();

        if (user.equals(event.getJDA().getSelfUser())){
            return;
        }

        Message message = event.getMessage();
        MessageChannelUnion channel = event.getChannel();

        List<String> args = Arrays.asList(message.getContentRaw().split(" "));

        // Utils (TNT...)
        // Pets
        // Sword
        // Axe

        Player player = new Player(user.getId());

        if(args.contains(".shop")){
            if(MineMate.debug){
                MineMate.getLogger().appendLogger(player.getUuid()+" Issued .shop");
                MineMate.getLogger().send();
            }

            if(args.size() != 2){
                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("<@");
                stringBuilder.append(user.getId());
                stringBuilder.append(">");
                stringBuilder.append(" ➔ Shop Display :");

                embedBuilder.setTitle(Coin.getEmojiID() + "Shop Action");
                embedBuilder.setColor(Color.green);

                embedBuilder.setDescription(stringBuilder.toString());

                embedBuilder.addField(Emoji.getPickaxeEmoji() + " Pickaxes", ".shop pickaxes | .shop pick", false);
                embedBuilder.addField(Emoji.getHoeEmoji() + " Hoes", ".shop h | .shop hoes", false);
                // Utils (TNT...)
                // Pets
                // Sword
                // Axe

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
            }
            if(args.contains("pickaxes") || args.contains("pick")){
                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder stringBuilder = new StringBuilder();

                Item WoodPickaxe = new Item(Material.WOOD, Type.PICKAXE, "0"); // If Item id = 0, Item is for display purposes
                Item StonePickaxe = new Item(Material.STONE, Type.PICKAXE, "0");
                Item IronPickaxe = new Item(Material.IRON, Type.PICKAXE, "0");
                Item GoldPickaxe = new Item(Material.GOLD, Type.PICKAXE, "0");
                Item DiamondPickaxe = new Item(Material.DIAMOND, Type.PICKAXE, "0");

                stringBuilder.append("<@");
                stringBuilder.append(user.getId());
                stringBuilder.append(">");
                stringBuilder.append(" ➔ Pickaxes Shop | .buy Pickaxe [Material] :");

                embedBuilder.setTitle(Emoji.getPickaxeEmoji() + "Shop Action");
                embedBuilder.setColor(Color.green);

                embedBuilder.setDescription(stringBuilder.toString());

                embedBuilder.addField(Emoji.getPickaxeEmoji() + WoodPickaxe.getDisplayName(), ResourceUtils.PriceToString(WoodPickaxe.getSellPrice()) + Coin.getEmojiID(), false);
                embedBuilder.addField(Emoji.getPickaxeEmoji() + StonePickaxe.getDisplayName(), ResourceUtils.PriceToString(StonePickaxe.getSellPrice()) + Coin.getEmojiID(), false);
                embedBuilder.addField(Emoji.getPickaxeEmoji() + IronPickaxe.getDisplayName(), ResourceUtils.PriceToString(IronPickaxe.getSellPrice()) + Coin.getEmojiID(), false);
                embedBuilder.addField(Emoji.getPickaxeEmoji() + GoldPickaxe.getDisplayName(), ResourceUtils.PriceToString(GoldPickaxe.getSellPrice()) + Coin.getEmojiID(), false);
                embedBuilder.addField(Emoji.getPickaxeEmoji() + DiamondPickaxe.getDisplayName(), ResourceUtils.PriceToString(DiamondPickaxe.getSellPrice()) + Coin.getEmojiID(), false);

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
            }
        }
    }
}
