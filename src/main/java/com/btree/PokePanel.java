package com.btree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by cesar on 2/23/17.
 */
public class PokePanel extends JPanel{
    private JButton b;
    private JTextField t;

    public PokePanel() {

        /**
         * Gets the user input and displays the given Pokemon and a similar one
         * <p>
         * This portion of the code listens for the user to press the search button.
         * Once they do, it looks at every other Pokemon in the Hashmap and
         * calculates the similarity value. When it finds the smallest number, it denotes that
         * as the most similar Pokemon. Then the program displays the Pokemon's  information
         * mapped to the key that the user provided as well as the most similar Pokemon's
         * information
         */

//        b = new JButton("Search");
//        t = new JTextField(10);
//        b.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent event) {
//                int min = Integer.MAX_VALUE;
//                int minIndex = 0;
//                for (int i = 1; i <= dexMax; i++) {
//                    if (hashmap.contains(Integer.toString(i))) {
//                        if (!Integer.toString(i).equals(t.getText())) {
//                            int diff = hashmap.get(t.getText()).compare(hashmap.get(Integer.toString(i)));
//                            if (diff < min) {
//                                min = diff;
//                                minIndex = i;
//                            }
//                        }
//                    }
//                }
//                JOptionPane.showMessageDialog(PokePanel.this,
//                        hashmap.get(t.getText()).toString() + "\n" +
//                "Similar Pokemon: \n" + hashmap.get((Integer.toString(minIndex))).toString());
//            }
//        });
//        add(b);
//        add(t);
//        t.setToolTipText("Please enter a dex number between 1-9");
    }
}
