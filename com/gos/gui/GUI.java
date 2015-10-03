package com.gos.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class GUI extends JFrame{
	public static final int WIDTH_DEF = 500;
	public static final int HEIGHT_DEF = 320;
	
	private JPanel panel;
	private JScrollPane scroll;
	private JTextArea text;
	
	private JMenuBar mnBar;
	private JMenu mnFile;
	private JMenu mnEdit;
	private JMenu mnHelp;
	private JMenuItem mnNew;
	private JMenuItem mnOpen;
	private JMenuItem mnSave;
	private JMenuItem mnExit;
	private JMenuItem mnCut;
	private JMenuItem mnCopy;
	private JMenuItem mnPaste;
	private JMenuItem mnAbout;
	
	private JFileChooser fileChooser;
	
	private Document docPad;
	private String tempotary;
	
	public GUI() {
		initialize();
		setMenu();
		setPanel();
		addEvent();
	}
	
	private void setMenu() {
		setJMenuBar(mnBar);
		
		mnBar.add(mnFile);
		mnBar.add(mnEdit);
		mnBar.add(mnHelp);
		
		mnFile.add(mnNew);
		mnFile.add(mnOpen);
		mnFile.add(mnSave);
		mnFile.add(mnExit);
		mnEdit.add(mnCut);
		mnEdit.add(mnCopy);
		mnEdit.add(mnPaste);
		mnHelp.add(mnAbout);
	}
	
	public void showWindow() {
		setVisible(true);
	}
	
	private void initialize() {
		int widthScreen = getToolkit().getScreenSize().width;
		int heightScreen = getToolkit().getScreenSize().height;
		
		setBounds((widthScreen - WIDTH_DEF) / 2, (heightScreen - HEIGHT_DEF) / 2, WIDTH_DEF, HEIGHT_DEF);
		setTitle("Notepad Fake");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBorder(new EmptyBorder(2, 2, 2, 2));
		
		scroll = new JScrollPane();
		panel.add(scroll, "Center");
		
		docPad = new PlainDocument();
		
		text = new JTextArea(docPad);
		text.setAlignmentX(0.5f);
		text.setAlignmentY(0.5f);
		text.setAutoscrolls(true);
		text.setFont(new Font("CourierNew", Font.PLAIN, 15));
		text.setTabSize(4);
		scroll.setViewportView(text);
		
		mnBar = new JMenuBar();
		
		mnFile = new JMenu("File");
		mnFile.setMnemonic(KeyEvent.VK_F);
		mnEdit = new JMenu("Edit");
		mnEdit.setMnemonic(KeyEvent.VK_E);
		mnHelp = new JMenu("Help");
		mnHelp.setMnemonic(KeyEvent.VK_H);
		
		mnNew = new JMenuItem("New");
		mnNew.setMnemonic(KeyEvent.VK_N);
		mnOpen = new JMenuItem("Open");
		mnOpen.setMnemonic(KeyEvent.VK_O);
		mnSave = new JMenuItem("Save");
		mnSave.setMnemonic(KeyEvent.VK_S);
		mnExit = new JMenuItem("Exit");
		mnExit.setMnemonic(KeyEvent.VK_Q);
		mnCut = new JMenuItem("Cut");
		mnCut.setMnemonic(KeyEvent.VK_X);
		mnCopy = new JMenuItem("Copy");
		mnCopy.setMnemonic(KeyEvent.VK_C);
		mnPaste = new JMenuItem("Paste");
		mnPaste.setMnemonic(KeyEvent.VK_V);
		mnAbout = new JMenuItem("About");
		mnAbout.setMnemonic(KeyEvent.VK_A);
		
		fileChooser = new JFileChooser();
	}
	
	private void setPanel() {
		this.getContentPane().add(panel, "Center");
	}
	
	private void addEvent() {
		mnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		mnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!text.getText().equals("")) text.setText("");
			}
		});
		
		mnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVar = fileChooser.showOpenDialog(rootPane);
				if (returnVar == JFileChooser.APPROVE_OPTION) {
					getTextFromFile(fileChooser.getSelectedFile());
				}
			}
		});
		
		mnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVar = fileChooser.showSaveDialog(rootPane);
				if (returnVar == JFileChooser.APPROVE_OPTION) {
					saveTextToFile(fileChooser.getSelectedFile());
				}
			}
		});
		
		mnCut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tempotary = text.getSelectedText();
				try {
					int startPos = text.getSelectionStart();
					int endPos = text.getSelectionEnd();
					docPad.remove(startPos, endPos - startPos);
				} catch (BadLocationException bLEx) {
					System.out.println(bLEx.getMessage());
				}
			}
		});
		
		mnCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tempotary = text.getSelectedText();
			}
		});
		
		mnPaste.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int startPos = text.getSelectionStart();
				int endPos = text.getSelectionEnd();
				if (endPos > startPos && tempotary.length() > 0) text.replaceRange(tempotary, startPos, endPos); 
				else text.insert(tempotary, text.getSelectionStart());
			}
		});
		
		mnAbout.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				String infor = "Name: Fake Notepad\n" + "Version: 0.0.1\n" + "Author: GoLoCe";
				JOptionPane.showConfirmDialog(rootPane, infor, "About this program", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
	}

	private void getTextFromFile(File selectedFile) {
		if (!text.getText().equals("")) text.setText("");
		try {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile)))) {
                text.append(bufferedReader.readLine());
                while (bufferedReader.ready()) {
                    text.append("\n");
                    text.append(bufferedReader.readLine());
                }
            }
 		} catch (FileNotFoundException fileEx) {
 			System.out.println(fileEx.getMessage());
 		} catch (IOException ioEx) {
 			System.out.println(ioEx.getMessage());
 		}
	}
	
	private void saveTextToFile(File selectedFile) {
		int currentOffset = docPad.getStartPosition().getOffset();
		int endOffset = docPad.getEndPosition().getOffset();
		try {
            try (PrintWriter printWriter = new PrintWriter(selectedFile)) {
                while (currentOffset <= endOffset) {
                    if ((endOffset - currentOffset) > 100)
                        printWriter.print(text.getText(currentOffset, 100));
                    else {
                        printWriter.print(text.getText(currentOffset, endOffset - currentOffset));
                        break;
                    }
                    printWriter.flush();
                    currentOffset += 100;
                }
            }
		} catch (FileNotFoundException | BadLocationException fileEx) {
			System.out.println(fileEx.getMessage());
		}
	}
}
