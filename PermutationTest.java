package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author Heming Wu
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }

    @Test
    public void checkUpper1A() {
        perm = new Permutation(NAVALA.get("I"), UPPER);
        checkPerm("I", UPPER_STRING, NAVALA_MAP.get("I"));
    }

    @Test
    public void checkUpper2A() {
        perm = new Permutation(NAVALA.get("II"), UPPER);
        checkPerm("II", UPPER_STRING, NAVALA_MAP.get("II"));
    }

    @Test
    public void checkUpper3A() {
        perm = new Permutation(NAVALA.get("III"), UPPER);
        checkPerm("III", UPPER_STRING, NAVALA_MAP.get("III"));
    }

    @Test
    public void checkUpper1B() {
        perm = new Permutation(NAVALB.get("I"), UPPER);
        checkPerm("I", UPPER_STRING, NAVALB_MAP.get("I"));
    }

    @Test
    public void checkUpperBetaB() {
        perm = new Permutation(NAVALB.get("Beta"), UPPER);
        checkPerm("Beta", UPPER_STRING, NAVALB_MAP.get("Beta"));
    }

    @Test
    public void checkUpperGammaB() {
        perm = new Permutation(NAVALB.get("Gamma"), UPPER);
        checkPerm("Gamma", UPPER_STRING, NAVALB_MAP.get("Gamma"));
    }

    @Test
    public void checkUpper5Z() {
        perm = new Permutation(NAVALZ.get("V"), UPPER);
        checkPerm("V", UPPER_STRING, NAVALZ_MAP.get("V"));
    }

    @Test
    public void checkUpper6Z() {
        perm = new Permutation(NAVALZ.get("VI"), UPPER);
        checkPerm("VI", UPPER_STRING, NAVALZ_MAP.get("VI"));
    }

    @Test
    public void checkUpper7Z() {
        perm = new Permutation(NAVALZ.get("VII"), UPPER);
        checkPerm("VII", UPPER_STRING, NAVALZ_MAP.get("VII"));
    }

    @Test
    public void checkUpper8Z() {
        perm = new Permutation(NAVALZ.get("VIII"), UPPER);
        checkPerm("VIII", UPPER_STRING, NAVALZ_MAP.get("VIII"));
    }


}
