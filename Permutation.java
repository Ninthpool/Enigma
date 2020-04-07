package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Heming Wu
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = cycles.replace(" ", "").replace("(", "")
                .replace(")", "@").split("@");

        for (String s : _cycles) {
            for (int i = 0; i < s.length(); i++) {
                char element = s.charAt(i);
                if (_alphabet.toInt(element) < 0) {
                    throw error("Some element in cycles "
                            + "is not in alphabet. "
                            + "How did you type in these letters?");
                }
            }
        }
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        _cycles[_cycles.length + 1] = cycle;
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        int index = wrap(p);
        char oriLetter = _alphabet.toChar(index);
        String targetCycle = "";

        if (_cycles.length == 1 && _cycles[0].equals("")) {
            return p;
        }
        for (String s: _cycles) {
            if (s.indexOf(oriLetter) >= 0) {
                targetCycle = s;
                break;
            }
        }
        if (targetCycle.equals("")) {
            return p;
        }

        if (targetCycle.indexOf(oriLetter) < targetCycle.length() - 1) {
            char outLetter = targetCycle.
                    charAt(targetCycle.indexOf(oriLetter) + 1);
            return _alphabet.toInt(outLetter);
        } else {
            char outLetter = targetCycle.charAt(0);
            return _alphabet.toInt(outLetter);
        }
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        int index = wrap(c);
        char oriLetter = _alphabet.toChar(index);

        if (_cycles.length == 1 && _cycles[0].equals("")) {
            return c;
        }
        String targetCycle = "";
        for (String s: _cycles) {
            if (s.indexOf(oriLetter) >= 0) {
                targetCycle = s;
                break;
            }
        }
        if (targetCycle.equals("")) {
            return c;
        }

        if (targetCycle.indexOf(oriLetter) > 0) {
            char outLetter = targetCycle.
                    charAt(targetCycle.indexOf(oriLetter) - 1);
            return _alphabet.toInt(outLetter);
        } else {
            char outLetter = targetCycle.charAt(targetCycle.length() - 1);
            return _alphabet.toInt(outLetter);
        }
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        int p1 = _alphabet.toInt(p);
        return _alphabet.toChar(permute(p1));
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        int c1 = _alphabet.toInt(c);
        return _alphabet.toChar(invert(c1));
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (int i = 0; i < _alphabet.size(); i++) {
            char element = _alphabet.toChar(i);
            if (permute(element) == element) {
                return false;
            }
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** The cycles provided. */
    private String[] _cycles;
}
