package enigma;

import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Heming Wu
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _numPawls = pawls;
        _allRotors = allRotors.toArray(_allRotors);
        _myRotors = new Rotor[_numRotors];

        if (_numPawls < 0 || _numPawls >= _numRotors) {
            throw EnigmaException.error("Number of pawls"
                    + "should be between 0 and number of rotors");
        }

        if (_numRotors <= 1) {
            throw EnigmaException.error("Must have at least"
                    + "two rotors");
        }
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _numPawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        if (rotors.length > _myRotors.length) {
            throw error("Too many rotors");
        }
        for (int i = 0; i < rotors.length; i++) {
            for (Rotor r : _allRotors) {
                if (r.name().equals(rotors[i])) {
                    _myRotors[i] = r;
                }
            }
        }
        if (!(_myRotors[0] instanceof Reflector)) {
            throw new EnigmaException("The leftmost rotor must be a reflector");
        }

        if (!(_myRotors[_myRotors.length - 1] instanceof MovingRotor)) {
            throw new EnigmaException("The rightmost rotor must be "
                    + "a moving rotor");
        }

        int movingRotorCount = 0;
        for (Rotor s : _myRotors) {
            if (s instanceof MovingRotor) {
                movingRotorCount += 1;
            }
        }
        if (movingRotorCount != _numPawls) {
            throw new EnigmaException("Number of moving rotors "
                    + "doesn't match");
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).
     *
     * @param  setting
     *         Current settings.
     *
     * @param  ringset
     *         Current ring settings.
     */
    void setRotors(String setting, String ringset) {
        if (setting.length() != _numRotors - 1) {
            throw new EnigmaException("Number of settings should be equal to "
                    + "the number of rotors (other than the reflector).");
        }
        if (!ringset.equals("") && ringset.length() != _numRotors - 1) {
            throw new EnigmaException("Number of ring settings should "
                    + "be equal to the number of rotors - 1.");
        }
        for (int i = 0; i < setting.length(); i++) {
            char setat = setting.charAt(i);
            for (int j = 1; j < _myRotors.length; j++) {
                if (j == i + 1) {
                    _myRotors[j].set(setat);
                }
            }
        }

        for (int i = 0; i < ringset.length(); i++) {
            char ringSetat = ringset.charAt(i);
            for (int j = 1; j < _myRotors.length; j++) {
                if (j == i + 1) {
                    _myRotors[j].ringSet(ringSetat);
                }
            }
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing

     *  the machine. */
    int convert(int c) {
        char input = _alphabet.toChar(c);
        if (_plugboard.alphabet().contains(input)) {
            input = _plugboard.permute(input);
        }



        Boolean preAtNotch = false;
        Boolean rightHasmoved = false;
        for (int i = _myRotors.length - 2; i > 0; i -= 1) {
            Boolean rightAtNotch = _myRotors[i + 1].atNotch();
            if ((rightAtNotch && !rightHasmoved) || preAtNotch) {
                preAtNotch = _myRotors[i].atNotch();
                _myRotors[i].advance();
                if (i + 1 != _myRotors.length - 1
                        && _myRotors[i] instanceof MovingRotor
                        && !rightHasmoved) {
                    _myRotors[i + 1].advance();
                }
                rightHasmoved = true;
            } else {
                rightHasmoved = false;
            }
        }
        _myRotors[_myRotors.length - 1].advance();

        int i = _alphabet.toInt(input);
        int output = convertHelper(i);
        return  _plugboard.permute(output);
    }

    /** Simulate the process of a letter floating from right to left
     * and then from left to right.
     * @return an int the fastest rotor will return.
     * @param ori the letter passed in.
     */
    int convertHelper(int ori) {
        int reflectorReturn = convertForwardHelper(_myRotors.length - 1, ori);
        return convertBackwardHelper(1, reflectorReturn);
    }


    /** The letter passed in will flow through
     * all the rotors from right to left.
     *  It returns what the reflector returns.
     * @param i the index of the current rotor
     * @param p the letter passed in.
     * @return what the reflector returns.
     */
    int convertForwardHelper(int i, int p) {
        if (i == 0) {
            p = _myRotors[i].permutation().wrap(p);
            return _myRotors[0].convertForward(p);
        } else {
            p = _myRotors[i].permutation().wrap(p);
            return convertForwardHelper(i - 1, _myRotors[i].convertForward(p));
        }
    }


    /** Help passed the letter from left to right (1 to length -1).
     *  It returns what the fastest rotor returns.
     * @param i the index of the current rotor.
     * @param p the letter the reflector returned
     * @return what the fastest rotor returns.
     */
    int convertBackwardHelper(int i, int p) {
        if (i == _myRotors.length - 1) {
            p = _myRotors[i].permutation().wrap(p);
            return _myRotors[_myRotors.length - 1].convertBackward(p);
        } else {
            p = _myRotors[i].permutation().wrap(p);
            return convertBackwardHelper(i + 1,
                    _myRotors[i].convertBackward(p));
        }
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String outmsg = "";
        for (int i = 0; i < msg.length(); i++) {
            char inLetter = msg.charAt(i);
            int in = _alphabet.toInt(inLetter);
            if (in == -1) {
                throw error("Letter(s) not in alphabet");
            }
            char outLetter = _alphabet.toChar(convert(in));
            outmsg += outLetter;
        }
        return outmsg;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Number of rotor. */
    private int _numRotors;

    /**Number of pawls (Also number of moving rotors). */
    private int _numPawls;

    /** All rotors in my toolbox that I can use. */
    private Rotor[] _allRotors = new Rotor[_numRotors];

    /** Rotors I'm currently using in my machine.
     * The array is ordered (Rotor[0] is my reflector). */
    private Rotor[] _myRotors;

    /** my plugboard setting. */
    private Permutation _plugboard;
}
