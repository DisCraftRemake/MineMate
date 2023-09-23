package me.Rl242Dev.Classes.Cases;

import me.Rl242Dev.Classes.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Random;

/*

@A = Rl242Dev
@U = Cases
@E = Main Class for Cases, used when a user when to open a case

 */

public class Case {
    private final int price;
    private final Map<? extends Enum<?>, Integer> loots;
    private final String name;

    @Nullable
    private Integer level;

    public Case(String name, int price, Map<? extends Enum<?>, Integer> loots) {
        this.name = name;
        this.price = price;
        this.loots = loots;
    }

    public Case(String name, int price, Map<? extends Enum<?>, Integer> loots, @Nullable Integer level){
        this.name = name;
        this.price = price;
        this.loots = loots;
        this.level = level;
    }

    public int getPrice() {
        return price;
    }

    public Map<? extends Enum<?>, Integer> getLoots() {
        return loots;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public Integer getLevel() {
        return level;
    }

    public <T> T open(String UUID){
        Player player = new Player(UUID);

        if(player.getLevel() < this.level){
            StringBuilder stringBuilder = new StringBuilder("You cannot open this case because you don't have enought money : "+player.getLevel()+"/"+this.level);
            player.sendMessage("Case Action", stringBuilder);
        }
        if(player.getBalance() < this.price){
            StringBuilder stringBuilder = new StringBuilder("You cannot open this case because you don't have enought money : "+player.getBalance()+"/"+this.price);
            player.sendMessage("Case Action", stringBuilder);
        }else{
            int totalProbability = this.loots.values().stream().mapToInt(Integer::intValue).sum();

            Random rand = new Random();
            int randomValue = rand.nextInt(totalProbability);

            int cumulativeProbability = 0;
            for (Map.Entry<?, Integer> entry : this.loots.entrySet()) {
                cumulativeProbability += entry.getValue();
                if (randomValue < cumulativeProbability) {
                    return (T) entry.getKey();
                }
            }
        }

        return null;
    }
}
