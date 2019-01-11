/*special condition:
0 is none
1 is Burned
2 is Poisoned
3 is Confused
4 is Paralyzed
5 is Asleep
6 is Frozen
 */

/**
 * A class for special conditions
 *
 * @author John Wuller
 * @version 1.1
 */
public class SpecialCondition
{
    /** The name of the special condition */
    String name;
    /** The number of turns that have passed */
    int turns;
    /** 
     * Whether the condition is checked before attacking or after
     * it should be either "before" or "after"
     */
    String when;

    Generic g;
    Battle game;

    /**
     * Constructor for objects of class SpecialCondition
     */
    public SpecialCondition(String name, String when, Battle game)
    {
        this.name = name;
        this.turns = 0;
        this.when = when;
        this.g = game.g;
        this.game = game;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  pokemon  the pokemon to be damaged
     * @return    the sum of x and y
     */
    public int afterTurn(Pokemon pokemon)
    {
        int damage = 0;
        switch (this.name) {
            case "Burned":
            g.p(pokemon.Name + " is hurt by his burn!");
            damage = g.randomNumber(30, 50);
            pokemon.Health -= damage;
            g.p("Dealt " + damage + " damage!");
            break;

            case "Poisoned":
            g.p(pokemon.Name + " is hurt by his poison!");
            damage = g.randomNumber(20, 40);
            pokemon.Health -= damage;
            g.p("Dealt " + damage + " damage!");
            break;
        }
        if (this.name.equals("Burned") || this.name.equals("Poisoned")) {
            this.turns++;
            if (this.turns > g.randomNumber(4, 7)) {
                g.p(pokemon.Name + " is no longer " + this.name.toLowerCase() + "!");
                pokemon.specialCondition = Battle.noSpCond;
            }
        }
        return damage;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  pokemon  the pokemon to be damaged
     * @return    the sum of x and y
     */
    public boolean beforeTurn(Pokemon pokemon, attack theAttack)
    {
        if (!(this.name.equals("Burned") || this.name.equals("Poisoned") || this.name.equals("Burned") || this.name.equals("none"))){
            this.turns++;
            if (this.turns > g.randomNumber(4, 7) ||
            (this.name.equals("Asleep") && turns > g.randomNumber(1, 3))) {
                printBreakOutName(pokemon);
                pokemon.specialCondition = Battle.noSpCond;
            }
        }

        if (pokemon.specialCondition.name.equals("Frozen") && theAttack.attackType.name.equals("Fire")){
            g.p(pokemon.Name + " thawed out!");
            return true;
        }

        if (theAttack.effect.equals("while asleep")){
            if(pokemon.specialCondition.name.equals("Asleep")) {
                return true;
            }else{
                g.p(pokemon.Name + " used " + theAttack.name + "!");
                g.p("But it failed!");
                return false;
            }
        }
        if(!pokemon.specialCondition.equals(Battle.noSpCond)){
            if (pokemon.specialCondition.name.equals("Asleep")) {
                g.p(pokemon.Name + " is fast asleep.");
                return false;
            } else if (pokemon.specialCondition.name.equals("Frozen")) {
                g.p(pokemon.Name + " is frozen solid!");
                return false;
            } else if (pokemon.specialCondition.name.equals("Confused")) {
                g.p(pokemon.Name + " is confused!");
                if (g.randomNumber(1, 3) == 3) {
                    g.p("He hurt himself in his confusion!");
                    int confDmg = new attack(game, "confused", "phys", Battle.none, "none", 100, 40, 1, 100).FindDmg(pokemon, pokemon);
                    g.p("Dealt " + confDmg + " damage!");
                    pokemon.Health -= confDmg;
                    return false;
                }

            } else if (pokemon.specialCondition.name.equals("Paralyzed")) {
                g.p(pokemon.Name + " is paralyzed!\nHe may be unable to move!");
                if (g.randomNumber(1, 2) == 2) {
                    return false;
                }
            }
        }

        return true;
    }

    public void printBreakOutName(Pokemon pokemon){
        switch (name){
            case "Burned":
            g.p(pokemon.Name + " is no longer burned!");
            break;
            
            case "Poisoned":
            g.p(pokemon.Name + " is no longer poisoned!");
            break;

            case "Confused":
            g.p(pokemon.Name + " snapped out of his confusion!");
            break;
            
            case "Paralyzed":
            g.p(pokemon.Name + " is no longer paralyzed!");
            break;
            
            case "Asleep":
            g.p(pokemon.Name + " woke up!");
            break;
            
            case "Frozen":
            g.p(pokemon.Name + " thawed out!");
            break;
            
            default:
            System.err.println("SpecialCondition.printBreakOutName error:\n"
            +"Invalid special condition");
            break;
        }
    }
}

// || (player.SpCond == 5 && player.SpCondTurnNum > g.randomNumber(1, 3))