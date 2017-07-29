package com.dd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.dd.Main.CURRENT_VERSION;


public class mainFrame {

    private JPanel mainPanel;
    private JButton начатьПарсингButton;
    private JTextArea textArea1;
    private JTextField textField1;
    private JButton button1;
    private JButton button2;

    mainFrame(){
        JFrame frame = new JFrame("VKparser by DD ver " + CURRENT_VERSION);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //обязательно! завершение программы при закрытии этой формы
        frame.setVisible(true); //обязательно!
        frame.setSize(1024, 768);
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setResizable(false);
        frame.setLayout(new GridLayout());
        frame.setLocationRelativeTo(null); //окно появиться по середине экрана
        frame.setContentPane(mainPanel); //эта строчка отрисовывает то, что нарисовано мышкой в GUI EDITOR, panel1 это корневой элемент оттуда

textArea1.append("Program loaded.");

    }
}
