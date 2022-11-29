package at.jku;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class HasherApp {
    public static void main(String[] args) {
        System.out.println("-------------------");
        System.out.println("| Password Hasher |");
        System.out.println("-------------------");
        System.out.print("Enter hashing strength (4-31, Default 16, higher takes longer): ");
        int strength = In.readInt();
        if (strength == 0) {
            strength = 16;
        } else if (strength < 4) {
            strength = 4;
        } else if (strength > 16) {
            strength = 16;
        }

        System.out.println("Hashing Strength chosen or autocorrected: " + strength);
        System.out.println("Enter Password:");
        //String pw = In.readString();
        Scanner in = new Scanner(System.in);
        String pw = in.next();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(strength);
        System.out.println("Generated hash: " + encoder.encode(pw));
    }
}