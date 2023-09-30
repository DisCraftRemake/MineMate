package me.Rl242Dev.Classes.Clans;

import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.MineMate;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*

@U = Clans
@E = Main class for Clans
@A = Rl242Dev

 */

public class Clan {
    private final int max_size = 30;
    private final String name;
    private final List<Player> members = new ArrayList<>(); //Cloud
    private final List<Player> applications = new ArrayList<>();
    private Player owner; //Cloud
    private final String id;
    private final int level; //Computation
    private final int income; //Computation
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Clan(String id){
        this.id = id;

        /* Add members and Owner*/
        Map<Player, Integer> members = MineMate.getInstance().getDatabaseManager().getMembersOfClan(id);
        for(Player player : members.keySet()){
            if(members.get(player).equals(1)){
                this.owner = player;
            }
            this.members.add(player);
        }

        int level = 0;
        for(Player player : this.members){
            level = level + player.getLevel() + (player.getPrestige() * 10); // badges
        }

        this.level = level / this.members.size();
        this.income = Clan.calculateIncome(this.level);

        User clanOwner = MineMate.getBot().retrieveUserById(owner.getUuid()).complete();
        this.name = clanOwner.getName()+"'s Clan";

        scheduler.scheduleAtFixedRate(this::giveIncome, 0, 10, TimeUnit.MINUTES);
    }

    public int getMax_size() {
        return max_size;
    }

    public String getName() {
        return name;
    }

    public List<Player> getMembers() {
        return members;
    }

    public Player getOwner() {
        return owner;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public int getIncome() {
        return income;
    }

    public List<Player> getApplications() {
        return applications;
    }

    public static int calculateIncome(int level){
        return level * 1000;
    }

    public boolean kickPlayer(Player init, Player player){
        if(getOwner() == init){
            this.members.remove(player);
            MineMate.getInstance().getDatabaseManager().kickPlayerOfClan(this.id, player.getUuid());

            StringBuilder stringBuilder = new StringBuilder("You have been kicked from "+this.getName());

            player.sendMessage("Clan", stringBuilder);

            return true;
        }
        return false;
    }

    public boolean allowEntry(Player init, Player player){
        if(getOwner() == init){
            if(this.members.size() >= max_size){
                return false;
            }
            if(player.getClan() != null){
                return false;
            }
            if(!applications.contains(player)){
                return false;
            }
            this.applications.remove(player);
            this.members.add(player);
            MineMate.getInstance().getDatabaseManager().addClanMember(this.id, player.getUuid());

            StringBuilder stringBuilder = new StringBuilder("You have been accepted to "+this.getName());

            player.sendMessage("Clan", stringBuilder);
            return true;
        }
        return false;
    }

    public boolean leave(Player player){
        if(!this.members.contains(player)){
            return false;
        }

        this.members.remove(player);
        MineMate.getInstance().getDatabaseManager().kickPlayerOfClan(this.id, player.getUuid());

        StringBuilder stringBuilder = new StringBuilder("You have leaved "+this.getName());

        player.sendMessage("Clan", stringBuilder);
        return true;
    }

    public boolean applyForClan(Player player){
        if(player.getClan() != null){
            return false;
        }
        this.applications.add(player);
        this.owner.sendMessage(player.getProfil());
        return true;
    }

    private void giveIncome() {
        for(Player player : this.members){
            MineMate.getInstance().getDatabaseManager().addToBalanceFromUUID(player.getUuid(), this.income);
        }
    }
}
