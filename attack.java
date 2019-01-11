
/**
 * this is the object used for attacks
 *
 * @author John Wuller
 * @version 3.0
 */
class attack{
    String name, DmgType, effect;
    public type attackType;
    int accuracy, damage, maxPP, effectChance;//, pp;
    /** The amount of damage dealt in one attack */
    int dmg;
    /** the game */
    Battle game;
    /** the generic function */
    Generic g = game.g;

    /**
     * @author John Wuller
     * 
     * The constructor
     */
    attack(Battle game, String name, String DmgType, type Type, String effect, int accuracy, int damage, int pp, int effectChance) {
        this.name = name;
        this.DmgType = DmgType;
        this.effect = effect;
        this.accuracy = accuracy;
        this.damage = damage;
        this.maxPP = pp;
        //this.pp = maxPP;
        this.effectChance = effectChance;
        this.attackType = Type;
        this.game = game;
    }

    void DoAttack(Pokemon attacker, Pokemon defender){
        DoAttack(attacker, defender, true);
    }

    /**
     * @author John Wuller
     * @version 2.0
     * 
     * @param attacker  the attacking Pokemon
     * @param defender  the Pokemon being attacked
     * 
     * Does an attack with the given Pokemon
     */
    void DoAttack(Pokemon attacker, Pokemon defender, boolean applyPP) {
        //subtract the pp
        if ((name != "Struggle") && applyPP) {
            if(attacker == game.player)
                attacker.pp[game.playerChoiceNumber] -= 1;
            else if(attacker == game.enemy)
                attacker.pp[game.enemyChoiceNumber] -= 1;
        }

        g.p(attacker.Name + " used " + name + "!");
        effectBeforeAttack(attacker, defender);
        if (damage != 0) {
            dmg = FindDmg(attacker, defender);
        } else {
            dmg = 0;
        }

        if (dmg > 0) {
            effectBeforeHurt(attacker, defender);
            g.p("Dealt " + dmg + " damage!");
            effectAfter(attacker, defender);
            defender.Health -= dmg;
            if (attackType.name.equals("Fire") && defender.specialCondition.name.equals("Frozen")) {
                g.p(defender.Name + " thawed out!");
                defender.setSpecialCondition(0);
            }
        } else if (DmgType.equals("none")) {
            if (g.randomNumber(1, 100) <= accuracy) {
                Effect(attacker, defender);
            } else {
                g.p("But it missed!");
            }
        } else if (damage > 0) {
            g.p("But it missed!");
        }

    }

    void Effect(Pokemon attacker, Pokemon defender){
        effectBeforeAttack(attacker, defender);
        effectBeforeHurt(attacker, defender);
        effectAfter(attacker, defender);
    }

    /**
     * @author John Wuller
     * @version 3.0
     * 
     * @param attacker  the attacking Pokemon
     * @param defender  the Pokemon being attacked
     * @return the damage being dealt
     * 
     * finds the amount of damage being dealt with this attack
     * after factoring in attack and defence
     * 
     */
    int FindDmg(Pokemon attacker, Pokemon defender) {
        if(name.equals("Roundhouse Kick") && attacker.Name.equals("Chuck Norris"))
            return Integer.MAX_VALUE;

        if (effect.equals("same")) {
            if (defender.TypeOne.typeCompad(this.attackType.index) * defender.TypeTwo.typeCompad(this.attackType.index) != 0) {
                return damage;
            } else {
                return 0;
            }
        } else if (g.randomNumber(1, 100) <= accuracy * attacker.Accuracy / defender.Evasion) {
            double multiplier = 1;
            if (g.randomNumber(1, 20) == 1) {
                multiplier = 1.5;
                g.p("It's a critical hit!");
            }

            multiplier = multiplier * g.randomNumber(85, 100) / 100;

            if (attackType.name.equals("Fire")) {
                if (game.weather.equals("sun")) {
                    multiplier = multiplier * 1.5;
                } else if (game.weather.equals("rain")) {
                    multiplier = multiplier / 2;
                }
            }

            if (attackType.name.equals("Water")) {
                if (game.weather.equals("sun")) {
                    multiplier = multiplier / 2;
                } else if (game.weather.equals("rain")) {
                    multiplier = multiplier * 1.5;
                }
            }

            if (attackType.equals(attacker.TypeOne)) {
                multiplier = multiplier * 1.5;
            }
            if (attackType.equals(attacker.TypeTwo)) {
                multiplier = multiplier * 1.5;
            }

            multiplier *= defender.TypeOne.typeCompad(this.attackType.index) * defender.TypeTwo.typeCompad(this.attackType.index);

            if (defender.TypeOne.typeCompad(this.attackType.index) * defender.TypeTwo.typeCompad(this.attackType.index) > 1) {
                g.p("It's super effective!");
            } else if (defender.TypeOne.typeCompad(this.attackType.index) * defender.TypeTwo.typeCompad(this.attackType.index) < 1) {
                g.p("It's not very effective…");
            } else if (defender.TypeOne.typeCompad(this.attackType.index) * defender.TypeTwo.typeCompad(this.attackType.index) == 0) {
                g.p("It doesn't have any effect…");
            }

            if (attacker.specialCondition.name.equals("Burned")) {
                multiplier /= 2;
            }

            if (DmgType.equals("phys")) {
                dmg = (int)(multiplier * (((2 * attacker.Level / 5) * damage * attacker.Attack / defender.Defense) / 50) + 2);
                return dmg;

            } else if (DmgType.equals("spec")) {
                dmg = (int)(multiplier * (((2 * attacker.Level / 5) * damage * attacker.SpAttack / defender.SpDefense) / 50) + 2);
                return dmg;

            } else {
                return -1;
            }
        } else {
            if (DmgType.equals("none")) {
                g.p("But it missed!");
            }
            return 0;
        }
    }

