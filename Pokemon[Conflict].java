import javax.swing.*;

/**
 * @author John Wuller
 * @version 2.0
 */
public class Pokemon{
    //Note - Max stats is actually the default stats, not the maximum!
    /** the minimum level randomly assigned to a Pokemon */
    public static int minLv = 85;
    /** the maximum level randomly assigned to a Pokemon */
    public static int maxLv = 100;
    String Name;
    long Health;
    long MaxHealth;
    long Attack;
    long Defense;
    long SpAttack;
    long SpDefense;
    long Speed;
    int SpCond;
    int SpCondTurnNum;
    long MaxAttack;
    long MaxDefense;
    long MaxSpAttack;
    long MaxSpDefense;
    long MaxSpeed;
    double Accuracy, Evasion;
    boolean Recharge;
    type TypeOne;
    type TypeTwo;
    int Level;
    attack[] AttackList = new attack[4];
    int[] statLvChange = {
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
        };
        
    //number 5 is for struggle
    int[] pp = {0, 0, 0, 0, 1};

    Battle_4_1 game;
    Generic g = game.g;


    /**
     * @author John Wuller
     * @version 2.0
     * 
     * The constructor when given any values
     */
    public Pokemon(Battle_4_1 game, String name, int level, long attack, long defense, long spAttack, long spDefense, long speed, long health, attack attack1,
    attack attack2, attack attack3, attack attack4, type type1, type type2) {

        this.game = game;

        this.AttackList[0] = attack1;
        this.AttackList[1] = attack2;
        this.AttackList[2] = attack3;
        this.AttackList[3] = attack4;

        for (int i = 0; i < 4; i++) {
            this.pp[i] = AttackList[i].maxPP;
            AttackList[i].pp = AttackList[i].maxPP;
            for (int j = 0; j < i; j++) {
                if (this.AttackList[i].equals(this.AttackList[j])) {
                    this.AttackList[i] = Battle_4_1.noAttack;
                }
            }

            this.Name = name;
            this.Health = health;
            this.MaxHealth = health;
            this.MaxAttack = attack;
            this.MaxDefense = defense;
            this.MaxSpAttack = spAttack;
            this.MaxSpDefense = spDefense;
            this.MaxSpeed = speed;
            this.SpCond = 0;
            this.SpCondTurnNum = 0;
            this.Speed = MaxSpeed;
            this.Attack = MaxAttack;
            this.Defense = MaxDefense;
            this.SpAttack = MaxSpAttack;
            this.SpDefense = MaxSpDefense;
            this.Recharge = false;
            this.Level = level;
            this.Accuracy = 1;
            this.Evasion = 1;

            this.TypeOne = type1;
            this.TypeTwo = type2;

            if (TypeTwo.equals(TypeOne)) {
                this.TypeTwo = Battle_4_1.none;

            }





        }
    }

    /**
     * @author John Wuller
     * @version 2.0
     * 
     * The constructor which gets everything randomly
     */
    public Pokemon(Battle_4_1 game, String name) {

        this.Name = name;
        this.Level = (int) g.randomNumber(minLv, maxLv);
        int minStatLv = (int) Level * 2;
        int maxStatLv = (int) Level * 4;

        this.AttackList[0] = game.getRandomAttack();
        this.AttackList[1] = game.getRandomAttack();
        this.AttackList[2] = game.getRandomAttack();
        this.AttackList[3] = game.getRandomAttack();

        for (int i = 0; i < 4; i++) {
            this.pp[i] = AttackList[i].maxPP;
            AttackList[i].pp = AttackList[i].maxPP;
            for (int j = 0; j < i; j++) {
                if (this.AttackList[i].equals(this.AttackList[j])) {
                    type none = Battle_4_1.none;
                    this.AttackList[i] = new attack(this.game, "none", "none", none, "none", 0, 0, 0, 0);
                }
            }

            this.Health = g.randomNumber(minStatLv, maxStatLv);
            this.MaxHealth = this.Health;
            this.MaxAttack = g.randomNumber(minStatLv, maxStatLv);
            this.MaxDefense = g.randomNumber(minStatLv, maxStatLv);
            this.MaxSpAttack = g.randomNumber(minStatLv, maxStatLv);
            this.MaxSpDefense = g.randomNumber(minStatLv, maxStatLv);
            this.MaxSpeed = g.randomNumber(minStatLv, maxStatLv);
            this.SpCond = 0;
            this.SpCondTurnNum = 0;
            this.Speed = MaxSpeed;
            this.Attack = MaxAttack;
            this.Defense = MaxDefense;
            this.SpAttack = MaxSpAttack;
            this.SpDefense = MaxSpDefense;
            this.Recharge = false;
            this.Accuracy = 1;
            this.Evasion = 1;

            this.TypeOne = Battle_4_1.types[(int) g.randomNumber(0, 17)];
            this.TypeTwo = Battle_4_1.types[(int) g.randomNumber(0, 18)];

            for (i = 0; i < 4; i++) {
                if (this.AttackList[i].effect.equals("sand")) {
                    if (g.randomNumber(1, 2) == 1) {
                        this.TypeTwo = Battle_4_1.Rock;
                    } else {
                        this.TypeTwo = Battle_4_1.Steel;
                    }
                } else if (this.AttackList[i].effect.equals("hail")) {
                    this.TypeTwo = Battle_4_1.Ice;
                }
            }


            if (TypeTwo.equals(TypeOne)) {
                this.TypeTwo = Battle_4_1.none;
            }


        }
    }


