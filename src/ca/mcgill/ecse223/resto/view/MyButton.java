package ca.mcgill.ecse223.resto.view;

import java.awt.Color;

import javax.swing.JButton;

public class MyButton extends JButton {
	
	 public MyButton(String text) {
         super(text);
         
         super.setBackground(new Color(241, 239, 241));
         super.setOpaque(true);
         super.setBorder(new RoundedBorder(10, new Color(176, 190, 196)));
         
     }
	 
	 public MyButton() {
         this("");
     }
 }
