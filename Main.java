package gb.g6032;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final int fNum=6;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите данные, разделенные пробелом: ");
        String data = scan.nextLine();
        scan.close();
        parseData(data);

    }

    private static void parseData(String data) {
        String parse [] = data.split(" ");
            if (parse.length != fNum) {
                System.err.println("Введено неверное количество данных - " + parse.length + ", а должно быть " + fNum);
            }
            else {
            int [] places = {0 ,1 ,2 ,3 ,4 ,5};
            String sName = "";
            String fName = "";
            String mName = "";
            String gender = "";
            LocalDate birthDay = null;
            long phone = 0;
        for (int i = 0; i < parse.length; i++) {
            if (parse[i].length()<2) {
                if (!"m".equals(parse[i]) && !"f".equals(parse[i])) {
                    System.err.println("Указан не верный пол, введите f или m");
                    return;
                } else {
                    gender = parse[i];
                    places[i] = -1;
                }
            } else if (parse[i].length()>4) {
                if (parse[i].charAt(2) == '.' || parse[i].charAt(3) == '.' || parse[i].charAt(4) == '.') {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        birthDay = LocalDate.parse(parse[i], formatter);
                        places[i] = -1;
                    } catch (DateTimeParseException e){
                        System.out.println("Не верный формат даты");
                        return;
                    }
                } else if (parse[i].charAt(2) != '.' && parse[i].charAt(3) != '.' && parse[i].charAt(4) != '.' && (parse[i].charAt(0) == '0' ||
                    parse[i].charAt(0) == '1' || parse[i].charAt(0) == '2' || parse[i].charAt(0) == '3' || parse[i].charAt(0) == '4' ||
                    parse[i].charAt(0) == '5' || parse[i].charAt(0) == '6' || parse[i].charAt(0) == '7' || parse[i].charAt(0) == '8' ||
                    parse[i].charAt(0) == '9' )) {
                    try {
                        phone = Long.parseLong(parse[i]);
                        places[i] = -1;
                    } catch (NumberFormatException e) {
                        System.err.println("Неверный формат номера телефона");
                        return;
                    }
                }
            }
        }
        if (birthDay == null) {
            System.err.println("Не верный формат даты");
            return;
        }
                for (int i = 0; i < places.length; i++) {
                    if (places[i] != -1) {
                        sName = parse[i];
                        fName = parse[i+1];
                        mName = parse[i+2];
                        places[i+1] = -1;
                        places[i+2] = -1;
                    }
                }
            String fileName = sName + ".txt";
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))){
                    bw.write(sName + " " + fName + " " + mName + " " + birthDay.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + phone + " " + gender);
                    bw.newLine();
                } catch (IOException e) {
                    System.out.println("Ошибка сохранения");
                }
        }

    }
}