    /*special condition:
    √0 is none
    √1 is Burned
    √2 is Poisoned
    3 is Confused
    4 is Paralyzed
    √5 is Asleep
    √6 is Frozen
     */

    /**
     * @author John Wuller
     * @version 3.2
     * 
     * @param attacker  the attacking Pokemon
     * @param defender  the Pokemon being attacked
     * 
     * This does the effect of the attack to the defender
     */
    void effectBeforeHurt(Pokemon attacker, Pokemon defender) {
        int playerNumber;
        if(attacker == game.player)
            playerNumber = 1;
        else
            playerNumber = 2;
        if (g.randomNumber(1, 100) <= effectChance) {
            switch (effect) {
                case "wth1":
                if (dmg >= defender.Health) {
                    dmg = defender.Health - 1;
                }
                break;

                case "none":
                break;

                default:
                //g.p("ERROR - INVALID MOVE EFFECT");
                break;

            }
        }
    }

    void effectBeforeAttack(Pokemon attacker, Pokemon defender) {
        int playerNumber;
        if(attacker == game.player)
            playerNumber = 1;
        else
            playerNumber = 2;
        if (g.randomNumber(1, 100) <= effectChance) {
            switch (effect) {
                //TODO: Change
                case "noSpCond":
                attacker.setSpecialCondition(0);
                g.p(attacker.Name + " cleared his special conditions!");
                break;

                case "none":
                break;

                default:
                //g.p("ERROR - INVALID MOVE EFFECT");
                break;

            }
        }
    }

