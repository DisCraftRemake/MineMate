package me.Rl242Dev.CommandHandler.Discord.Shop;

import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import me.Rl242Dev.Classes.Entity.Pets.*;
import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Items.Ressource.Type;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.Classes.Utils.Coin;
import me.Rl242Dev.MineMate;
import me.Rl242Dev.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/*

@A = Rl242Dev
@U = Shop
@E = Class for the BuyHandler, used when users buy Items

 */

public class BuyHandler extends ListenerAdapter{

    public EmbedBuilder MoneyError(String UUID, int price, int balance){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        StringBuilder description = new StringBuilder();

        description.append("<@");
        description.append(UUID);
        description.append(">");
        description.append(" ➔ You don't have enough to afford this "+balance+"/"+price+"| <#"+MineMate.getConfigManager().getString("channels.help")+">");

        embedBuilder.setTitle(Coin.getEmojiID() + " Buy Action");
        embedBuilder.setColor(Color.green);

        embedBuilder.setDescription(description.toString());

        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

        return embedBuilder;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User user = event.getAuthor();
        String uuid = user.getId();

        if (user.equals(event.getJDA().getSelfUser())){
            return;
        }

        Message message = event.getMessage();
        MessageChannelUnion channel = event.getChannel();

        Player player = new Player(uuid);

        List<String> args = Arrays.asList(message.getContentRaw().split(" "));

        List<String> tokens = new ArrayList<>();
        tokens.add("pet");
        tokens.add("pets");
        tokens.add("PETS");
        tokens.add("PET");

        if(args.contains(".buy")){
            if(MineMate.debug){
                MineMate.getLogger().appendLogger(player.getUuid()+" Issued .buy");
                MineMate.getLogger().send();
            }

            if(args.size() != 3) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("<@");
                stringBuilder.append(user.getId());
                stringBuilder.append(">");
                stringBuilder.append(" ➔ .buy [Type] [Material] | <#"+MineMate.getConfigManager().getString("channels.help")+">");

                embedBuilder.setTitle(Coin.getEmojiID() + " Buy Action");
                embedBuilder.setColor(Color.green);

                embedBuilder.setDescription(stringBuilder.toString());

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
            }else {
                //TODO: UTILS buy
                if(tokens.contains(args.get(1))){
                    Pets pet = PetUtils.getPetFromString(args.get(2));

                    if(pet == null){
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        StringBuilder description = new StringBuilder();

                        description.append("<@");
                        description.append(user.getId());
                        description.append(">");
                        description.append(" ➔ Pet Invalid | <#"+MineMate.getConfigManager().getString("channels.help")+">");

                        embedBuilder.setTitle(Coin.getEmojiID() + " Buy Action");
                        embedBuilder.setColor(Color.green);

                        embedBuilder.setDescription(description.toString());

                        embedBuilder.setTimestamp(Instant.now());
                        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                        channel.sendMessageEmbeds(embedBuilder.build()).queue();
                        return;
                    }
                    if(player.getPet() != null){
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        StringBuilder description = new StringBuilder();

                        description.append("<@");
                        description.append(user.getId());
                        description.append(">");
                        description.append(" ➔ Your already have a pet | <#"+MineMate.getConfigManager().getString("channels.help")+">");

                        embedBuilder.setTitle(Coin.getEmojiID() + " Buy Action");
                        embedBuilder.setColor(Color.green);

                        embedBuilder.setDescription(description.toString());

                        embedBuilder.setTimestamp(Instant.now());
                        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                        channel.sendMessageEmbeds(embedBuilder.build()).queue();
                        return;
                    }
                    Object object = null;
                    switch (pet){
                        case CAT -> object = new Cat(pet);
                        case BEE -> object = new Bee(pet);
                        case GOAT -> object = new Goat(pet);
                    }

                    if (object instanceof Cat) {
                        Cat cat = (Cat) object;
                        if(cat.getPrice() > player.getBalance()){
                            EmbedBuilder embedBuilder = MoneyError(player.getUuid(), cat.getPrice(), player.getBalance());
                            channel.sendMessageEmbeds(embedBuilder.build()).queue();
                            return;
                        }

                        MineMate.getInstance().getDatabaseManager().removeAmountFromUUID(player.getUuid(), cat.getPrice());
                        MineMate.getInstance().getDatabaseManager().updatePet(player.getUuid(), cat.getType());

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        StringBuilder description = new StringBuilder();

                        description.append("<@");
                        description.append(user.getId());
                        description.append(">");
                        description.append(" ➔ Pet acquired | <#"+MineMate.getConfigManager().getString("channels.help")+">");

                        embedBuilder.setTitle(Coin.getEmojiID() + " Buy Action");
                        embedBuilder.setColor(Color.green);

                        embedBuilder.setDescription(description.toString());

                        embedBuilder.setTimestamp(Instant.now());
                        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                        channel.sendMessageEmbeds(embedBuilder.build()).queue();
                        return;
                    } else if (object instanceof Bee) {
                        Bee bee = (Bee) object;
                        if(bee.getPrice() > player.getBalance()){
                            EmbedBuilder embedBuilder = MoneyError(player.getUuid(), bee.getPrice(), player.getBalance());
                            channel.sendMessageEmbeds(embedBuilder.build()).queue();
                            return;
                        }

                        MineMate.getInstance().getDatabaseManager().removeAmountFromUUID(player.getUuid(), bee.getPrice());
                        MineMate.getInstance().getDatabaseManager().updatePet(player.getUuid(), bee.getType());

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        StringBuilder description = new StringBuilder();

                        description.append("<@");
                        description.append(user.getId());
                        description.append(">");
                        description.append(" ➔ Pet acquired | <#"+MineMate.getConfigManager().getString("channels.help")+">");

                        embedBuilder.setTitle(Coin.getEmojiID() + " Buy Action");
                        embedBuilder.setColor(Color.green);

                        embedBuilder.setDescription(description.toString());

                        embedBuilder.setTimestamp(Instant.now());
                        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                        channel.sendMessageEmbeds(embedBuilder.build()).queue();
                        return;
                    } else if (object instanceof Goat) {
                        Goat goat = (Goat) object;
                        if(goat.getPrice() > player.getBalance()){
                            EmbedBuilder embedBuilder = MoneyError(player.getUuid(), goat.getPrice(), player.getBalance());
                            channel.sendMessageEmbeds(embedBuilder.build()).queue();
                            return;
                        }

                        MineMate.getInstance().getDatabaseManager().removeAmountFromUUID(player.getUuid(), goat.getPrice());
                        MineMate.getInstance().getDatabaseManager().updatePet(player.getUuid(), goat.getType());

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        StringBuilder description = new StringBuilder();

                        description.append("<@");
                        description.append(user.getId());
                        description.append(">");
                        description.append(" ➔ Pet acquired | <#"+MineMate.getConfigManager().getString("channels.help")+">");

                        embedBuilder.setTitle(Coin.getEmojiID() + " Buy Action");
                        embedBuilder.setColor(Color.green);

                        embedBuilder.setDescription(description.toString());

                        embedBuilder.setTimestamp(Instant.now());
                        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                        channel.sendMessageEmbeds(embedBuilder.build()).queue();
                        return;
                    }
                }
                Type type = null;
                Material material = null;
                if(Utils.TypeEnumContainsString(args.get(1))){
                    type = ResourceUtils.getTypeFromString(args.get(1));
                }
                if(Utils.MaterialEnumContainsString(args.get(2))){
                    material = ResourceUtils.getMaterialFromString(args.get(2));
                }

                if(material == null || type == null){
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(user.getId());
                    description.append(">");
                    description.append(" ➔ Type or Material incorrect | <#"+MineMate.getConfigManager().getString("channels.help")+">");

                    embedBuilder.setTitle(Coin.getEmojiID() + " Buy Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                    return;
                }

                Item item = new Item(material, type, "0");
                if(player.getBalance() < item.getSellPrice()){
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(user.getId());
                    description.append(">");
                    description.append(" ➔ You don't have enought money to buy this Item | " + player.getBalance()+"/"+item.getSellPrice());

                    embedBuilder.setTitle(Coin.getEmojiID() + " Buy Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                }else {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(user.getId());
                    description.append(">");
                    description.append(" ➔ You successfully bought this Item | " + item.getDisplayName());

                    embedBuilder.setTitle(Coin.getEmojiID() + " Buy Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                    channel.sendMessageEmbeds(embedBuilder.build()).queue();

                    MineMate.getInstance().getDatabaseManager().removeAmountFromUUID(player.getUuid(), item.getSellPrice());
                    MineMate.getInstance().getDatabaseManager().updateItem(player.getUuid(), item);
                }
            }
        }
    }
}
