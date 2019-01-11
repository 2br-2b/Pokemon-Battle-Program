//import javax.swing.*;

/**
 * @author John Wuller
 * @version 5.2 10/24/2018
 */
public class Battle {

    public static boolean debug = false;
    public static Battle game;
    public static int pokemonInRoster = 6;

    public static Pokemon[] p1Roster = new Pokemon[pokemonInRoster];
    public static Pokemon[] p2Roster = new Pokemon[pokemonInRoster];

    public static SpecialCondition noSpCond  = new SpecialCondition("none",      "none",   game);
    public static SpecialCondition burned    = new SpecialCondition("Burned",    "after",  game);
    public static SpecialCondition poisoned  = new SpecialCondition("Poisoned",  "after",  game);    
    public static SpecialCondition confused  = new SpecialCondition("Confused",  "before", game);
    public static SpecialCondition paralyzed = new SpecialCondition("Paralyzed", "before", game);
    public static SpecialCondition asleep    = new SpecialCondition("Asleep",    "before", game);
    public static SpecialCondition frozen    = new SpecialCondition("Frozen",    "before", game);

    public static SpecialCondition[] allSpecialConditions = {burned, poisoned, confused,
            paralyzed, asleep, frozen};

    /** true if there is more than one player, false if there is only one player*/
    public static boolean twoPlayer;
    /** amount of damage dealt this attack */
    public static int damage;
    /** the Pokemon object for p1 */
    public static Pokemon player;
    /** the Pokemon object for p2 */
    public static Pokemon enemy;

    public static String playerName = "";
    public static String enemyName = "";

    /** the weather condition */
    public static String weather;
    /** the number of turns the current weather condition has lasted */
    public static int weatherTurns;

    public static String AtkName;

    /** The generic function object */
    public static Generic g = new Generic();

    static boolean simpleGame = false;

    public static int playerChoiceNumber = -1;
    public static int enemyChoiceNumber = -1;

