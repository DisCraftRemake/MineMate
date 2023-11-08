package me.Rl242Dev.Classes.Levels;

/*

@A = Rl242Dev
@U = Ranks
@E = Utils for Ranks

 */

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

    public static int getLevelFromString(String level){
        switch (level) {
            case "ONE": return 1;
            case "TWO": return 2;
            case "THREE": return 3;
            case "FOUR": return 4;
            case "FIVE": return 5;
            default: return 0;
        }
    }

    public static String presentRank(Ranks rank){
        switch (rank){
            case BRONZE -> {
                return "Bronze";
            }
            case SILVER -> {
                return "Silver";
            }
            case GOLD -> {
                return "Gold";
            }
            case PLATINUM -> {
                return "Platinum";
            }
            case DIAMOND -> {
                return "Diamond";
            }
            case MASTER -> {
                return "Master";
            }
            case GRAND_MASTER -> {
                return "Grand Master";
            }
            case CHALLENGER -> {
                return "Challenger";
            }
            case LEGEND -> {
                return "Legend";
            }
        }
        return null;
    }
}
