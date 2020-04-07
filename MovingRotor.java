package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Heming Wu
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches.toCharArray();
    }

    @Override
    boolean rotates() {
        return true;
    }

    @Override
    boolean atNotch() {
        for (char c : _notches) {
            if (this.alphabet().toInt(c) == this.setting()) {
                return true;
            }
        }
        return false;
    }


    @Override
    void advance() {
        int posn = this.permutation().wrap(this.setting() + 1);
        set(posn);
    }

    /** Notches of the rotors. */
    private char[] _notches;

}
