package Toby.MainBuild.Dto;

public enum Level {
    GOLD(3, null),
    SILVER(2, GOLD),
    BASIC(1, SILVER);

    int intValue;
    Level nextLevel;

    Level(int intValue, Level nextLevel) {
        this.intValue = intValue;
        this.nextLevel = nextLevel;
    }

    public int getInt() {
        return intValue;
    }

    public Level getNextLevel() {
        return this.nextLevel;
    }

    public static Level getUserLevel(int value) throws AssertionError{
        switch (value) {
            case 1: return BASIC;
            case 2: return SILVER;
            case 3: return GOLD;
            default : throw new AssertionError("!- Unknown Value : [ value = " + value + "]");
        }
    }

    public static int getIntValue(Level value) throws AssertionError{
        switch (value) {
            case BASIC: return 1;
            case SILVER: return 2;
            case GOLD: return 3;
            default: throw new AssertionError("!- Unknown Value : [ value = " + value + "]");
        }
    }
}
