/**
 * This is the object for types
 * There are 19 types (in this order):
 * 
 * none, Normal, Fighting, Flying, Poison, Ground,
 * Rock, Bug, Ghost, Steel, Fire, Water, Grass, Electric,
 * Psychic, Ice, Dragon, Dark, Fairy
 * 
 * @author John Wuller
 * @version 1.1
 */
public class type {
    /** The name of the type */
    String name;
    /** The number in the list of attacks, used for typeCompad */
    int index;
    /** This is how the type fares when attacked by each other type*/
    double[] vsIndex;

    /**
     * This is the constructor for a new type
     */
    public type(String name, int index, double[] vsIndex) {
        this.name = name;
        this.index = index;
        this.vsIndex = vsIndex;
    }

    /**
     * A method to return how a type fares against this type
     * 
     * @param index  the index of the type attacking this type
     * @return the damage multiplier for this type compadibility
     */
    public double typeCompad(int index) {
        if(index == -1){
            return 1;
        }else{
            return vsIndex[index];
        }
    }

    @Override
    public String toString(){
        return name;
    }

}