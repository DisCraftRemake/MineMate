package me.Rl242Dev.Classes.Levels;

public class RanksUtils {

    public static Ranks GetRankFromLevel(int level)
    {
        if (level >= 0 && level <= 19)
            return Ranks.BRONZE;
        else if (level >= 20 && level <= 39)
            return Ranks.SILVER;
        else if (level >= 40 && level <= 49)
            return Ranks.GOLD;
        else if (level >= 50 && level <= 74)
            return Ranks.PLATINUM;
        else if (level >= 75 && level <= 99)
            return Ranks.DIAMOND;
        else if (level >= 100 && level <= 124)
            return Ranks.MASTER;
        else if (level >= 125 && level <= 149)
            return Ranks.GRAND_MASTER;
        else if (level >= 150 && level <= 299)
            return Ranks.CHALLENGER;
        else if (level >= 300)
            return Ranks.LEGEND;
        else
            throw new IllegalArgumentException("Invalid level value");
    }

    public static int getPriceForLevelUp(int level){
        return 100000 * level;
    }
}