    void effectAfter(Pokemon attacker, Pokemon defender) {
        int playerNumber;
        if(attacker == game.player)
            playerNumber = 1;
        else
            playerNumber = 2;
        if (g.randomNumber(1, 100) <= effectChance) {
            switch (effect) {
                case "1":
                defender.setSpecialCondition(1);
                break;

                case "2":
                defender.setSpecialCondition(2);
                break;

                case "3":
                defender.setSpecialCondition(3);
                break;

                case "4":
                defender.setSpecialCondition(4);
                break;

                case "5":
                defender.setSpecialCondition(5);
                break;

                case "6":
                defender.setSpecialCondition(6);
                break;

                case "Spd+":
                attacker.statChange("speed", 1);
                break;

                case "Spd++":
                attacker.statChange("speed", 2);
                break;

                case "DefSpDef-":
                attacker.statChange("defense", -1);
                attacker.statChange("spdefense", -1);
                break;

                case "healHlf":
                int hd = (int)(dmg / 2);
                if (attacker.MaxHealth - attacker.Health < hd) {
                    hd = attacker.MaxHealth - attacker.Health;
                }
                attacker.Health += hd;
                g.p("Healed " + hd + " damage!");
                break;

                case "oppAtk+2,3":
                defender.statChange("attack", 2);
                defender.setSpecialCondition(3);
                break;

                case "Splsh":
                g.p("But nothing happened!");
                break;

                case "thdBck":
                attacker.Health -= dmg / 3;
                g.p(attacker.Name + " was hit with the recoil!\nDealt " + dmg / 3 + " damage!");
                break;

                case "Def+":
                attacker.statChange("defense", 1);;
                break;

                case "Recover":
                int h = attacker.MaxHealth / 2;
                if (attacker.MaxHealth - attacker.Health < h) {
                    h = attacker.MaxHealth - attacker.Health;
                }
                attacker.Health += h;
                g.p("Healed " + h + " damage!");
                break;

                case "Recharge":
                attacker.Recharge = true;
                break;

                case "-1/4Max":
                attacker.Health -= attacker.MaxHealth / 4;
                g.p(attacker.Name + " was hit with the recoil!\nDealt " + attacker.MaxHealth / 4 + " damage!");
                break;

                case "recoil 1/2":
                attacker.Health -= dmg / 2;
                g.p(attacker.Name + " was hit with the recoil!\nDealt " + dmg / 2 + " damage!");
                break;

                case "hail":
                if (!game.weather.equals("hail")) {
                    g.p("It started to hail!");
                    game.weather = "hail";
                    game.weatherTurns = 0;
                } else {
                    g.p("But it is already hailing!");
                }

                break;

                case "rain":

                if (!game.weather.equals("rain")) {
                    g.p("It started to rain!");
                    game.weather = "rain";
                    game.weatherTurns = 0;
                } else {
                    g.p("But it is already raining!");
                }

                break;

                case "sand":
                if (!game.weather.equals("sand")) {
                    g.p("A sandstorm brewed!");
                    game.weather = "sand";
                    game.weatherTurns = 0;
                } else {
                    g.p("But there was already a sandstorm!");
                }

                break;

                case "sun":
                if (!game.weather.equals("sun")) {
                    g.p("The sunlight turned harsh!");
                    game.weather = "sun";
                    game.weatherTurns = 0;
                } else {
                    g.p("But the sunlight was already harsh!");
                }

                break;

                case "Metronome":
                attack randomAttack = game.allAttacks[(int) g.randomNumber(1, game.allAttacks.length - 1)];
                g.p("Waggling a finger let it use " + randomAttack.name + "!");
                //randomAttack.pp += 1;
                randomAttack.DoAttack(attacker, defender, false);
                break;

                case "Conversion":
                attacker.TypeOne = attacker.AttackList[0].attackType;
                attacker.TypeTwo = game.none;
                g.p(attacker.Name + " transformed into the " + attacker.TypeOne.name + " type!");
                break;

                case "ReflectType":
                attacker.TypeOne = defender.TypeOne;
                attacker.TypeTwo = defender.TypeTwo;
                g.p(attacker.Name + "'s type changed to match " + defender.Name + "'s!");
                break;

                case "Soak":
                defender.TypeOne = game.Water;
                defender.TypeTwo = game.none;
                g.p(defender.Name + " transformed into the Water type!");
                break;

                case "Atk--":
                attacker.statChange("attack", -2);
                break;

                case "OSpDef-":
                defender.statChange("spdefense", -1);
                break;

                case "Def++":
                attacker.statChange("defense", 2);
                break;

                case "OSpDef--":
                defender.statChange("spdefense", -2);
                break;

                case "SpDef++":
                attacker.statChange("spdefense", 2);
                break;

                case "OAtk--":
                defender.statChange("attack", -1);
                break;

                case "Belly Drum":
                g.p(attacker.Name + " beat his belly!"); //TODO: change
                attacker.Health -= attacker.MaxHealth / 2;
                attacker.statChange("attack", 13);
                break;

                case "OSpd-":
                defender.statChange("speed", -1);
                break;

                case "ODef-":
                defender.statChange("defense", -1);
                break;

                case "SpA--":
                attacker.statChange("spattack", -2);
                break;

                case "Celebrate": //TODO: Change
                g.p("Yay!  You found an easter egg!\nThat's because I don't know what this attack says in the game! :-P");
                break;

                case "Pain split":
                int total = game.player.Health + game.enemy.Health;
                game.player.Health = total / 2;
                game.enemy.Health = total / 2;
                g.p(game.player.Name + " and " + game.enemy.Name + " shared pain!");
                break;

                case "Coil":
                attacker.statChange("attack", 1);
                attacker.statChange("defense", 1);
                attacker.statChange("accuracy", 1);
                break;

                case "OAcc-":
                defender.statChange("accuracy", -1);
                break;

                case "HoneClaws":
                attacker.statChange("attack", 1);
                attacker.statChange("accuracy", 1);
                break;

                case "OEva--":
                defender.statChange("evasion", -2);
                break;

                case "OEva-":
                defender.statChange("evasion", -2);
                break;

                case "Eva+":
                attacker.statChange("evasion", 1);
                break;

                case "KOself":
                g.p("I warned you not to use this attack!");
                attacker.Health = 0;
                break;

                case "SpASpD+":
                attacker.statChange("spattack", 1);
                attacker.statChange("spdefense", 1);
                break;

                case "Def-":
                attacker.statChange("defense", -1);
                break;

                //TODO: Change
                case "remove oStat changes":
                defender.resetStatChanges();
                g.p(defender.Name + "'s stats were reset!");
                break;

                case "SpAtk--":
                attacker.statChange("spattack", -2);
                break;

                case "Random++":
                if(attacker.statLvChange[1] + attacker.statLvChange[2] +
                attacker.statLvChange[3] + attacker.statLvChange[4] +
                attacker.statLvChange[5] + attacker.statLvChange[6] + 
                attacker.statLvChange[7] == 42)
                    g.p("But it failed!");
                else
                    switch(g.randomNumber(1, 7)){
                        case 1:
                        if(attacker.statLvChange[1] > 5)
                            effectAfter(attacker, defender);
                        else
                            attacker.statChange("attack", 2);
                        break;

                        case 2:
                        if(attacker.statLvChange[2] > 5)
                            effectAfter(attacker, defender);
                        else
                            attacker.statChange("defense", 2);
                        break;

                        case 3:
                        if(attacker.statLvChange[2] > 5)
                            effectAfter(attacker, defender);
                        else
                            attacker.statChange("spattack", 2);
                        break;

                        case 4:
                        if(attacker.statLvChange[4] > 5)
                            effectAfter(attacker, defender);
                        else
                            attacker.statChange("spdefense", 2);
                        break;

                        case 5:
                        if(attacker.statLvChange[5] > 5)
                            effectAfter(attacker, defender);
                        else
                            attacker.statChange("speed", 2);
                        break;

                        case 6:
                        if(attacker.statLvChange[6] > 5)
                            effectAfter(attacker, defender);
                        else
                            attacker.statChange("accuracy", 2);
                        break;

                        case 7:
                        if(attacker.statLvChange[7] > 5)
                            effectAfter(attacker, defender);
                        else
                            attacker.statChange("evasion", 2);
                        break;
                    }
                break;

                case "oAtkSpA-Swch":
                //TODO: Check
                /*if(game.simpleGame ||
                (playerNumber == 1 && Battle.pokemonLeft(game.p1Roster) < 2
              || playerNumber == 2 && Battle.pokemonLeft(game.p2Roster) < 2))
                    g.p("But it failed!");
                else*/{
                    defender.statChange("attack", -1);
                    defender.statChange("spattack", -1);
                }
                
                
                
                case "retreat":
                if(!game.simpleGame &&
                (playerNumber == 1 && Battle.pokemonLeft(game.p1Roster) < 1 || 
                playerNumber == 2 && Battle.pokemonLeft(game.p2Roster) < 1))
                
                    if(playerNumber == 1)
                        game.playerNewPokemon();
                    else
                        game.enemyNewPokemon();

                
                break;

                case "none":
                break;

                default:
                //g.p("ERROR - INVALID MOVE EFFECT");
                break;

            }
        }
    }