    /**
     * @author John Wuller
     * @version 3.2
     * 
     * @param stat  the stat to be changed
     * @param howMuch  How many levels the stat should be changed
     * 
     * This changes a stat of the Pokemon by a given number of levels
     */
    public void statChange(String stat, int howMuch) {

        switch (stat) {

            case "attack":
            if (howMuch == 1) {
                g.p(this.Name + "'s attack rose!");
            } else if (howMuch == -1) {
                g.p(this.Name + "'s attack fell!");
            } else if (howMuch == 2) {
                g.p(this.Name + "'s attack sharply rose!");
            } else if (howMuch == -2) {
                g.p(this.Name + "'s attack harshly fell!");
            } else if (howMuch > 2) {
                g.p(this.Name + "'s attack rose drastically!");
            } else {
                g.p(this.Name + "'s attack severely fell!");

            }

            this.statLvChange[1] += howMuch;
            if (this.statLvChange[1] > 6) {
                g.p(this.Name + "'s attack can't go any higher!");
                this.statLvChange[1] = 6;
            } else if (this.statLvChange[1] < -6) {
                g.p(this.Name + "'s attack can't go any lower!");
                this.statLvChange[1] = -6;
            }

            if (this.statLvChange[1] < 0) {
                this.Attack = this.MaxAttack * 2 / (Math.abs(this.statLvChange[1]) + 2);
            } else {
                this.Attack = this.MaxAttack * (this.statLvChange[1] + 2) / 2;
            }
            break;

            case "defense":
            if (howMuch == 1) {
                g.p(this.Name + "'s defense rose!");
            } else if (howMuch == -1) {
                g.p(this.Name + "'s defense fell!");
            } else if (howMuch == 2) {
                g.p(this.Name + "'s defense sharply rose!");
            } else if (howMuch == -2) {
                g.p(this.Name + "'s defense harshly fell!");
            } else if (howMuch > 2) {
                g.p(this.Name + "'s defense rose drastically!");
            } else {
                g.p(this.Name + "'s defense severely fell!");

            }

            this.statLvChange[2] += howMuch;
            if (this.statLvChange[2] > 6) {
                g.p(this.Name + "'s defense can't go any higher!");
                this.statLvChange[2] = 6;
            } else if (this.statLvChange[2] < -6) {
                g.p(this.Name + "'s defense can't go any lower!");
                this.statLvChange[2] = -6;
            }

            if (this.statLvChange[2] < 0) {
                this.Defense = this.MaxDefense * 2 / (Math.abs(this.statLvChange[2]) + 2);
            } else {
                this.Defense = this.MaxDefense * (this.statLvChange[2] + 2) / 2;
            }
            break;

            case "spattack":
            if (howMuch == 1) {
                g.p(this.Name + "'s special attack rose!");
            } else if (howMuch == -1) {
                g.p(this.Name + "'s special attack fell!");
            } else if (howMuch == 2) {
                g.p(this.Name + "'s special attack sharply rose!");
            } else if (howMuch == -2) {
                g.p(this.Name + "'s special attack harshly fell!");
            } else if (howMuch > 2) {
                g.p(this.Name + "'s special attack rose drastically!");
            } else {
                g.p(this.Name + "'s special attack severely fell!");

            }

            this.statLvChange[3] += howMuch;
            if (this.statLvChange[3] > 6) {
                g.p(this.Name + "'s special attack can't go any higher!");
                this.statLvChange[3] = 6;
            } else if (this.statLvChange[3] < -6) {
                g.p(this.Name + "'s special attack can't go any lower!");
                this.statLvChange[3] = -6;
            }

            if (this.statLvChange[3] < 0) {
                this.SpAttack = this.MaxSpAttack * 2 / (Math.abs(this.statLvChange[3]) + 2);
            } else {
                this.SpAttack = this.MaxSpAttack * (this.statLvChange[3] + 2) / 2;
            }
            break;

            case "spdefense":
            if (howMuch == 1) {
                g.p(this.Name + "'s special defense rose!");
            } else if (howMuch == -1) {
                g.p(this.Name + "'s special defense fell!");
            } else if (howMuch == 2) {
                g.p(this.Name + "'s special defense sharply rose!");
            } else if (howMuch == -2) {
                g.p(this.Name + "'s special defense harshly fell!");
            } else if (howMuch > 2) {
                g.p(this.Name + "'s special defense rose drastically!");
            } else {
                g.p(this.Name + "'s special defense severely fell!");

            }

            this.statLvChange[4] += howMuch;
            if (this.statLvChange[4] > 6) {
                g.p(this.Name + "'s special defense can't go any higher!");
                this.statLvChange[4] = 6;
            } else if (this.statLvChange[4] < -6) {
                g.p(this.Name + "'s special defense can't go any lower!");
                this.statLvChange[4] = -6;
            }

            if (this.statLvChange[4] < 0) {
                this.SpDefense = this.MaxSpDefense * 2 / (Math.abs(this.statLvChange[4]) + 2);
            } else {
                this.SpDefense = this.MaxSpDefense * (this.statLvChange[4] + 2) / 2;
            }
            break;

            case "speed":
            if (howMuch == 1) {
                g.p(this.Name + "'s speed rose!");
            } else if (howMuch == -1) {
                g.p(this.Name + "'s speed fell!");
            } else if (howMuch == 2) {
                g.p(this.Name + "'s speed sharply rose!");
            } else if (howMuch == -2) {
                g.p(this.Name + "'s speed harshly fell!");
            } else if (howMuch > 2) {
                g.p(this.Name + "'s speed rose drastically!");
            } else {
                g.p(this.Name + "'s speed severely fell!");

            }

            this.statLvChange[5] += howMuch;
            if (this.statLvChange[5] > 6) {
                g.p(this.Name + "'s speed can't go any higher!");
                this.statLvChange[5] = 6;
            } else if (this.statLvChange[5] < -6) {
                g.p(this.Name + "'s speed can't go any lower!");
                this.statLvChange[5] = -6;
            }

            if (this.statLvChange[5] < 0) {
                this.Speed = this.MaxSpeed * 2 / (Math.abs(this.statLvChange[5]) + 2);
            } else {
                this.Speed = this.MaxSpeed * (this.statLvChange[5] + 2) / 2;
            }
            break;

            case "accuracy":
            //g.p("Accuracy and Evasion support coming soon!");
            if (howMuch == 1) {
                g.p(this.Name + "'s accuracy rose!");
            } else if (howMuch == -1) {
                g.p(this.Name + "'s accuracy fell!");
            } else if (howMuch > 0) {
                g.p(this.Name + "'s accuracy sharply rose!");
            } else {
                g.p(this.Name + "'s accuracy harshly fell!");
            }

            this.statLvChange[6] += howMuch;
            if (this.statLvChange[6] > 6) {
                g.p(this.Name + "'s accuracy can't go any higher!");
                this.statLvChange[6] = 6;
            } else if (this.statLvChange[6] < -6) {
                g.p(this.Name + "'s accuracy can't go any lower!");
                this.statLvChange[6] = -6;
            }

            if (this.statLvChange[6] < 0) {
                this.Accuracy = 3.0 / (3.0 - this.statLvChange[6]);
            } else {
                this.Accuracy = (this.statLvChange[6] + 3.0) / 3.0;
            }
            break;

            case "evasion":
            //g.p("Accuracy and Evasion support coming soon!");
            if (howMuch == 1) {
                g.p(this.Name + "'s evasion rose!");
            } else if (howMuch == -1) {
                g.p(this.Name + "'s evasion fell!");
            } else if (howMuch > 0) {
                g.p(this.Name + "'s evasion sharply rose!");
            } else {
                g.p(this.Name + "'s evasion harshly fell!");
            }

            this.statLvChange[7] += howMuch;
            if (this.statLvChange[7] > 6) {
                g.p(this.Name + "'s evasion can't go any higher!");
                this.statLvChange[7] = 6;
            } else if (this.statLvChange[7] < -6) {
                g.p(this.Name + "'s evasion can't go any lower!");
                this.statLvChange[7] = -6;
            }

            if (this.statLvChange[7] < 0) {
                this.Evasion = 3.0 / (3.0 - this.statLvChange[7]);
            } else {
                this.Evasion = (this.statLvChange[7] + 3.0) / 3.0;
            }
            break;

            default:
            System.err.println("ERROR - " + stat + " is an invalid stat type");
            break;
        }

    }
    
    
    @Override
    public String toString() {
        return
        Name + " (lv. " + Level + ")'s stats:"+
        "\n\nAttack: " + Attack +
        "\nDefense: " + Defense +
        "\nSpecial Attack: " + SpAttack +
        "\nSpecial Defense: " + SpDefense +
        "\nSpeed: " + Speed +
        "\nHealth: " + Health + " of " + MaxHealth +
        "\nAccuracy: " + Math.floor(Accuracy * 10.0)/10.0 +
        "\nEvasiness: " + Math.floor(Evasion * 10.0)/10.0 + "\n" +
        "\nType 1: " + TypeOne +
        "\nType 2: " + TypeTwo 
        
        + "\n\n" +
        AttackList[0] + "\n\n" +
        AttackList[1] + "\n\n" +
        AttackList[2] + "\n\n" +
        AttackList[3];

    }

}