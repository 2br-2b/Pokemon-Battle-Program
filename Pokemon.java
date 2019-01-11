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
    int Health = 0;
    int MaxHealth;
    int Attack;
    int Defense;
    int SpAttack;
    int SpDefense;
    int Speed;
    int MaxAttack;
    int MaxDefense;
    int MaxSpAttack;
    int MaxSpDefense;
    int MaxSpeed;
    double Accuracy, Evasion;
    boolean Recharge;
    type TypeOne;
    type TypeTwo;
    type oType1, oType2;
    int Level = (int) g.randomNumber(minLv, maxLv);
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

    //pp[4] is for struggle
    //pp[5] is for view stats, etc.
    int[] pp = {0, 0, 0, 0, 1, 0};
    SpecialCondition specialCondition;

    static Generic g = Battle.g;
    static Battle game;

    /**
     * @author John Wuller
     * @version 2.0
     * 
     * The constructor when given any values
     */
    public Pokemon(Battle game, String name, int level, int attack, int defense, int spAttack, int spDefense, int speed, int health, attack attack1,
    attack attack2, attack attack3, attack attack4, type type1, type type2, boolean setTypes) {

        this.game = game;

        this.AttackList[0] = attack1;
        this.AttackList[1] = attack2;
        this.AttackList[2] = attack3;
        this.AttackList[3] = attack4;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i; j++) {
                if (this.AttackList[i].equals(this.AttackList[j])) {
                    this.AttackList[i] = Battle.noAttack;
                }
            }
            this.pp[i] = this.AttackList[i].maxPP;
        }

        this.pp[0] = this.AttackList[0].maxPP;
        this.pp[1] = this.AttackList[1].maxPP;
        this.pp[2] = this.AttackList[2].maxPP;
        this.pp[3] = this.AttackList[3].maxPP;

        this.Name = name;
        this.Level = level;

        if(Level == -1){
            this.Level = g.randomNumber(minLv, maxLv);
        }

        int minStatLv = Level * 2;
        int maxStatLv = Level * 4;

        this.MaxHealth = health;
        this.MaxAttack = attack;
        this.MaxDefense = defense;
        this.MaxSpAttack = spAttack;
        this.MaxSpDefense = spDefense;
        this.MaxSpeed = speed;

        if(MaxHealth == -1){
            this.MaxHealth = g.randomNumber(minStatLv, maxStatLv);
        }
        if(MaxAttack == -1){
            this.MaxAttack = g.randomNumber(minStatLv, maxStatLv);
        }
        if(MaxDefense == -1){
            this.MaxDefense = g.randomNumber(minStatLv, maxStatLv);
        }
        if(MaxSpAttack == -1){
            this.MaxSpAttack = g.randomNumber(minStatLv, maxStatLv);
        }
        if(MaxSpDefense == -1){
            this.MaxSpDefense = g.randomNumber(minStatLv, maxStatLv);
        }
        if(MaxSpeed == -1){
            this.MaxSpeed = g.randomNumber(minStatLv, maxStatLv);
        }

        this.Health = MaxHealth;
        resetStatChanges();

        this.Recharge = false;

        this.TypeOne = type1;
        this.TypeTwo = type2;

        if(this.TypeOne == Battle.none && this.TypeTwo == Battle.none  && setTypes){
            this.TypeOne = AttackList[g.randomNumber(0,3)].attackType;
            this.TypeTwo = AttackList[g.randomNumber(0,3)].attackType;
        }

        for (int i = 0; i < 4; i++) {
            if (this.AttackList[i].effect.equals("sand")) {
                if (g.randomNumber(1, 2) == 1) {
                    this.TypeTwo = Battle.Rock;
                } else {
                    this.TypeTwo = Battle.Steel;
                }
            } else if (this.AttackList[i].effect.equals("hail")) {
                this.TypeTwo = Battle.Ice;
            }
        }

        if (TypeTwo.equals(TypeOne)) {
            this.TypeTwo = Battle.none;
        }

        oType1 = TypeOne;
        oType2 = TypeTwo;

        setSpecialCondition(0);

    }

    public Pokemon(Battle game, String name, int level, int attack, int defense, int spAttack, int spDefense, int speed, int health, attack attack1,
    attack attack2, attack attack3, attack attack4, type type1, type type2) {
        this(game, name, level, attack, defense,
            spAttack, spDefense, speed, health,
            attack1, attack2, attack3, attack4,
            type1, type2,
            true);
    }

    /**
     * @author John Wuller
     * @version 2.0
     * 
     * The constructor which gets everything randomly
     */
    public Pokemon(Battle game, String name) {

        this(game, name, -1, -1, -1, -1, -1, -1, -1, game.getRandomAttack(),
            game.getRandomAttack(), game.getRandomAttack(), game.getRandomAttack(),
            Battle.none,
            Battle.none);

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

    /**
     * @author John Wuller
     * @version 2.0
     */
    @Override
    public String toString() {

        String[] plusMinus = new String[8];
        for(int i = 0; i < statLvChange.length; i++)
            if(statLvChange[i] == 0)
                plusMinus[i] = "";
            else if(statLvChange[i] > 0)
                plusMinus[i] = " (+" + statLvChange[i] + ")";
            else
                plusMinus[i] = " (" + statLvChange[i] + ")";

        String tab = "    ";
        return Name + " (lv. " + Level + ")'s stats:\n"+
        "\nAttack: " + Attack + plusMinus[1] +
        "\nDefense: " + Defense + plusMinus[2] +
        "\nSpecial Attack: " + SpAttack + plusMinus[3] +
        "\nSpecial Defense: " + SpDefense + plusMinus[4] +
        "\nSpeed: " + Speed + plusMinus[5] +
        "\nHealth: " + Health + " of " + MaxHealth +
        "\nAccuracy: " + ((int)(Accuracy * 10 + 0.5)/10.0) + plusMinus[6] +
        "\nEvasiness: " + ((int)(Evasion * 10 + 0.5)/10.0) + plusMinus[7] + "\n" +
        "\nType 1: " + oType1 +
        "\nType 2: " + oType2 + "\n\n" +
        AttackList[0].toString(pp[0]) + "\n\n" + 
        AttackList[1].toString(pp[1]) + "\n\n" +
        AttackList[2].toString(pp[2]) + "\n\n" + 
        AttackList[3].toString(pp[3]);

    }

    /**
     * @author John Wuller
     * @version 1.0
     * 
     * Resets all the variables
     */
    public void reset() {

        for (int i = 0; i < 4; i++) {
            this.pp[i] = AttackList[i].maxPP;
            //AttackList[i].pp = AttackList[i].maxPP;
        }

        this.Speed = MaxSpeed;
        this.Attack = MaxAttack;
        this.Defense = MaxDefense;
        this.SpAttack = MaxSpAttack;
        this.SpDefense = MaxSpDefense;
        this.Health = MaxHealth;
        this.Accuracy = 1;
        this.Evasion = 1;

        this.TypeOne = oType1;
        this.TypeTwo = oType2;

    }

    public void setSpecialCondition(int index){
        if(Health > 0){
            boolean didSomething = true;
            switch (index){
                case 1:
                if(specialCondition.name.equals("Burned")){
                    g.p("But " + Name + " is already burned!");
                    didSomething = false;
                }else
                    specialCondition = new SpecialCondition("Burned",    "after",  game);
                break;

                case 2:
                if(specialCondition.name.equals("Poisoned")){
                    g.p("But " + Name + " is already poisoned!");
                    didSomething = false;
                }
                else
                if(TypeOne.name.equals("Poison") || TypeTwo.name.equals("Poison") ||
                TypeOne.name.equals("Steel") || TypeTwo.name.equals("Steel"))
                    g.p(Name + " is immune to poison!");
                else
                    specialCondition = new SpecialCondition("Poisoned",  "after",  game);
                break;

                case 3:
                if(specialCondition.name.equals("Confused")){
                    g.p("But " + Name + " is already confused!");
                    didSomething = false;
                }
                else
                    specialCondition = new SpecialCondition("Confused",  "before", game);
                break;

                case 4:
                if(specialCondition.name.equals("Paralyzed")){
                    g.p("But " + Name + " is already paralyzed!");
                    didSomething = false;
                }
                else
                    specialCondition = new SpecialCondition("Paralyzed", "before", game);
                break;

                case 5:
                if(specialCondition.name.equals("Asleep")){
                    g.p("But " + Name + " is already asleep!");
                    didSomething = false;
                }
                else
                    specialCondition = new SpecialCondition("Asleep",    "before", game);
                break;

                case 6:
                if(specialCondition.name.equals("Frozen")){
                    g.p("But " + Name + " is already burned!");
                    didSomething = false;
                }
                else
                    specialCondition = new SpecialCondition("Frozen",    "before", game);
                break;

                default:
                specialCondition = new SpecialCondition("none",      "none",   game);
                break;

            }

            if(index != 0 && didSomething){
                g.p(this.Name + " is now " + specialCondition.name.toLowerCase() + "!");
            }
        }
    }

    public void resetStatChanges(){
        this.Speed = MaxSpeed;
        this.Attack = MaxAttack;
        this.Defense = MaxDefense;
        this.SpAttack = MaxSpAttack;
        this.SpDefense = MaxSpDefense;
        this.Accuracy = 1.0;
        this.Evasion = 1.0;
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
    }

}