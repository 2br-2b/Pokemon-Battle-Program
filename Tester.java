
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author John Wuller
 * @version 2.0
 * 
 * This is a class which is being tested in order to make the battle
 * appear nicer.  It is still a work in progress.
 * 
 * TODO:
 *  add "View stats" option
 *  get it to work and incorporate it
 * 
 */
public class Tester extends JFrame implements ActionListener{

    public static String AtkNumber;
    JButton atck1button;
    JButton atck2button;
    JButton atck3button;
    JButton atck4button;
    JButton viewStatsButton;
    JButton switchOutButton;

    Battle game;
    Generic g = game.g;

    boolean gottenResult;
    public attack theAttack;

    /**
     * @author John Wuller
     * @version 1.0
     * 
     * The function to make the popup box
     */
    public Tester (Pokemon pokemon){
        super("What will "+pokemon.Name+" do?");

        gottenResult = false;

        String[] names = new String[5];
        for (int i = 0; i < 4; i++) {
            attack tAtk = pokemon.AttackList[i];
            names[i] = tAtk.name + " (" + pokemon.pp[i] + "pp)";
        }
        names[4] = "View stats";

        atck1button = new JButton(names[0]);
        atck1button.setActionCommand("1");
        atck1button.addActionListener(this);

        atck2button = new JButton(names[1]);
        atck2button.setActionCommand("2");
        atck2button.addActionListener(this);

        atck3button = new JButton(names[2]);
        atck3button.setActionCommand("3");
        atck3button.addActionListener(this);

        atck4button = new JButton(names[3]);
        atck4button.setActionCommand("4");
        atck4button.addActionListener(this);

        viewStatsButton = new JButton("View stats");
        viewStatsButton.setActionCommand("vs");
        viewStatsButton.addActionListener(this);

        switchOutButton = new JButton("Switch out");
        switchOutButton.setActionCommand("so");
        switchOutButton.addActionListener(this);

        //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setLayout(new FlowLayout());
        add(atck1button);
        add(atck2button);
        add(atck3button);
        add(atck4button);
        add(viewStatsButton);
        add(switchOutButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(400,150);
        this.setVisible(true);

        while(!gottenResult){
            //wait for result
        }

        if(AtkNumber.equals("1"))
            theAttack = pokemon.AttackList[0];
        else if(AtkNumber.equals("2"))
            theAttack = pokemon.AttackList[1];
        else if(AtkNumber.equals("3"))
            theAttack = pokemon.AttackList[2];
        else if(AtkNumber.equals("4"))
            theAttack = pokemon.AttackList[3];
        else if(AtkNumber.equals("View stats"))
            theAttack = Battle.viewStats;
        /*else if(AtkNumber.equals("Switch out"))
        theAttack = Battle.switchOut;*/
        else
            theAttack = Battle.Struggle;
    }

    /**
     * @author John Wuller
     * @version 1.1
     * 
     * @param evt  the choice
     * 
     * This prints out the attack chosen
     */
    public void actionPerformed(ActionEvent evt){
        this.AtkNumber = evt.getActionCommand();
        this.gottenResult = true;
        //System.exit(0);
    }

}
//       _____________    ___________     _        _    __         _
//      |_____   _____|  / _________ \   | |      | |  |   \      | |
//            | |       / /         \ \  | |      | |  | |\ \     | |
//            | |       | |         | |  | |      | |  | | \ \    | |
//            | |       | |         | |  | |______| |  | |  \ \   | |
//            | |       | |         | |  |  ______  |  | |   \ \  | |
//  _         | |       | |         | |  | |      | |  | |    \ \ | |
//  \\        / /       | |         | |  | |      | |  | |     \ \| |
//   \\______/ /        \ \_________/ /  | |      | |  | |      \   |
//    \_______/          \___________/   |_|      |_|  |_|       \__|