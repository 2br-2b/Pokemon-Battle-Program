import javax.swing.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
/**
 * This contains all of the generic functions in the programming
 *
 * @author John Wuller
 * @author Katherine Franda
 * @author LouwHopley (https://stackoverflow.com/users/679277/louwhopley)
 * @author Lonely Neuron (https://stackoverflow.com/users/4298200/lonely-neuron)
 * @version 1.4
 */
public class Generic
{

    String replay = "";

    /**
     * @author John Wuller
     * @version 1.2
     * 
     * A method to make a popup of anything and add it to the replay
     *
     * @param  str  the string to be popped up
     */
    public void p(String str){
        JOptionPane.showMessageDialog(null, str);
        replay += "\n" + str;
    }

    /**
     * @author John Wuller
     * @version 1.0
     * 
     * A method to make a popup of anything and not add it to the replay
     *
     * @param  str  the string to be popped up
     */
    public void pNo(String str){
        JOptionPane.showMessageDialog(null, str);
    }

    /**
     * @version 1.1
     * @author John Wuller
     * @author Katherine Franda
     * 
     * returns a random interger in the range
     * 
     * @param min  the lowest possible random number
     * @param max  the highest possible random number
     * @return the random number
     */
    public int randomNumber(int min, int max) {
        if (min > max) {
            System.err.println("Custom Random Number Function Error:\nMinimun number must be less than Maximum Number.");
            System.exit(1);
        }
        int range = max - min;
        int ret = (int) (Math.random() * (range + 1) + 0.5) + min;

        if(ret > max || ret < min)
            return randomNumber(min, max);
        else
            return ret;

    }

    /**
     * @author John Wuller
     * @version 1.0
     * 
     * asks a question and returns the answer
     * 
     * @param str  the question to be asked
     * @return     the answer to the question asked
     * 
     */
    public String ask(String str) {
        return JOptionPane.showInputDialog(str);
    }

    /**
     * @author John Wuller
     * @version 1.3
     * 
     * copies a replay of the game
     * 
     * @param game  the game
     * 
     */
    public void saveReplay(Battle game) {
        String fullReplay = replay;
        if(game.simpleGame){
            game.player.reset();
            game.enemy.reset();
            fullReplay = game.player + "\n\n\n" + game.enemy + "\n\n" + replay;
        }else{
            for(int i=0; i < game.p1Roster.length; i++){
                game.p1Roster[i].reset();
                game.p2Roster[i].reset();
            }

            for(int i= game.p2Roster.length - 1; i > -1; i--){
                fullReplay = game.p2Roster[i] + "\n\n" + fullReplay;
            }

            fullReplay = "\n\n-------------"
            +"\n\n" + game.enemyName + "'s roster:\n\n"+ fullReplay;

            for(int i=game.p1Roster.length - 1; i > -1; i--){
                fullReplay = game.p1Roster[i] + "\n\n" + fullReplay;
            }

            fullReplay = 
            "\n\n-------------"
            +"\n\n" + game.playerName + "'s roster:\n\n" + fullReplay;

        }
        copyTextToClipboard(fullReplay);
        pNo("The text has been copied to the clipboard!");
    }

    /**
     * @author John Wuller
     * @version 1.2
     * @param user  the pokemon to view the stats of
     * 
     * this function shows the stats of any given Pokemon
     */
    public void viewStats(Pokemon user) {
        pNo(user + "");
    }

    public void endTurn() {
        replay += "\n";
    }

    /**
     * 
     * copies the given text to the clipboard
     * 
     * @author John Wuller
     * @author LouwHopley (https://stackoverflow.com/users/679277/louwhopley)
     * @author Lonely Neuron (https://stackoverflow.com/users/4298200/lonely-neuron)
     * @version 1.0
     * @param str  the string to copy
     */

    private void copyTextToClipboard(String str){
        StringSelection stringSelection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public attack askForAttack(Pokemon pokemon){
        Tester window = new Tester(pokemon);
        return window.theAttack;
    }

    public String askForOption(String question, String title, String[] answers){

        String replayAsk = JOptionPane.showInputDialog(null, question, title,
                JOptionPane.INFORMATION_MESSAGE, null, answers,
                null).toString();

        return replayAsk;

    }

    public boolean askYN(String question, String title){

        String[] args = {"Yes","No"};
        String answer = askForOption(question, title, args);

        if(answer == "Yes")
            return true;
        else
            return false;

    }

    public String getRandomName(Pokemon pokemon){
        /*
         * Inside Jokes:
         * 
         * Fireus, Shock, Sky, Gold, Groundes, Watres, and Leaf are all names of types from a Pokemon ripoff I was making when I was 10 called "Johnare"
         * 
         * 
         */
        String[][] typeNames = {

                //Normal
                {"Normalium", "Victorium", "Arien", "Factorian", "Unbroken", "Xanadu", "Hypertension", "Londinium", "Durovium"},
                //Fighting
                {"Musculan", "Stormbreaker", "Buster", "Hercules", "Unbroken"},
                //Flying
                {"Duckie", "Arien", "HurriKaine", "Winshield", "Starhopper", "Caledonia"},
                //Poison
                {"Scorpius", "Stinger"},
                //Ground
                {"Earthshaker", "Landmaster", "Qanat", "Londinium"},
                //Rock
                {"Boulder", "Unbroken", "Buster"},
                //Bug
                {"Mosquito", "Winshield", "Beastie", "Gremlin", "Scorpius"},
                //Ghost
                {"Phase", "Mysterium", "Chill", "Pumpkin"},
                //Steel
                {"Metalium", "Alloy", "Robotron 2000", "Calvary", "Aluminium", "Unbroken"},
                //Fire
                {"Infernite", "Fuego", "Matchbox", "Barbeque", "Forest Fire", "Fireus"},
                //Water
                {"Droplet", "Duckie", "Ocean", "Aquarium", "Amoria", "Saphera", "HurriKaine", "Qanat", "Aquae Sulis"},
                //Grass
                {"Leafitie", "Pumpkin", "Forest Fire", "Foeliage"},
                //Electric
                {"Thundrite", "Excitric", "Pokachu", "Stormbreaker", "Thundertres"},
                //Psychic
                {"Psyche", "Mysterium", "Psychology", "Xanadu"},
                //Ice
                {"Chill", "Glacier", "Icicle", "Deadlock"},
                //Dragon
                {"Draco", "Eragon", "Saphera", "Starhopper"},
                //Dark
                {"Shadow", "Mysterium", "Ninjun", "Cosmeon", "The Imposter", "Starhopper", "Darkpatch"},
                //Fairy
                {"Pumpkin", "Pixie", "Twinkle", "Gremlin", "Caledonia"}
            };
        int i;
        if(pokemon.TypeOne.index < 0)
            i = 0;
        else
            i = pokemon.TypeOne.index;

        int j = -1;
        while(j < 0)
            j = randomNumber(0, (typeNames[i].length-1));

        return typeNames[i][j];
    }
    
    
}