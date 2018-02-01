package pl.szczerbiak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicTacToeFrame_NxN extends JFrame implements ActionListener {

    private int gridSize;
    private List<Integer> gridArray; // Array: [rows, columns, diags]
    private int counter = 0; // number of counts
    private int index; // index of active button
    private List<JButton> buttons = new ArrayList<>();

    public TicTacToeFrame_NxN(String title, int width) {
        super(title);
        setGridSize();
        initializeGridArray(gridSize);
        setSize(width, width); // height, width
        setVisible(true);
        for (int i = 1; i <= gridSize * gridSize; i++) {
            JButton jButton = new JButton("");
            jButton.setFont(new Font("Arial", Font.PLAIN, 40));
            jButton.addActionListener(this); // what to do after clicking
            add(jButton);
            buttons.add(jButton);
        }
        setLayout(new GridLayout(gridSize, gridSize));
    }

    public void setGridSize() {
        int gridSize = Integer.parseInt((JOptionPane.showInputDialog(null, "Enter grid size:")));
        this.gridSize = gridSize;
    }

    public void initializeGridArray(int gridSize) {
        gridArray = new ArrayList<>(Collections.nCopies(gridSize * 2 + 2, 0));
    }

    public void updateGridArray(int value) {
        int row = index / gridSize;
        int col = index % gridSize;
        // Update suitable elements:
        // -- rows and columns
        gridArray.set(row, gridArray.get(row) + value);
        gridArray.set(gridSize + col, gridArray.get(gridSize + col) + value);
        // -- diagonals
        if (row == col)
            gridArray.set(gridSize * 2, gridArray.get(gridSize * 2) + value);
        if (gridSize - 1 - row == col)
            gridArray.set(gridSize * 2 + 1, gridArray.get(gridSize * 2 + 1) + value);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        button.setEnabled(false);
        index = buttons.indexOf(button);

        if (counter % 2 == 0) {
            button.setText("X");
            UIManager.getDefaults().put("Button.disabledText", Color.RED);
            updateGridArray(1); // increment
        } else {
            button.setText("O");
            UIManager.getDefaults().put("Button.disabledText", Color.GREEN);
            updateGridArray(-1); // decrement
        }
        counter++;
        // Check whether there is a winner or draw

        if (isWinner() || isDraw()) {
            System.exit(0);
        }
    }

    public boolean isWinner() {
        for (Integer element : gridArray) {
            if (element == gridSize) {
                JOptionPane.showMessageDialog(null, "X won!");
                return true;
            }
            if (element == -gridSize) {
                JOptionPane.showMessageDialog(null, "O won!");
                return true;
            }
        }
        return false;
    }

    public boolean isDraw() {
        if (counter == gridSize * gridSize) {
            JOptionPane.showMessageDialog(null, "Draw!");
            return true;
        }
        return false;
    }
}