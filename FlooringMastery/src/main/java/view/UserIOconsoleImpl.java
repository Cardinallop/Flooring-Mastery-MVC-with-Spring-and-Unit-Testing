/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author bobur
 */

@Component
public class UserIOconsoleImpl implements UserIO {
    
     Scanner scan = new Scanner(System.in);

    @Override
    public void print(String message) {

        System.out.println(message);

    }

    @Override
    public String readString(String prompt) {

        System.out.println(prompt);

        return scan.nextLine();

    }

    @Override
    public int readInt(String prompt) {

        System.out.println(prompt);

        int readInt = scan.nextInt();

        scan.nextLine();
        return readInt;

    }

    @Override
    public int readInt(String prompt, int min, int max) {

        boolean valid = false;

        while (!valid) {

            System.out.println(prompt);

            int readInt = scan.nextInt();
            scan.nextLine();

            if ((readInt >= min) && (readInt <= max)) {

                valid = true;

                return readInt;

            } else {
                valid = false;
            }
        }
        return 0;

    }

    @Override
    public double readDouble(String prompt) {

        System.out.println(prompt);

        double dobl = scan.nextDouble();
        scan.nextLine();

        return dobl;

    }

    @Override
    public double readDouble(String prompt, double min, double max) {

        boolean valid = false;

        while (!valid) {

            System.out.println(prompt);

            double readDouble = scan.nextDouble();
            scan.nextLine();

            if ((readDouble >= min) && (readDouble <= max)) {

                valid = true;

                return readDouble;
            } else {
                valid = false;
            }
        }
        return 0;
    }

    @Override
    public float readFloat(String prompt) {

        System.out.println(prompt);

        float fl = scan.nextFloat();
        scan.nextLine();

        return fl;

    }

    @Override
    public float readFloat(String prompt, float min, float max) {

        boolean valid = false;

        while (!valid) {

            System.out.println(prompt);

            float readfl = scan.nextFloat();
            scan.nextLine();

            if ((readfl > min) && (readfl < max)) {

                valid = true;

                return readfl;
            } else {
                valid = false;
            }
        }
        return 0;

    }

    @Override
    public long readLong(String prompt) {

        System.out.println(prompt);

        long lg = scan.nextLong();
        scan.nextLine();

        return lg;

    }

    @Override
    public long readLong(String prompt, long min, long max) {

        boolean valid = false;

        while (!valid) {

            System.out.println(prompt);

            long lg = scan.nextLong();
            scan.nextLine();

            if ((lg > min) && (lg < max)) {

                valid = true;

                return lg;
            } else {
                valid = false;
            }
        }
        return 0;

    }
    
    
}
