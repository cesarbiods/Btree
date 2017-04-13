package com.btree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by cesar on 2/23/17.
 */
public class PokePanel extends JPanel{
    private JButton b;
    private JTextField t;
    Tree tr;
    Kmeans kmeans;

    public PokePanel() throws IOException {
        tr = new Tree();
        tr.root = tr.recreateTree();
        kmeans = new Kmeans();
        kmeans.init(tr, 721);
        kmeans.calculate();

        /**
         * Var dexMax
         * <p>
         * This variable determines how many Pokemon are loaded onto
         * the Cache at the start.
         */

        int dexMax = 721;

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

        b = new JButton("Search");
        t = new JTextField(10);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int min = Integer.MAX_VALUE;
                int minIndex = 0;
                for (int i = 1; i <= dexMax; i++) {
                        if (!Integer.toString(i).equals(t.getText())) {
                            int diff = 0;
                            try {
                                diff = tr.search(tr.root,Integer.parseInt(t.getText())).get().compare(tr.search(tr.root, i).get());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (diff < min) {
                                min = diff;
                                minIndex = i;
                            }
                        }
                }
                try {
                    JOptionPane.showMessageDialog(PokePanel.this,
                            tr.search(tr.root, Integer.parseInt(t.getText())).get().toString() + "\n" +
                    "Similar Pokemon: \n" + tr.search(tr.root, minIndex).get().toString() + "\n" + kmeans.plotClusters()) ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
            }
        });
        add(b);
        add(t);
        t.setToolTipText("Please enter a dex number between 1-1442");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new GUI();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