    /*@Override
    public String toString(){

    String acc = this.accuracy + "";
    if(this.accuracy > 100){
    acc = "∞";
    }

    return
    name + " (" + pp + "/" + maxPP + ")" +
    "\n     Type: " + attackType +
    "\n     Damage: " + damage + " (" + DmgType + ")" +
    "\n     Accuracy: " + acc + "%";
    }*/

    @Deprecated
    public String toString(int playerNum, int attackIndex){

        String acc = this.accuracy + "";
        if(this.accuracy > 100){
            acc = "∞";
        }
        String pp;
        if(playerNum == 1)
            pp = game.player.pp[attackIndex] + "";
        else
            pp = game.enemy.pp[attackIndex] + "";

        return
        name + " (" + pp + "/" + maxPP + ")" +
        "\n     Type: " + attackType +
        "\n     Damage: " + damage + " (" + DmgType + ")" +
        "\n     Accuracy: " + acc + "%";
    }

    public String toString(int pp){

        String acc = this.accuracy + "";
        if(this.accuracy > 100){
            acc = "∞";
        }

        return
        name + " (" + pp + "/" + maxPP + ")" +
        "\n     Type: " + attackType +
        "\n     Damage: " + damage + " (" + DmgType + ")" +
        "\n     Accuracy: " + acc + "%";
    }
}
