package enigma;

import static enigma.EnigmaException.*;

/** Superclass that represents a rotor in the enigma machine.
 *  @author Heming Wu
 */
class Rotor {

    /** A rotor named NAME whose permutation is given by PERM. */
    Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;

    }

    /** Return my name. */
    String name() {
        return _name;
    }

    /** Return my alphabet. */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /** Return my permutation. */
    Permutation permutation() {
        return _permutation;
    }

    /** Return the size of my alphabet. */
    int size() {
        return _permutation.size();
    }

    /** Return true iff I have a ratchet and can move. */
    boolean rotates() {
        return false;
    }

    /** Return true iff I reflect. */
    boolean reflecting() {
        return false;
    }

    /** Return my current setting. */
    int setting() {
        return _setting;
    }

    /** Set setting() to POSN.  */
    void set(int posn) {
        _setting = posn;
    }

    /** Set setting() to character CPOSN. */
    void set(char cposn) {
        _setting = _permutation.alphabet().toInt(cposn);
    }

    /** Return current ringset. */
    int ringSetting() {
        return _ringSetting;
    }

    /** Set ringSetting() to a.
     * @param a The ring setting.
     * */
    void ringSet(int a) {
        _ringSetting = a;
    }

    /** Set ringSetting() to character ca.
     * @param a The ring setting in char
     * */
    void ringSet(char a) {
        _ringSetting = _permutation.alphabet().toInt(a);
    }


    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation. */
    int convertForward(int p) {
        int enter = _permutation.wrap(p + _setting);
        int i = _permutation.permute(enter - _ringSetting);
        int outIndex = _permutation.wrap(i - _setting + _ringSetting);
        return outIndex;
    }

    /** Return the conversion of E (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        int enter = _permutation.wrap(e + _setting);
        int i = _permutation.invert(enter - _ringSetting);
        int out = _permutation.wrap(i - _setting + _ringSetting);
        return out;
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;

    /** Initial setting of the rotors. */
    private int _setting;

    /** Ring settings of the rotor, initially at A(0). */
    private int _ringSetting = 0;

}