    private static double[] compad = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    public static type none = new type("none", -1, compad);
    private static double[] compad1 = {1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    public static type Normal = new type("Normal", 0, compad1);
    private static double[] compad2 = {1, 1, 2, 1, 1, 0.5, 0.5, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0.5, 2};
    public static type Fighting = new type("Fighting", 1, compad2);
    private static double[] compad3 = {1, 0.5, 1, 1, 0, 2, 0.5, 1, 1, 1, 1, 0.5, 2, 1, 2, 1, 1, 1};
    public static type Flying = new type("Flying", 2, compad3);
    private static double[] compad4 = {1, 0.5, 1, 0.5, 2, 1, 0.5, 1, 1, 1, 1, 0.5, 1, 2, 1, 1, 1, 0.5};
    public static type Poison = new type("Poison", 3, compad4);
    private static double[] compad5 = {1, 1, 1, 0.5, 1, 0.5, 1, 1, 1, 1, 2, 2, 0, 1, 2, 1, 1, 1};
    public static type Ground = new type("Ground", 4, compad5);
    private static double[] compad6 = {
            0.5,
            2,
            0.5,
            0.5,
            2,
            1,
            1,
            1,
            2,
            0.5,
            2,
            2,
            1,
            1,
            1,
            1,
            1,
            1
        };
    public static type Rock = new type("Rock", 5, compad6);
    private static double[] compad7 = {
            1,
            0.5,
            2,
            1,
            0.5,
            2,
            1,
            1,
            1,
            2,
            1,
            0.5,
            1,
            1,
            1,
            1,
            1,
            1
        };
    public static type Bug = new type("Bug", 6, compad7);
    private static double[] compad8 = {
            0,
            0,
            1,
            0.5,
            1,
            1,
            0.5,
            2,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            2,
            1
        };
    public static type Ghost = new type("Ghost", 7, compad8);
    private static double[] compad9 = {
            0.5,
            2,
            0.5,
            0,
            2,
            0.5,
            0.5,
            1,
            0.5,
            2,
            1,
            0.5,
            1,
            0.5,
            0.5,
            0.5,
            1,
            0.5
        };
    public static type Steel = new type("Steel", 8, compad9);
    private static double[] compad10 = {
            1,
            1,
            1,
            1,
            2,
            2,
            0.5,
            1,
            0.5,
            0.5,
            2,
            0.5,
            1,
            1,
            0.5,
            1,
            1,
            0.5
        };
    public static type Fire = new type("Fire", 9, compad10);
    private static double[] compad11 = {
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            0.5,
            0.5,
            0.5,
            2,
            2,
            1,
            0.5,
            1,
            1,
            1
        };
    public static type Water = new type("Water", 10, compad11);
    private static double[] compad12 = {
            1,
            1,
            2,
            2,
            0.5,
            1,
            2,
            1,
            1,
            2,
            0.5,
            0.5,
            0.5,
            1,
            2,
            1,
            1,
            1
        };
    public static type Grass = new type("Grass", 11, compad12);
    private static double[] compad13 = {
            1,
            1,
            0.5,
            1,
            2,
            1,
            1,
            1,
            0.5,
            1,
            1,
            1,
            0.5,
            1,
            1,
            1,
            1,
            1
        };
    public static type Electric = new type("Electric", 12, compad13);
    private static double[] compad14 = {
            1,
            0.5,
            1,
            1,
            1,
            1,
            2,
            2,
            1,
            1,
            1,
            1,
            1,
            0.5,
            1,
            1,
            2,
            1
        };
    public static type Psychic = new type("Psychic", 13, compad14);
    private static double[] compad15 = {
            1,
            2,
            1,
            1,
            1,
            2,
            1,
            1,
            2,
            2,
            1,
            1,
            1,
            1,
            0.5,
            1,
            1,
            1
        };
    public static type Ice = new type("Ice", 14, compad15);
    private static double[] compad16 = {
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            0.5,
            0.5,
            0.5,
            0.5,
            1,
            2,
            2,
            1,
            2
        };
    public static type Dragon = new type("Dragon", 15, compad16);
    private static double[] compad17 = {
            1,
            2,
            1,
            1,
            1,
            1,
            2,
            0.5,
            1,
            1,
            1,
            1,
            1,
            0,
            1,
            1,
            0.5,
            2
        };
    public static type Dark = new type("Dark", 16, compad17);
    private static double[] compad18 = {
            1,
            0.5,
            1,
            2,
            1,
            1,
            0.5,
            1,
            2,
            1,
            1,
            1,
            1,
            1,
            1,
            0,
            0.5,
            1
        };
    public static type Fairy = new type("Fairy", 17, compad18);

    //initialize the variables 
    /**
     * @author John Wuller
     * @version 2.0
     * 
     * This function initializes the game variables
     * 
     */
    public static void initialize() {
        //place methods initialization inside other methods
        twoPlayer = twoPlay();
        debug = false;

        if (!twoPlayer) {
            playerName = g.ask("What is your name?");
            enemyName = g.ask("What is your opponent's name?");
        } else {
            playerName = g.ask("What is p1's name?");
            enemyName  = g.ask("What is p2's name?");
        }

        simpleGame = g.askYN("Would you like a simple game?", "Simple game?");
        if(!simpleGame)
            pokemonInRoster = Integer.parseInt(g.ask("How many Pokemon do you want in each roster?"));

        boolean debugPkmn = false;
        if (playerName.equals("debug")) {
            debug();
            debugPkmn = g.askYN("Would you like to use the debugger Pokemon?",
                "Custom Pokemon");
            if(!debugPkmn && simpleGame)
                playerName = g.ask("What is your Pokemon's name?");
        }

        if(!simpleGame){
            if(playerName.equals(""))
                playerName = "John";

            if(enemyName.equals(""))
                enemyName = "The Computer";

            if(twoPlayer)
                g.pNo(playerName + ", press OK");
            p1Roster = makePokemonRoster();

            if(twoPlayer){

                g.pNo(enemyName + ", press OK");
                p2Roster = makePokemonRoster();
            }else{

                g.pNo("Now, you can enter your opponent's names.");
                p2Roster = makePokemonRoster();
            }

            if(debugPkmn)
                p1Roster[0] = makePokemon("debug");
            player = p1Roster[0];
            enemy = p2Roster[0];

        }else{
            player = makePokemon(playerName);
            enemy = makePokemon(enemyName);

            playerName = player.Name;
            enemyName = enemy.Name;
        }

        if (twoPlayer) {
            g.p(playerName + ", press OK");
        }

        /*g.p(player.Name+"'s stats:\n\nLevel: "+player.Level+"\nAttack: "+player.Attack+"\nDefense: "+player.Defense+"\nSpecial Attack: "+player.SpAttack+"\nSpecial Defense: "+player.SpDefense+
        "\nSpeed: "+player.Speed+"\nHealth: "+player.Health+"\n\nType 1: "+player.TypeOne.name+"\nType 2: "+player.TypeTwo.name +"\n\nAttack 1: "+player.AttackList[0].name+
        "\nAttack 2: "+player.AttackList[1].name+"\nAttack 3: "+player.AttackList[2].name+"\nAttack 4: "+player.AttackList[3].name);*/

        if(simpleGame)
            g.viewStats(player);
        else
            showMultiplePokemon(p1Roster);

        if (twoPlayer) {
            g.p(enemyName + ", press OK");
            if(simpleGame)
                g.viewStats(enemy);
            else
                showMultiplePokemon(p2Roster);
        }

        if (debug) {
            if(simpleGame)
                g.viewStats(enemy);
            else
                showMultiplePokemon(p2Roster);
        }

        weather = "clear";
        weatherTurns = 0;

    }

    /**
     * This attack comes up whenever you have no PP remaining for any
     * of your attacks
     */
    public static attack Struggle = new attack(game, "Struggle", "phys", Normal, "-1/4Max", 10000, 50, 1, 100);

    /*special conditions are as follows:
    0 is none
    1 is Burned
    2 is Poisoned
    3 is Confused
    4 is Paralyzed
    5 is Asleep
    6 is Frozen
     */
    //            name, phys/spec/none, type, effect, acc, dam, pp, effectChance
    public static attack DynamicPunch = new attack(game, "Dynamic Punch", "phys", Fighting, "3", 50, 100, 5, 100);
    public static attack Surf = new attack(game, "Surf", "phys", Water, "none", 95, 100, 10, 0);
    public static attack CloseCombat = new attack(game, "Close Combat", "phys", Fighting, "DefSpDef-", 85, 120, 5, 100);
    public static attack Blizzard = new attack(game, "Blizzard", "spec", Ice, "6", 70, 120, 5, 10);
    public static attack Swift = new attack(game, "Swift", "spec", Normal, "none", 10000, 60, 15, 0);
    public static attack Incinerate = new attack(game, "Incinerate", "spec", Fire, "none", 100, 60, 20, 0);
    public static attack Swagger = new attack(game, "Swagger", "none", Normal, "oppAtk+2,3", 100, 0, 20, 100);
    public static attack FlameCharge = new attack(game, "Flame Charge", "phys", Fire, "Spd+", 100, 60, 15, 100);
    public static attack FalseSwipe = new attack(game, "False Swipe", "phys", Normal, "wth1", 100, 40, 20, 100);
    public static attack Inferno = new attack(game, "Inferno", "phys", Fire, "1", 50, 100, 5, 100);
    public static attack Tackle = new attack(game, "Tackle", "phys", Normal, "none", 95, 30, 30, 0);
    public static attack DragonRage = new attack(game, "Dragon Rage", "phys", Dragon, "same", 10000, 40, 10, 0);
    public static attack Thunder = new attack(game, "Thunder", "phys", Electric, "4", 50, 100, 5, 30);
    public static attack FireBlast = new attack(game, "Fire Blast", "phys", Fire, "1", 85, 120, 5, 30);
    public static attack SuperSonic = new attack(game, "SuperSonic", "none", Normal, "3", 55, 0, 30, 100);
    public static attack StunSpore = new attack(game, "Stun Spore", "none", Grass, "4", 100, 0, 30, 100);
    public static attack Agility = new attack(game, "Agility", "none", Normal, "Spd++", 100, 0, 30, 100);
    public static attack AerialAce = new attack(game, "Aerial Ace", "phys", Flying, "none", 10000, 60, 15, 0);
    public static attack Splash = new attack(game, "Splash", "none", Normal, "Splsh", 100, 0, 40, 100);
    public static attack Spore = new attack(game, "Spore", "none", Grass, "5", 100, 0, 20, 100);
    public static attack Ember = new attack(game, "Ember", "spec", Fire, "1", 100, 40, 25, 10);
    public static attack BraveBird = new attack(game, "Brave Bird", "phys", Flying, "thdBck", 95, 120, 10, 100);
    public static attack Bite = new attack(game, "Bite", "phys", Dark, "none", 100, 30, 40, 0);
    public static attack Scratch = new attack(game, "Scratch", "phys", Normal, "none", 100, 30, 40, 0);
    public static attack RazorLeaf = new attack(game, "Razor Leaf", "phys", Grass, "none", 95, 60, 15, 0);
    public static attack MagicalLeaf = new attack(game, "Magical Leaf", "phys", Grass, "none", 10000, 100, 10, 0);
    public static attack MegaDrain = new attack(game, "Mega Drain", "spec", Grass, "healHlf", 100, 60, 20, 100);
    public static attack GigaDrain = new attack(game, "Giga Drain", "spec", Grass, "healHlf", 100, 100, 10, 100);
    public static attack DefenseCurl = new attack(game, "Defense Curl", "none", Normal, "Def+", 100, 0, 20, 100);
    public static attack PoisonPowder = new attack(game, "PoisonPowder", "none", Poison, "2", 100, 0, 10, 100);
    public static attack Headbut = new attack(game, "Headbut", "phys", Normal, "0", 95, 40, 40, 0);
    public static attack Absorb = new attack(game, "Absorb", "spec", Grass, "healHlf", 100, 20, 25, 100);
    public static attack EggBomb = new attack(game, "Egg Bomb", "spec", Normal, "none", 75, 100, 10, 0);
    public static attack GunkShot = new attack(game, "Gunk Shot", "phys", Poison, "2", 70, 120, 5, 30);
    public static attack BlastBurn = new attack(game, "Blast Burn", "spec", Fire, "Recharge", 95, 150, 5, 100);
    public static attack HyperBeam = new attack(game, "Hyper Beam", "spec", Normal, "Recharge", 95, 150, 5, 100);
    public static attack FrenzyPlant = new attack(game, "Frenzy Plant", "spec", Grass, "Recharge", 95, 150, 5, 100);
    public static attack HydroCannon = new attack(game, "Hydro Cannon", "spec", Water, "Recharge", 95, 150, 5, 100);
    public static attack HydroPump = new attack(game, "Hydro Pump", "spec", Water, "none", 95, 120, 10, 0);
    public static attack Recover = new attack(game, "Recover", "none", Normal, "Recover", 100, 0, 5, 100);
    public static attack RoarOfTime = new attack(game, "Roar of Time", "spec", Dragon, "Recharge", 95, 120, 5, 100);
    public static attack GrassWhistle = new attack(game, "Grass Whistle", "none", Grass, "5", 55, 0, 10, 100);

    //2.0
    //            name, phys/spec/none, type, effect, acc, dam, pp, effectChance
    //Moves with 10000 Accuracy are attacks that never miss
    public static attack FairyWind = new attack(game, "Fairy Wind", "spec", Fairy, "none", 100, 40, 30, 100);
    //public static attack AquaRing = new attack(game, "Aqua Ring", "phys", Water, "none", 90, 90, 10, 100);
    public static attack BlueFlare = new attack(game, "Blue Flare", "spec", Fire, "1", 85, 130, 5, 20);
    public static attack DisarmingVoice = new attack(game, "Disarming Voice", "spec", Fairy, "none", 10000, 40, 15, 100);
    public static attack DragonAscent = new attack(game, "Dragon Ascent", "phys", Flying, "DefSpDef-", 100, 120, 5, 100);
    public static attack DragonBreath = new attack(game, "Dragon Breath", "spec", Dragon, "4", 100, 60, 20, 30);
    public static attack DrainPunch = new attack(game, "Drain Punch", "phys", Fighting, "healHlf", 100, 75, 10, 100);
    public static attack DrainingKiss = new attack(game, "Draining Kiss", "spec", Fairy, "healHlf", 100, 50, 10, 100);
    public static attack ForcePalm = new attack(game, "Force Palm", "phys", Fighting, "4", 100, 60, 10, 30);
    public static attack Glare = new attack(game, "Glare", "none", Normal, "4", 100, 0, 30, 100);
    public static attack Harden = new attack(game, "Harden", "none", Normal, "Def+", 100, 0, 30, 100);
    public static attack HealOrder = new attack(game, "Heal Order", "none", Bug, "Recover", 100, 0, 10, 100);
    public static attack HighHorsepower = new attack(game, "High Horsepower", "phys", Ground, "none", 95, 95, 10, 100);
    public static attack HoldBack = new attack(game, "Hold Back", "phys", Normal, "wth1", 100, 40, 40, 100);
    public static attack HornLeech = new attack(game, "Horn Leech", "phys", Grass, "healHlf", 100, 75, 10, 100);
    public static attack Hurricane = new attack(game, "Hurricane", "spec", Flying, "3", 70, 110, 10, 30);
    public static attack LeechLife = new attack(game, "Leech Life", "phys", Bug, "healHlf", 100, 80, 10, 100);
    public static attack Lick = new attack(game, "Lick", "phys", Ghost, "4", 100, 30, 30, 30);
    public static attack MindBlown = new attack(game, "Mind Blown", "spec", Fire, "recoil 1/2", 100, 150, 5, 100);
    public static attack PowderSnow = new attack(game, "Powder Snow", "spec", Ice, "6", 100, 40, 35, 10);
    public static attack PrismaticLaser = new attack(game, "Prismatic Laser", "spec", Psychic, "Recharge", 100, 160, 10, 100);
    public static attack RockThrow = new attack(game, "Rock Throw", "phys", Rock, "none", 90, 50, 10, 100);
    public static attack RockWrecker = new attack(game, "Rock Wrecker", "phys", Rock, "Recharge", 90, 150, 5, 100);
    public static attack SlackOff = new attack(game, "Slack Off", "none", Normal, "Recover", 100, 0, 10, 100);
    public static attack SteamEruption = new attack(game, "Steam Eruption", "spec", Water, "1", 95, 110, 5, 30);
    public static attack SweetKiss = new attack(game, "Sweet Kiss", "none", Fairy, "3", 75, 0, 10, 100);
    public static attack ZapCannon = new attack(game, "Zap Cannon", "spec", Electric, "4", 50, 120, 5, 100);
    public static attack AquaTail = new attack(game, "Aqua Tail", "phys", Water, "none", 90, 90, 10, 100);

    //2.1
    public static attack RainDance = new attack(game, "Rain Dance", "none", Water, "rain", 100, 0, 5, 100);
    public static attack SunnyDay = new attack(game, "Sunny Day", "none", Fire, "sun", 100, 0, 5, 100);
    public static attack Sandstorm = new attack(game, "Sandstorm", "none", Rock, "sand", 100, 0, 10, 100);
    public static attack Hail = new attack(game, "Hail", "none", Ice, "hail", 100, 0, 10, 100);
    public static attack ExtremeSpeed = new attack(game, "Extreme Speed", "phys", Normal, "Go p2", 100, 80, 5, 100);
    public static attack Accelerock = new attack(game, "Accelerock", "phys", Rock, "Go p1", 100, 40, 20, 100);
    public static attack AquaJet = new attack(game, "Aqua Jet", "phys", Water, "Go p1", 100, 40, 20, 100);
    public static attack BulletPunch = new attack(game, "Bullet Punch", "phys", Steel, "Go p1", 100, 40, 30, 100);
    public static attack IceShard = new attack(game, "Ice Shard", "phys", Ice, "Go p1", 100, 40, 30, 100);
    public static attack MachPunch = new attack(game, "Mach Punch", "phys", Fighting, "Go p1", 100, 40, 30, 100);
    public static attack QuickAttack = new attack(game, "Quick Attack", "phys", Normal, "Go p1", 100, 40, 30, 100);
    public static attack ShadowSneak = new attack(game, "Shadow Sneak", "phys", Ghost, "Go p1", 100, 40, 30, 100);
    public static attack VacuumWave = new attack(game, "Vacuum Wave", "spec", Fighting, "Go p1", 100, 40, 30, 100);
    public static attack VitalThrow = new attack(game, "Vital Throw", "phys", Fighting, "Go p-1", 10000, 70, 10, 100);

    //2.2
    public static attack MagnetBomb = new attack(game, "Magnet Bomb", "phys", Steel, "none", 100, 60, 20, 100);
    public static attack SonicBoom = new attack(game, "Sonic Boom", "spec", Normal, "same", 90, 20, 20, 100);

    //2.3
    public static attack Conversion = new attack(game, "Conversion", "none", Normal, "Conversion", 100, 0, 30, 100);
    public static attack ReflectType = new attack(game, "Reflect Type", "none", Normal, "ReflectType", 10000, 0, 15, 100);
    public static attack Soak = new attack(game, "Soak", "none", Water, "Soak", 100, 0, 20, 100);

    //3.0
    public static attack noAttack = new attack(game, "none", "none", none, "none", 0, 0, 0, 0);

    //3.1
    public static attack Metronome = new attack(game, "Metronome", "none", Normal, "Metronome", 100, 0, 10, 100);
    public static attack Snore = new attack(game, "Snore", "spec", Normal, "while asleep", 100, 50, 15, 100);

    //3.2
    public static attack DracoMeteor = new attack(game, "Draco Meteor", "spec", Dragon, "SpA--", 90, 140, 5, 100);
    public static attack Acid = new attack(game, "Acid", "spec", Poison, "OSpDef-", 100, 40, 30, 10);
    public static attack AcidArmor = new attack(game, "Acid Armor", "none", Poison, "Def++", 100, 0, 20, 100);
    public static attack AcidSpray = new attack(game, "Acid Spray", "spec", Poison, "OSpDef--", 100, 40, 20, 100);
    public static attack Amnesia = new attack(game, "Amnesia", "none", Psychic, "SpDef++", 100, 0, 20, 100);
    public static attack AuraSphere = new attack(game, "Aura Sphere", "spec", Fighting, "none", 10000, 80, 20, 100);
    public static attack AuroraBeam = new attack(game, "Aurora Beam", "spec", Ice, "OAtk-", 100, 65, 20, 10);
    public static attack BellyDrum = new attack(game, "Belly Drum", "none", Normal, "Belly Drum", 100, 0, 10, 100);
    public static attack BodySlam = new attack(game, "Body Slam", "phys", Normal, "4", 10000, 85, 15, 20);
    public static attack Bulldoze = new attack(game, "Bulldoze", "phys", Ground, "OSpd-", 100, 60, 20, 100);
    public static attack Celebrate = new attack(game, "Celebrate", "none", Normal, "Celebrate", 100, 0, 40, 100);
    public static attack CoreEnforcer = new attack(game, "Core Enforcer", "spec", Dragon, "none", 100, 100, 10, 100);
    public static attack DizzyPunch = new attack(game, "Dizzy Punch", "phys", Normal, "3", 100, 70, 10, 20);
    public static attack FireLash = new attack(game, "Fire Lash", "phys", Fire, "ODef-", 100, 80, 15, 100);
    public static attack FleurCannon = new attack(game, "Fleur Cannon", "spec", Fairy, "SpA--", 90, 130, 5, 100);

    //3.3
    public static attack FeintAttack = new attack(game, "Feint Attack", "phys", Dark, "none", 10000, 60, 20, 100);
    public static attack PainSplit = new attack(game, "Pain Split", "none", Normal, "Pain split", 10000, 0, 20, 100);
    public static attack SmartStrike = new attack(game, "Smart Strike", "phys", Steel, "none", 10000, 70, 10, 100);
    public static attack SoftBoiled = new attack(game, "Soft-Boiled", "none", Normal, "Recover", 100, 0, 10, 100);
    public static attack Smokescreen = new attack(game, "Smokescreen", "none", Normal, "OAcc-", 100, 0, 20, 100);
    public static attack LeafTornado = new attack(game, "Leaf Tornado", "spec", Grass, "OAcc-", 90, 65, 10, 50);
    public static attack MirrorShot = new attack(game, "Mirror Shot", "spec", Steel, "OAcc-", 85, 65, 10, 30);
    public static attack Octazooka = new attack(game, "Octazooka", "spec", Water, "OAcc-", 85, 65, 10, 50);
    public static attack Flash = new attack(game, "Flash", "none", Normal, "OAcc-", 100, 0, 20, 100);
    public static attack HoneClaws = new attack(game, "Hone Claws", "none", Dark, "HoneClaws", 100, 0, 15, 100);
    public static attack SweetScent = new attack(game, "Sweet Scent", "none", Normal, "OEva--", 100, 0, 20, 100);
    public static attack DoubleTeam = new attack(game, "Double Team", "none", Normal, "Eva+", 100, 0, 15, 100);

    //3.4
    public static attack viewStats = new attack(game, "View Stats", "none", none, "none", 0, 0, 0, 0);
    public static attack DoNotUse = new attack(game, "DO NOT USE THIS ATTACK!!", "none", none, "KOself", 100, 0, 1, 100);

    //4.2
    public static attack calmMind = new attack(game, "Calm Mind", "none", Psychic, "SpASpD+", 100, 0, 20, 100);
    public static attack aromatherapy = new attack(game, "Aromatherapy", "none", Grass, "noSpCond", 100, 0, 5, 100);
    public static attack charm = new attack(game, "Charm", "none", Fairy, "OAtk--", 100, 5, 20, 100);
    public static attack clangingScales = new attack(game, "Clanging Scales", "spec", Dragon, "Def-", 100, 110, 5, 100);
    public static attack coil = new attack(game, "Coil", "none", Poison, "Coil", 100, 0, 20, 100);

    //5.0
    public static attack switchOut = new attack(game, "Switch Out", "none", none, "none", 0, 0, 1, 0);
    public static attack recharge = new attack(game, "Recharge", "none", none, "none", 0, 0, 1, 0);

    //5.1
    public static attack chuckNorrisRoundhouseKick = new attack(game, "Roundhouse Kick", "phys", none, "Go p5", Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    public static attack accupressure = new attack(game, "Accupressure", "none", Normal, "Random++", 100, 0, 30, 100);
    //public static attack partingShot = new attack(game, "Parting Shot", "none", Dark, "oAtkSpA-Swch", 100, 0, 20, 100);
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /** 
     * this is a list of all of the attacks in the game.
     * it is used when some function needs a random attack.
     */
    public static attack[] allAttacks = {
            DynamicPunch, Surf, CloseCombat, //Blizzard, (100% accuracy in hail)
            Swift, Incinerate, Swagger, FlameCharge, //FalseSwipe, (this isn't working for some reason)
            Inferno, Tackle, DragonRage, //Thunder, (100% accuracy in rain)
            FireBlast, SuperSonic, StunSpore, Agility, AerialAce, Splash,
            //Spore, (Spore is too OP)
            Ember, BraveBird, Bite, Scratch, RazorLeaf, MagicalLeaf, MegaDrain,
            GigaDrain, DefenseCurl, PoisonPowder, Headbut, Absorb, EggBomb,
            GunkShot, BlastBurn, HyperBeam, FrenzyPlant, HydroCannon, HydroPump,
            RoarOfTime, GrassWhistle, Recover,

            FairyWind, //AquaRing, (has a completely different effect then the one I put)
            BlueFlare, //DisarmingVoice, (hits multiple targets)
            DragonAscent,
            DragonBreath,
            DrainPunch,
            DrainingKiss,
            FlameCharge,
            ForcePalm,
            Glare,
            Harden,
            HealOrder,
            HighHorsepower,
            //HoldBack, (this isn't working for some reason)
            HornLeech,
            //Hurricane, (100% accuracy in rain)
            Inferno,
            LeechLife,
            Lick,
            MindBlown,
            PowderSnow,
            PrismaticLaser,
            RockThrow,
            RockWrecker,
            SlackOff,
            SteamEruption,
            SweetKiss,
            ZapCannon,
            AquaTail,

            RainDance,
            SunnyDay,
            Sandstorm,
            Hail,
            ExtremeSpeed,
            Accelerock,
            AquaJet,
            BulletPunch,
            IceShard,
            MachPunch,
            QuickAttack,
            ShadowSneak,
            VacuumWave,
            VitalThrow,

            MagnetBomb,
            SonicBoom,

            Conversion,
            ReflectType,
            Soak,

            DracoMeteor,
            Acid,
            AcidArmor,
            AcidSpray,
            Amnesia,// (working on it)
            AuraSphere,
            AuroraBeam,
            BellyDrum,
            BodySlam,
            Bulldoze,
            Celebrate,
            CoreEnforcer,
            DizzyPunch,
            FireLash,
            FleurCannon,

            FeintAttack,
            PainSplit,
            SmartStrike,
            SoftBoiled,
            Smokescreen,
            LeafTornado,
            MirrorShot,
            Octazooka,
            Flash,
            HoneClaws,
            SweetScent,
            DoubleTeam,

            calmMind,
            aromatherapy,
            charm,
            clangingScales,
            coil,

            accupressure//, partingShot
        };
    /** 
     * this is a list of all of the types in the game.
     * it is used when some function needs a random type.
     */
    public static type[] types = {
            Normal,
            Fire,
            Water,
            Electric,
            Grass,
            Ice,
            Fighting,
            Poison,
            Ground,
            Flying,
            Psychic,
            Bug,
            Rock,
            Ghost,
            Dragon,
            Dark,
            Steel,
            Fairy,
            none
        };

    /**
     * @author John Wuller
     * @version 2.0
     * 
     * This determines which player attacks first, then
     * checks if they can attack
     * 
     * @param player  p1's Pokemon
     * @param playerAttack  p1's attack
     * @param enemy  p2's Pokemon
     * @param enemyAttack  p2's attack
     * 
     */
    public static void attack(Pokemon player, attack playerAttack, Pokemon enemy, attack enemyAttack) {
        //These variables are multipliers for the speed values; eg, if paralyzed, etc.
        //If the attack goes first, the multiplier will be *100^priority
        //If it goes last, it will be *0.01^priority
        //for more info, go to https://bulbapedia.bulbagarden.net/wiki/Priority

        /** The player speed multiplier */
        double psm = 1;
        /** The enemy speed multiplier */
        double esm = 1;

        if (playerAttack.effect.equals("Go p1")) {
            psm *= 100;
        }

        if (enemyAttack.effect.equals("Go p1")) {
            esm *= 100;
        }

        if (playerAttack.effect.equals("Go p2")) {
            psm *= 10000;
        }

        if (enemyAttack.effect.equals("Go p2")) {
            esm *= 10000;
        }

        if (playerAttack.effect.equals("Go p3")) {
            psm *= 1000000;
        }

        if (enemyAttack.effect.equals("Go p3")) {
            esm *= 1000000;
        }

        if (playerAttack.effect.equals("Go p4")) {
            psm *= 100000000;
        }

        if (enemyAttack.effect.equals("Go p4")) {
            esm *= 100000000;
        }

        if (playerAttack.effect.equals("Go p5")) {
            psm *= 1000000000;
        }

        if (enemyAttack.effect.equals("Go p5")) {
            esm *= 1000000000;
        }

        if (playerAttack.effect.equals("Go p-1")) {
            psm *= 0.01;
        }

        if (enemyAttack.effect.equals("Go p-1")) {
            esm *= 0.01;
        }

        if (playerAttack.effect.equals("Go p-2")) {
            psm *= 0.0001;
        }

        if (enemyAttack.effect.equals("Go p-2")) {
            esm *= 0.0001;
        }

        if (playerAttack.effect.equals("Go p-3")) {
            psm *= 0.000001;
        }

        if (enemyAttack.effect.equals("Go p-3")) {
            esm *= 0.000001;
        }

        if (playerAttack.effect.equals("Go p-4")) {
            psm *= 0.00000001;
        }

        if (enemyAttack.effect.equals("Go p-4")) {
            esm *= 0.00000001;
        }

        if (playerAttack.effect.equals("Go p-5")) {
            psm *= 0.0000000001;
        }

        if (enemyAttack.effect.equals("Go p-5")) {
            esm *= 0.0000000001;
        }

        if (player.specialCondition.name.equals("Paralyzed")) {
            psm /= 2;
        }

        if (enemy.specialCondition.name.equals("Paralyzed")) {
            esm /= 2;
        }

        //p1 goes first
        if ((player.Speed * psm > enemy.Speed * esm) || playerAttack.equals(switchOut)) {
            doAtk(playerAttack, player, enemy);
            if (continueItQ()) {
                doAtk(enemyAttack, enemy, player);
            }
        } else {

            //p2 goes first
            doAtk(enemyAttack, enemy, player);
            if (continueItQ()) {
                doAtk(playerAttack, player, enemy);
            }
        }
    }

    /**
     * @author John Wuller
     * @version 3.0
     * 
     * This function checks if the attacking Pokemon can attack,
     * then attacks if they can
     * 
     * @param attack  the attack
     * @param attacker  the attacking Pokemon
     * @param defender  the defending Pokemon
     * 
     */
    public static void doAtk(attack attack, Pokemon attacker, Pokemon defender) {
        if(attack.equals(switchOut)){
            g.p(playerName + " swiched out to "+attacker.Name+"!");
        }else if(attack.equals(switchOut)){
            g.p(enemyName + " switched out to "+attacker.Name+"!");
        }else{

            if (!attacker.Recharge){
                if(attacker.specialCondition.beforeTurn(attacker, attack))
                    attack.DoAttack(attacker, defender);
            } else {
                g.p(attacker.Name + " must recharge!");
                attacker.Recharge = false;
            }
        }
        overhealthCheck();
    }

    public static String[] stgle;
    /**
     * @author John Wuller
     * @version 3.1
     * 
     * this runs the program, asks what attack to use,
     * then stops when told to
     */
    public static void runProgram(Battle gameGiven) {

        game = gameGiven;

        while (true) {
            //initialize variables when you enter the main program
            initialize();
            if (!twoPlayer) {
                g.p("You are challenged by " + enemyName + "!");
                if(!simpleGame)
                    g.p(enemyName + " sent out " + enemy.Name + "!");
                g.p("Go, " + player.Name + "!");
            }else{
                g.p(playerName + " sent out " + player.Name + "!");
                g.p(enemyName + " sent out " + enemy.Name + "!");
            }

            do {
                if (twoPlayer) {
                    g.pNo(playerName + ", press OK");
                }

                attack playerAttack = askForAttack(player, p1Roster);

                if (playerAttack.equals(switchOut))
                    playerNewPokemon();

                attack enemyAttack;

                if (twoPlayer) {
                    g.pNo(enemyName + ", press OK");
                    enemyAttack = askForAttack(enemy, p2Roster);

                    if (enemyAttack.equals(switchOut))
                        enemyNewPokemon();

                } else {
                    enemyAttack = computerChooseAttack();
                }
                attack(player, playerAttack, enemy, enemyAttack);

                if (enemy.Health < 1 && player.Health < 1) {
                    g.p("Both Pokemon fainted!");
                }else if (player.Health < 1) {
                    g.p(player.Name + " fainted!");
                } else if (enemy.Health < 1) {
                    g.p(enemy.Name + " fainted!");
                }

                if (continueItQAll()) {
                    if (player.Health > 0)
                        player.specialCondition.afterTurn(player);
                    if (enemy.Health > 0)
                        enemy.specialCondition.afterTurn(enemy);
                }

                if(continueItQAll())
                    checkWeather();

                overhealthCheck();

                if (continueItQ()) {
                    if (!twoPlayer) {
                        g.p("Your Health: " + player.Health + " of " + player.MaxHealth
                            +"\n"+hPdisplay(player));
                        //g.p("Damage dealt to " + enemy.Name + ": " + (enemy.MaxHealth - enemy.Health));
                        if(debug)
                            g.p("Enemy Health: " + enemy.Health + " of " + enemy.MaxHealth
                                +"\n"+hPdisplay(enemy));
                        else
                            g.p("Enemy Health: \n" + hPdisplay(enemy));
                    } else {
                        g.p(player.Name + "'s Health: "
                            +"\n"+hPdisplay(player));
                        g.p(enemy.Name + "'s Health: "
                            +"\n"+hPdisplay(enemy));
                    }
                }

                g.endTurn();

                if (enemy.Health < 1 && player.Health < 1) {
                    playerNewPokemon();
                    enemyNewPokemon();
                }else if (player.Health < 1) {
                    playerNewPokemon();
                } else if (enemy.Health < 1) {
                    enemyNewPokemon();
                }

            } while (continueItQAll());
            boolean replayAsk = g.askYN("Would you like save the replay?", "Save replay?");
            if (replayAsk) {
                g.saveReplay(game);
            }

            boolean continueIt = g.askYN("Would you like to play again?", "Remach?");

            if (!continueIt) {
                System.exit(0);
            }
        }
    }

    /**
     * @author John Wuller
     * @version 1.0
     * 
     * This asks and finds out how many players are playing
     * 
     */
    public static boolean twoPlay() {
        String[] lister = {
                "1",
                "2"
            };
        Object players = g.askForOption("How many players?", "Number of Players",
                lister);
        if (players.equals(lister[0])) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @author John Wuller
     * @version 1.0
     * 
     * @return  if the program should end the game
     */
    public static boolean continueItQ() {
        return enemy.Health > 0 && player.Health > 0;
    }

    /**
     * @author John Wuller
     * @version 1.0
     * 
     * @return  if the program should end the game
     */
    public static boolean continueItQAll() {
        return playerIn() && enemyIn();
    }

    public static boolean playerIn() {
        if(!simpleGame){
            if(pokemonLeft(p1Roster) > 0)
                return true;
        }else if(player.Health > 0)
            return true;

        return false;
    }

    public static boolean enemyIn() {
        if(!simpleGame){
            if(pokemonLeft(p2Roster) > 0)
                return true;
        }else if(enemy.Health > 0)
            return true;

        return false;
    }

    /**
     * @author John Wuller
     * @version 1.0
     * 
     * makes sure that neither player has more health then they should.
     */
    public static void overhealthCheck() {
        if (player.Health > player.MaxHealth) {
            player.Health = player.MaxHealth;
        }
        if (enemy.Health > enemy.MaxHealth) {
            enemy.Health = enemy.MaxHealth;
        }

        if (player.Health < 0) {
            player.Health = 0;
        }
        if (enemy.Health < 0) {
            enemy.Health = 0;
        }
    }

    /**
     * @author John Wuller
     * @version 1.1
     * 
     * @return the attack the computer will use
     * 
     * This method chooses a random attack for the computer
     */
    public static attack computerChooseAttack() {
        if (enemy.pp[0] + enemy.pp[1] +
        enemy.pp[2] + enemy.pp[3] < 1) {
            return Struggle;
        }

        enemyChoiceNumber = g.randomNumber(0, 3);

        attack Tp2choice = enemy.AttackList[enemyChoiceNumber];
        if (enemy.pp[enemyChoiceNumber] < 1) {
            return computerChooseAttack();
        }

        return Tp2choice;

    }

    /**
     * @author John Wuller
     * @version 1.0
     * 
     * @return the random attack
     * 
     * this method returns a random attack from the list of attacks
     */
    public static attack getRandomAttack() {
        return allAttacks[(int) g.randomNumber(0, allAttacks.length - 1)];
    }

    /**
     * @author John Wuller
     * @version 1.0
     * 
     * This is called whenever the user's name is "debug".  It lets
     * you change different game values.  Right now, it:
     *      prints out the number of attacks
     */
    public static void debug() {
        int minLv = Integer.parseInt(g.ask("What is the minimum level?"));
        int maxLv = Integer.parseInt(g.ask("What is the maximum level?"));
        player.minLv = minLv;
        player.maxLv = maxLv;
        enemy.minLv = minLv;
        enemy.maxLv = maxLv;
        g.p(allAttacks.length + " attacks");
        debug = true;
    }

    public static void main(String[] args){
        runProgram(game);
    }

    private static void checkWeather(){

        if (weather != "clear") {
            weatherTurns += 1;
            switch (weather) {
                case "sun":
                if (weatherTurns <= 5) {
                    g.p("The sunlight is strong.");
                } else {
                    g.p("The sunlight faded.");
                    weather = "clear";
                    weatherTurns = 0;
                }
                break;

                case "rain":
                if (weatherTurns <= 5) {
                    g.p("Rain continues to fall.");
                } else {
                    g.p("The rain stopped.");
                    weather = "clear";
                    weatherTurns = 0;
                }
                break;

                case "sand":
                if (weatherTurns <= 5) {
                    g.p("The sandstorm rages.");

                    if (player.Health > 0 && !(player.TypeOne.name.equals("Rock") || player.TypeOne.name.equals("Steel") || player.TypeOne.name.equals("Rock") ||
                        player.TypeTwo.name.equals("Rock") || player.TypeTwo.name.equals("Steel") || player.TypeTwo.name.equals("Rock"))) {

                        g.p(player.Name + " is buffeted by the sandstorm!");

                        player.Health -= (int)(player.MaxHealth / 16);
                        g.p("Dealt " + (int)(player.MaxHealth / 16) + " damage!");

                    }
                    if (enemy.Health > 1 && !(enemy.TypeOne.name.equals("Rock") || enemy.TypeOne.name.equals("Steel") || enemy.TypeOne.name.equals("Rock") ||
                        enemy.TypeTwo.name.equals("Rock") || enemy.TypeTwo.name.equals("Steel") || enemy.TypeTwo.name.equals("Rock"))) {

                        g.p(enemy.Name + " is buffeted by the sandstorm!");

                        enemy.Health -= (int)(enemy.MaxHealth / 16);
                        g.p("Dealt " + (int)(enemy.MaxHealth / 16) + " damage!");
                    }

                } else {
                    g.p("The sandstorm subsided.");
                    weather = "clear";
                    weatherTurns = 0;
                }
                break;

                case "hail":
                if (weatherTurns <= 5) {
                    g.p("Hail continues to fall.");

                    if (player.Health > 1 && !(player.TypeOne.name.equals("Ice") || player.TypeTwo.name.equals("Ice"))) {

                        g.p(player.Name + " is buffeted by the hail!");

                        player.Health -= (int)(player.MaxHealth / 16);
                        g.p("Dealt " + (int)(player.MaxHealth / 16) + " damage!");

                    }
                    if (enemy.Health > 1 && !(enemy.TypeOne.name.equals("Ice") || enemy.TypeTwo.name.equals("Ice"))) {
                        g.p(enemy.Name + " is buffeted by the hail!");
                        enemy.Health -= (int)(enemy.MaxHealth / 16);
                        g.p("Dealt " + (int)(enemy.MaxHealth / 16) + " damage!");

                    }
                } else {
                    g.p("The hail stopped.");
                    weather = "clear";
                    weatherTurns = 0;
                }
                break;

                default:
                System.err.println("ERROR - " + weather + " is not a valid weather condition!");
                weather = "clear";
                weatherTurns = 0;
                break;

            }

        }
    }

    private static String hPdisplay(Pokemon pokemon){
        double NUMBER_OF_BARS = 12;
        double fraction = (double) pokemon.Health/ (double) pokemon.MaxHealth;
        int fct = (int) (fraction * NUMBER_OF_BARS);
        String str = "";
        for(int i=0; i < (NUMBER_OF_BARS-fct); i++){
            str += "═";
        }

        for(int i=0; i < fct; i++){
            str += "█";
        }

        str += "▏";

        return str;
    }

    public static Pokemon makePokemon(String name){
        switch(name.toLowerCase()){
            case "ee":
            return new Pokemon(game, "Easter Egg", 0, 350, 350, 350, 350, 350, 350, EggBomb, SoftBoiled, Celebrate, DoNotUse, Normal, none, false);

            case "murder":
            return new Pokemon(game, "Murder", 100, 400, 400, 400, 400, 400, 400, Metronome, Snore, SteamEruption, HealOrder, Water, Steel, false);

            case "debug":
            return new Pokemon(game, "John (debug)", 100,
                300, 300, 300, 300, 300, 300,
                accupressure, BlastBurn, HealOrder, Metronome,
                Water, Steel, false);

            case "spc":
            return new Pokemon(game, "Special Condition", 100, 0, 400, 0, 400, 400, 400, Spore, Glare, SweetKiss, PoisonPowder, none, none, false);

            case "us":
            double[] compad = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
            return new Pokemon(game, "Ultimate Struggle", 100, 350, 200, 0, 200, 400, 700, Struggle, Recover, PainSplit, noAttack, new type("Haha you're weak to everything!", -1, compad), none, false);

            case "lose!":
            return new Pokemon(game, "Lose!", 0, 0, 200, 0, 200, 0, 1, Splash, noAttack, noAttack, noAttack, none, none, false);

            case "walker - texas ranger":
            return new Pokemon(game, "Chuck Norris", Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, chuckNorrisRoundhouseKick, chuckNorrisRoundhouseKick, chuckNorrisRoundhouseKick, chuckNorrisRoundhouseKick, Fighting, Dark, false);

            case "":
            Pokemon p = new Pokemon(game, name);
            p.Name = g.getRandomName(p);
            return p;

            case "custom":
            return customPokemonAsk();
            
            case "random":
            return new Pokemon(game, "Random!", 300, 300, 300, 300, 300, 300, 300, accupressure, Metronome, noAttack, noAttack, none, none, false);

            case "recover":
            return new Pokemon(game, "Recover", 100, 0, 100, 0, 100, 0, 1000, Recover, HealOrder, SlackOff, SoftBoiled, none, none, false);
            
            default:
            return new Pokemon(game, name);

        }
    }

    public static void playerNewPokemon(){

        boolean say = true;
        if(playerIn()){
            if(!simpleGame){
                String[] args = new String[pokemonInRoster];
                for(int i = 0; i<args.length; i++)
                    args[i] = p1Roster[i].Name + " (HP "+p1Roster[i].Health+" of "+p1Roster[i].MaxHealth+")";

                String name = g.askForOption("Who do you want to send out?", "New Pokemon", args);

                for(int i=0;i<args.length;i++){
                    if(name == args[i]){
                        if(player == p1Roster[i]){
                            g.p("Please choose a different Pokemon!");
                            playerNewPokemon();
                            say = false;
                            break;
                        }else{

                            player = p1Roster[i];
                            break;
                        }
                    }
                }

                if(player.Health < 1){
                    g.pNo("Please choose a Pokemon with one or more HP!");
                    playerNewPokemon();
                }else{
                    /*if(say)
                    if(twoPlayer)
                    g.p(playerName + " sent out " + player.Name + "!");
                    else
                    g.p("Go, " + player.Name + "!");*/
                }
            }
        }else{
            if(!simpleGame)
                g.p(playerName + " blacked out!");
        }
    }

    public static void enemyNewPokemon(){
        boolean say = true;
        if(enemyIn()){
            if(!simpleGame){
                if(twoPlayer)
                {String[] args = new String[pokemonInRoster];
                    for(int i = 0; i<args.length; i++)
                        args[i] = p2Roster[i].Name + " (HP "+p2Roster[i].Health+" of "+p2Roster[i].MaxHealth+")";

                    String name = g.askForOption("Who do you want to send out?", "New Pokemon", args);

                    for(int i=0;i<args.length;i++){
                        if(name == args[i]){
                            if(player == p2Roster[i]){
                                g.p("Please choose a different Pokemon!");
                                enemyNewPokemon();
                                say = false;
                                break;
                            }else{
                                enemy = p2Roster[i];
                                break;
                            }
                        }
                    }

                    if(enemy.Health < 1){
                        g.pNo("Please choose a Pokemon with one or more HP!");
                        enemyNewPokemon();
                    }else{
                        if(say)
                            g.p(enemyName + " sent out " + enemy.Name + "!");
                    }

                }else{
                    enemy = p2Roster[g.randomNumber(0, p2Roster.length - 1)];
                    if(enemy.Health < 1){
                        enemyNewPokemon();
                    }else{
                        if(say)
                            g.p(enemyName + " sent out " + enemy.Name + "!");
                    }
                }
            }else{
                if(!simpleGame)
                    g.p(enemyName + " blacked out!");
            }

        }else{
            if(!simpleGame)
                g.p(enemyName + " blacked out!");
        }
    }

    public static Pokemon[] makePokemonRoster(){
        Pokemon[] theList = new Pokemon[pokemonInRoster];

        for(int i=0; i<theList.length; i++){
            theList[i] = makePokemon(g.ask("What is Pokemon #" + (i + 1) + "'s name?"));
            if(theList[i].Name.equals("")){
                theList[i].Name = g.getRandomName(theList[i]);
            }
        }

        return theList;
    }

    public static void showMultiplePokemon(Pokemon[] roster){
        for(int i = 0; i < roster.length; i++){
            if(roster[i] == player || roster[i] == enemy)
                g.pNo("Pokemon #" + (i + 1) + " (active):\n"+roster[i]);
            else
                g.pNo("Pokemon #" + (i + 1) + ":\n"+roster[i]);
        }
    }

    public static int pokemonLeft(Pokemon[] roster){
        int pkmn = 0;
        for(int i = 0; i < roster.length; i++){
            if(roster[i].Health > 1)
                pkmn++;
        }
        return pkmn;
    }

    public static Pokemon customPokemonAsk(){
        //Pokemon(Battle game, String name, int level, int attack, int defense, int spAttack, int spDefense, int speed, int health, attack attack1,
        //attack attack2, attack attack3, attack attack4, type type1, type type2)

        g.pNo("Welcome to the custom Pokemon builder!");
        String name = g.ask("What is this pokemon's name?");

        int level = Integer.parseInt(g.ask("What is "+name+"'s level?"));
        int attack = Integer.parseInt(g.ask("What is "+name+"'s attack stat?"));
        int defense = Integer.parseInt(g.ask("What is "+name+"'s defense stat?"));
        int spAttack = Integer.parseInt(g.ask("What is "+name+"'s special attack stat?"));
        int spDefense = Integer.parseInt(g.ask("What is "+name+"'s special defense stat?"));    
        int speed = Integer.parseInt(g.ask("What is "+name+"'s speed stat?"));
        int health = Integer.parseInt(g.ask("What is "+name+"'s health?"));

        attack attack1 = attackSearch(g.ask("What is "+name+"'s first attack?"));
        attack attack2 = attackSearch(g.ask("What is "+name+"'s second attack?"));
        attack attack3 = attackSearch(g.ask("What is "+name+"'s third attack?"));
        attack attack4 = attackSearch(g.ask("What is "+name+"'s fourth attack?"));

        type typeOne = typeSearch(g.ask("What is "+name+"'s first type?"));
        type typeTwo = typeSearch(g.ask("What is "+name+"'s second type?"));

        return new Pokemon(game, name, level, attack, defense, spAttack,
            spDefense, speed, health, attack1, attack2, attack3, attack4,
            typeOne, typeTwo, false);

    }

    public static attack attackSearch(String name){
        for(int i = 0; i < allAttacks.length; i++){
            if(allAttacks[i].name.toLowerCase().equals(name.toLowerCase())){
                return allAttacks[i];
            }
        }

        return noAttack;
    }

    public static type typeSearch(String name){
        for(int i = 0; i < types.length; i++){
            if(types[i].name.toLowerCase().equals(name.toLowerCase())){
                return types[i];
            }
        }

        return none;
    }

    public static attack askForAttack(Pokemon player, Pokemon[] roster){
        String playerChoice;
        attack tempPlayerChoice = noAttack;
        int playerChoiceNumber1;
        do {
            playerChoice = "";
            playerChoiceNumber1 = 5;

            if (!player.Recharge) {
                /*Tp1choice = g.askForAttack(player);
                g.p(Tp1choice.name);*/
                playerChoiceNumber1 = 5;
                String[] playerSelectionValues;
                if(simpleGame)
                    playerSelectionValues = new String[5];
                else{
                    playerSelectionValues = new String[6];
                    playerSelectionValues[5] = "Switch out";
                }
                for (int i = 0; i < 4; i++) {
                    attack tAtk = player.AttackList[i];
                    playerSelectionValues[i] = tAtk.name + " (" + player.pp[i] + "pp)";
                }
                playerSelectionValues[4] = "View stats";
                if (player.pp[0] + player.pp[1] + player.pp[2] + player.pp[3] > 0) {
                    playerChoice = g.askForOption("What will " + player.Name + " use?",
                        "Choose attack", playerSelectionValues);
                } else {

                    if(simpleGame){
                        String[] stgle = {
                                Struggle.name,
                                "View stats"
                            };
                    }
                    else
                    {
                        String[] stgle = {
                                Struggle.name,
                                "View stats",
                                "Switch out"
                            };
                    }
                    playerChoice =  g.askForOption("What will " + player.Name + " use?", "Choose attack",
                        stgle);
                }
                if (playerChoice.equals(playerSelectionValues[0])) {
                    playerChoiceNumber1 = 0;
                    tempPlayerChoice = player.AttackList[0];
                } else if (playerChoice.equals(playerSelectionValues[1])) {
                    playerChoiceNumber1 = 1;
                    tempPlayerChoice = player.AttackList[1];
                } else if (playerChoice.equals(playerSelectionValues[2])) {
                    playerChoiceNumber1 = 2;
                    tempPlayerChoice = player.AttackList[2];
                } else if (playerChoice.equals(playerSelectionValues[3])) {
                    playerChoiceNumber1 = 3;
                    tempPlayerChoice = player.AttackList[3];
                } else if (playerChoice.equals("View stats")) {
                    tempPlayerChoice = viewStats;
                } else if (playerChoice.equals("Switch out")) {
                    tempPlayerChoice = switchOut;
                } else if(!playerChoice.equals("")){
                    tempPlayerChoice = Struggle;
                    playerChoiceNumber1 = 4;
                }
            }else{
                tempPlayerChoice = recharge;
                playerChoiceNumber1 = 4;
            }

            if (tempPlayerChoice.name.equals("none")) {
                g.p("Please choose a valid attack!");
            } else if (tempPlayerChoice.equals(viewStats)) {
                if(simpleGame)
                    g.viewStats(player);
                else
                    showMultiplePokemon(roster);
            } else if (tempPlayerChoice.equals(switchOut)) {
                if(pokemonLeft(roster) > 1){
                    playerChoiceNumber1 = 4;
                }else{
                    g.pNo("All other Pokemon are knocked out!");
                }
            } else if (playerChoiceNumber1 > 0 && player.pp[playerChoiceNumber1] < 1) {
                g.p("Invalid Move - out of pp");
            }

        } while (player.pp[playerChoiceNumber1] < 1);

        if(player == enemy){
            enemyChoiceNumber = playerChoiceNumber1;
        }else{
            playerChoiceNumber = playerChoiceNumber1;
        }

        return tempPlayerChoice;
    }

}
//                     _____________    ___________     _        _    ___        _
//                    |_____   _____|\ / _________ \\  | |\     | |\ |   \\     | |\
//                     \\\\\| |\\\\\\\/ /\        \ \\ | |\     | |\ | |\ \\    | |\
//                          | |\      | |\        | |\ | |\     | |\ | |\\ \\   | |\
//                          | |\      | |\        | |\ | |______| |\ | |\ \ \\  | |\
//                          | |\      | |\        | |\ |  ______  |\ | |\  \ \\ | |\
//                _         | |\      | |\        | |\ | |\\\\\\| |\ | |\   \ \\| |\
//                \\        / /\      | |\        | |\ | |\     | |\ | |\    \ \| |\
//                 \\______/ /\       \ \_________/ /\ | |\     | |\ | |\     \   |\
//                  \_______/\         \___________/\  |_|\     |_|\ |_|\      \__|\
//                   \\\\\\\\\          \\\\\\\\\\\\    \\       \\   \\        \\\\
//
//  __                          __   _         _    _             _             _______    ________
//  \ \\                       / /\ | |\      | |\ | |\          | |\          |  _____|\ |  _____ \\
//   \ \\                     / /\  | |\      | |\ | |\          | |\          | |\\\\\\\ | |\\\\\\ \\
//    \ \\                   / /\   | |\      | |\ | |\          | |\          | |\       | |\    | |\
//     \ \\       __        / /\    | |\      | |\ | |\          | |\          | |\____   | |\____/ /\
//      \ \\     /  \\     / /\     | |\      | |\ | |\          | |\          |  _____|\ |    ____/\
//       \ \\   / /\ \\   / /\      | |\      | |\ | |\          | |\          | |\\\\\\\ | |\ \\\\\
//        \ \\ / /\ \ \\ / /\       \ \\      / /\ | |\          | |\          | |\       | |\\ \\
//         \ \/ /\   \ \/ /\         \ \\____/ /\  | |\_______   | |\_______   | |\____   | |\ \ \\
//          \__/\     \__/\           \_______/\   |__________|\ |__________|\ |_______|\ |_|\  \_\\
//           \\\\      \\\\            \\\\\\\\\    \\\\\\\\\\\\  \\\\\\\\\\\\  \\\\\\\\\  \\    \\\
//
//          ___________          _________     ________        ___________          _________
//         |_________  \\       |  ______ \\  |  _____ \\     |_________  \\       |  ______ \\
//          \\\\\\\\\\  \\      | |\\\\\\\ \\ | |\\\\\\ \\     \\\\\\\\\\  \\      | |\\\\\\\ \\
//                    \  \\     | ||     | || | ||    | ||               \  \\     | ||     | ||
//                     \  \\    | ||_____/ // | ||____/ //  _____         \  \\    | ||_____/ //
//                     |  ||    |  ______ ||  |    ____//  |_____|\       |  ||    |  ______ ||
//                     |  ||    | |\\\\\\\ \\ | |\ \\       \\\\\\\       |  ||    | |\\\\\\\ \\
//                     /  //    | ||     | || | ||\ \\                    /  //    | ||     | ||
//          __________/  //__   | ||_____/ // | || \ \\        __________/  //__   | ||_____/ //
//         |_________________|\ |_________//  |_||  \_\\      |_________________|\ |_________//
//          \\\\\\\\\\\\\\\\\\\  \\\\\\\\\\    \\    \\\       \\\\\\\\\\\\\\\