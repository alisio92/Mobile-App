package be.howest.nmct.stopafstand;

import java.util.Scanner;

/**
 * Created by alisio on 12/02/2015.
 */
public class TestStopAfstand {
    public static void main(String[] args){
        //testMethod();
        getAfstand();
    }

    private static void getAfstand(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Geef snelheid op(in km/u): ");
        int snelheid = scanner.nextInt();

        System.out.println("Geef reactietijd op(in sec, bv: 1,2): ");
        float reactie = scanner.nextFloat();

        System.out.println("Welk wegtype selecteer je: NAT of DROOG");
        String wegtype = scanner.next();

        StopAfstandinfo info = new StopAfstandinfo(snelheid, reactie, wegtype);
        System.out.println("De stopafstand is: " + info.getStopafstand());
    }

    private static void testMethod(){
        System.out.println("Demo oef1");
        Voertuig voertuig = new Voertuig(Voertuig.MerkVoertuig.OPEL,2014);
        System.out.println(voertuig);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Geef een getal op: ");
        int getal = scanner.nextInt();
        System.out.println("Getal is: " + getal);

        System.out.println("Geef een decimaal getal op: ");
        float decimaal = scanner.nextFloat();
        System.out.println("Decimaal getal is: " + decimaal);
    }
}
