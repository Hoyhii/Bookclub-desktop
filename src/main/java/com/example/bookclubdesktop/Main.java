package com.example.bookclubdesktop;

import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        if (Arrays.asList(args).contains("--stat")){
            Statisztika.main(args);
        }else {
            BookclubGUI.main(args);
        }
    }
}
