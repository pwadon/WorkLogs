package project.worklogs.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ScannerService {

    public static int scanInt(String question, String exception){
        System.out.println(question);
        Scanner scan = new Scanner(System.in);
        while(!scan.hasNextInt()){
            System.out.println(exception);
            scan.next();
        }
        return  scan.nextInt();
    }

    public static Long scanLong(String question, String exception){
        System.out.println(question);
        Scanner scan = new Scanner(System.in);
        while(!scan.hasNextLong()){
            System.out.println(exception);
            scan.next();
        }
        return scan.nextLong();
    }

    public static String scanString(String question){
        System.out.println(question);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();

    }


}
