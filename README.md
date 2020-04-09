# Enigma Machine Simulator

## Introduction
  The Enigma machine is an encryption device developed and used in the early- to mid-20th century to protect commercial, diplomatic and military communication. It was employed extensively by Nazi Germany during World War II, in all branches of the German military.
  Alan Turing and other scientists work on cracking this machine ensured the success of the Allies. Learn more about the Enigma machine [here](https://en.wikipedia.org/wiki/Enigma_machine), or by watching [this video](https://www.youtube.com/watch?v=G2_Q9FoD-oQ).

## Despription
  The Enigma machine primary uses rotors and the plugboard to encrypt messages (More rotors enables better encrypting power). The original Enigma machine has only 5 rotors, but in this simulator you can put in as many as you want. P.S. the default.conf inside testing/correct is the original rotor configuration of the original machine.


## To run the program:

1. First compile code by `javac *.java`
2. Put messages you want to encrypt into an input file using the format `*.in`; Configuration file should have the format `*.conf`.
3. type `cd ..`
4. Run Main.java to execute the program and see the encrypted message.
5. Example:  type `java -ea Enigma.Main Enigma/testing/correct/default.conf Enigma/testing
/correct/prayer.in` to encrypt file `prayer.in` with default configuration, using 1 reflector (B) and 4 Rotors (Gamma II I V) with initial setting CDBI (corresponding to rotor Gamma, II, I, V, respectively) and plugboard setting (TD) (KC) (JZ).
6. You're welcome to make a new directory and put in your own configuration, .in file, etc.
7. Other things: 
    1) Support ring settings. Put settings at input file like `* B Gamma II I V CDBI ABCD (TD) (KC) (JZ)`. ABCD is the ring setting of rotor Gamma, II, I, V, respectively.

    2) Use `java -ea Enigma.Main [*.conf] [*.in] [*.out]` to dump your encrypted messages into a [*.out] file.
