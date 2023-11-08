package me.Rl242Dev.Classes.Cases;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import me.Rl242Dev.MineMate;
import me.Rl242Dev.Utils;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.Classes.Entity.Pets.Bee;
import me.Rl242Dev.Classes.Entity.Pets.Cat;
import me.Rl242Dev.Classes.Entity.Pets.Goat;
import me.Rl242Dev.Classes.Entity.Pets.PetUtils;
import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Levels.RanksUtils;
import me.Rl242Dev.Classes.Utils.Emoji;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.awt.Color;

public class CaseUtils {
    public static EmbedBuilder process(String UUID, String caseLoot, Member member, Guild guild){
        Player player = new Player(UUID);

        List<String> Identifiers = Arrays.asList(caseLoot.split("_"));
        String category = Identifiers.get(0);
        String loot = Identifiers.get(1);

        if(category.equals("PET")){
            if(player.getPet() != null){
                switch(PetUtils.getPetFromString(loot.toLowerCase())){
                    case BEE -> {
                        MineMate.getInstance().getDatabaseManager().addToBalanceFromUUID(UUID, new Bee().getPrice());
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        StringBuilder description = new StringBuilder();

                        description.append("<@");
                        description.append(player.getUuid());
                        description.append(">");
                        description.append(" ➔ You have received "+Utils.IntToString(new Bee().getPrice())+" since you already had a pet");

                        embedBuilder.setTitle(Emoji.getXpEmoji() + " Case Action");
                        embedBuilder.setColor(Color.green);

                        embedBuilder.setDescription(description.toString());

                        embedBuilder.setTimestamp(Instant.now());
                        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
                        return embedBuilder;
                    }
                    case GOAT -> {
                        MineMate.getInstance().getDatabaseManager().addToBalanceFromUUID(UUID, new Goat().getPrice());
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        StringBuilder description = new StringBuilder();

                        description.append("<@");
                        description.append(player.getUuid());
                        description.append(">");
                        description.append(" ➔ You have received "+Utils.IntToString(new Goat().getPrice())+" since you already had a pet");

                        embedBuilder.setTitle(Emoji.getXpEmoji() + " Case Action");
                        embedBuilder.setColor(Color.green);

                        embedBuilder.setDescription(description.toString());

                        embedBuilder.setTimestamp(Instant.now());
                        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
                        return embedBuilder;
                    }
                    case CAT -> {
                        MineMate.getInstance().getDatabaseManager().addToBalanceFromUUID(UUID, new Cat().getPrice());
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        StringBuilder description = new StringBuilder();

                        description.append("<@");
                        description.append(player.getUuid());
                        description.append(">");
                        description.append(" ➔ You have received "+Utils.IntToString(new Cat().getPrice())+" since you already had a pet");

                        embedBuilder.setTitle(Emoji.getXpEmoji() + " Case Action");
                        embedBuilder.setColor(Color.green);

                        embedBuilder.setDescription(description.toString());

                        embedBuilder.setTimestamp(Instant.now());
                        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
                        return embedBuilder;
                    }
                }  
            }
            MineMate.getInstance().getDatabaseManager().updatePet(player.getUuid(), PetUtils.getPetFromString(loot));
            EmbedBuilder embedBuilder = new EmbedBuilder();
            StringBuilder description = new StringBuilder();

            description.append("<@");
            description.append(player.getUuid());
            description.append(">");
            description.append(" ➔ You have received"+PetUtils.getNameFromPet(PetUtils.getPetFromString(loot))+" pet");

            embedBuilder.setTitle(Emoji.getXpEmoji() + " Case Action");
            embedBuilder.setColor(Color.green);

            embedBuilder.setDescription(description.toString());

            embedBuilder.setTimestamp(Instant.now());
            embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
            return embedBuilder;
        }
        if(category.equals("LEVEL")){
            if(player.getLevel() == 301){
                MineMate.getInstance().getDatabaseManager().addToBalanceFromUUID(player.getUuid(), RanksUtils.getPriceForLevelUp(RanksUtils.getLevelFromString(loot)));
                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder description = new StringBuilder();

                description.append("<@");
                description.append(player.getUuid());
                description.append(">");
                description.append(" ➔ You have received "+Utils.IntToString(RanksUtils.getPriceForLevelUp(RanksUtils.getLevelFromString(loot)))+"because you are already level 301");

                embedBuilder.setTitle(Emoji.getXpEmoji() + " Case Action");
                embedBuilder.setColor(Color.green);

                embedBuilder.setDescription(description.toString());

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
                return embedBuilder;
            }
            MineMate.getInstance().getDatabaseManager().updateLevelToUUID(player.getUuid(), player.getLevel() + RanksUtils.getLevelFromString(loot));
            EmbedBuilder embedBuilder = new EmbedBuilder();
            StringBuilder description = new StringBuilder();

            description.append("<@");
            description.append(player.getUuid());
            description.append(">");
            description.append(" ➔ You have leveled up to "+Utils.IntToString(player.getLevel()+RanksUtils.getLevelFromString(loot)));

            embedBuilder.setTitle(Emoji.getXpEmoji() + " Case Action");
            embedBuilder.setColor(Color.green);

            embedBuilder.setDescription(description.toString());

            embedBuilder.setTimestamp(Instant.now());
            embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
            return embedBuilder;
        }
        if(category.equals("ROLE")){
            for(Role role : member.getRoles()){
                if(role.getId().equals(MineMate.getConfigManager().getString("general.legend_role_id"))){
                    MineMate.getInstance().getDatabaseManager().addToBalanceFromUUID(player.getUuid(), 10000);
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(player.getUuid());
                    description.append(">");
                    description.append(" ➔ You were given 10k because you already have the Legend role");

                    embedBuilder.setTitle(Emoji.getXpEmoji() + " Case Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
                    return embedBuilder;
                }
            }

            Role role = guild.getRoleById(MineMate.getConfigManager().getString("general.legend_role_id"));
            guild.addRoleToMember(member, role).queue();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            StringBuilder description = new StringBuilder();

            description.append("<@");
            description.append(player.getUuid());
            description.append(">");
            description.append(" ➔ You were given the Legend role");

            embedBuilder.setTitle(Emoji.getXpEmoji() + " Case Action");
            embedBuilder.setColor(Color.green);

            embedBuilder.setDescription(description.toString());

            embedBuilder.setTimestamp(Instant.now());
            embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
            return embedBuilder;
        }
        if(category.equals("ITEM")){
            Item lootItem = new Item(
                ResourceUtils.getMaterialFromString(Identifiers.get(1)),
                ResourceUtils.getTypeFromString(Identifiers.get(2)),
                "0"
            );

            switch(ResourceUtils.getTypeFromString(Identifiers.get(2))){
                case HOE -> {
                    if(player.getHoe().getMaterial().ordinal() < lootItem.getMaterial().ordinal()){
                        MineMate.getInstance().getDatabaseManager().updateItem(player.getUuid(), lootItem);
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        StringBuilder description = new StringBuilder();

                        description.append("<@");
                        description.append(player.getUuid());
                        description.append(">");
                        description.append(" ➔ You have dropped "+lootItem.getDisplayName());

                        embedBuilder.setTitle(Emoji.getXpEmoji() + " Case Action");
                        embedBuilder.setColor(Color.green);

                        embedBuilder.setDescription(description.toString());

                        embedBuilder.setTimestamp(Instant.now());
                        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
                        return embedBuilder;
                    }
                    MineMate.getInstance().getDatabaseManager().addToBalanceFromUUID(player.getUuid(), lootItem.getSellPrice());
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(player.getUuid());
                    description.append(">");
                    description.append(" ➔ You were given "+Utils.IntToString(lootItem.getSellPrice())+"k because you already had a better Item");

                    embedBuilder.setTitle(Emoji.getXpEmoji() + " Case Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
                    return embedBuilder;
                }
                case PICKAXE -> {
                    if(player.getPickaxe().getMaterial().ordinal() < lootItem.getMaterial().ordinal()){
                        MineMate.getInstance().getDatabaseManager().updateItem(player.getUuid(), lootItem);
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        StringBuilder description = new StringBuilder();

                        description.append("<@");
                        description.append(player.getUuid());
                        description.append(">");
                        description.append(" ➔ You have dropped "+lootItem.getDisplayName());

                        embedBuilder.setTitle(Emoji.getXpEmoji() + " Case Action");
                        embedBuilder.setColor(Color.green);

                        embedBuilder.setDescription(description.toString());

                        embedBuilder.setTimestamp(Instant.now());
                        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
                        return embedBuilder;
                    }
                    MineMate.getInstance().getDatabaseManager().addToBalanceFromUUID(player.getUuid(), lootItem.getSellPrice());
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(player.getUuid());
                    description.append(">");
                    description.append(" ➔ You were given "+Utils.IntToString(lootItem.getSellPrice())+"k because you already had a better Item");

                    embedBuilder.setTitle(Emoji.getXpEmoji() + " Case Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
                    return embedBuilder;
                }
                case SWORD -> {
                    return null;
                }
            }
        }    
        return null;
    }
}
