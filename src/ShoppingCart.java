import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class ShoppingCart extends JFrame{

	private JList itemList;
	private JList cartList;
	private JButton addCart;
	private JButton removeCart;
	private JButton reset;
	private JLabel totalLabel;
	private JTextField totalTF;
	private JPanel listPanel;         // To hold components
	 private JPanel buttonPanel; // To hold components
     private JPanel labelPanel;        // To hold the button
	 private double total = 0;
	 private JScrollPane scrollPane1;
	 
     String[] bookList = new String[7]; 
	double[] priceList = new double[7];
	DefaultListModel listModel = new DefaultListModel();
	
	public ShoppingCart() {
		setTitle("Shopping Cart");
		
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	      buildListPanel();
	      
	     buildButtonPanel();
	      
	     buildLabelPanel();
	      
	      add(listPanel, BorderLayout.NORTH);
	      add(buttonPanel,BorderLayout.CENTER);
	     add(labelPanel, BorderLayout.SOUTH);

	      // Pack and display the window.
	      pack();
	      setVisible(true);
	}
	
	private void buildListPanel() {
		listPanel = new JPanel();
		int i = 0;
		
		File file = new File("Bookprices.txt");
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "File " + file + "  not found.\nTry again later.");
			System.exit(0);
		}
		
		while (inputFile.hasNext()) {
			String str = inputFile.nextLine();
			
			String[] strSplit = str.split(",");
			bookList[i] = strSplit[0];
			priceList[i] = Double.parseDouble(strSplit[1]);
			i++;
		}
		inputFile.close();
		
		itemList = new JList(bookList);
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPanel.add(itemList);
		
		
		 cartList = new JList(listModel);
		 cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 cartList.setVisibleRowCount(7);
		 
		 scrollPane1 = new JScrollPane(cartList);
		 listPanel.add(scrollPane1);
	}
	
	private void buildButtonPanel() {
		buttonPanel = new JPanel();
		
		addCart = new JButton("Add Item");
		addCart.addActionListener(new addButtonListener());
		buttonPanel.add(addCart);
		
		removeCart = new JButton("Remove Item");
		removeCart.addActionListener(new removeButtonListener());
		buttonPanel.add(removeCart);
		
		reset = new JButton("Reset");
		reset.addActionListener(new resetButtonListener());
		buttonPanel.add(reset);
	}
	
	private class addButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int selection = itemList.getSelectedIndex();
			
			if (selection != -1) {
			listModel.addElement(bookList[selection]);
			
			total = total + (priceList[selection] * 1.06);
			totalTF.setText("$" + String.format("%.2f",	total));
			}
			
			pack();
		}
		
	}
	
	private class removeButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			int rem = cartList.getSelectedIndex();
			int count = 0;
			if (rem != -1) {
				String selection = cartList.getSelectedValue().toString();
				
				for (int i = 0; i < bookList.length; i++) {
					if (bookList[i].equals(selection)) {
						count = i;
					}
			}
					
				listModel.remove(rem);
				
				total = total - (priceList[count] * 1.06);
				totalTF.setText("$" + String.format("%.2f",	total));
			}
			
		}
		
	}
	
	private class resetButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			listModel.clear();
			totalTF.setText("");
			total = 0;
		}
		
	}
	
	private void buildLabelPanel() {
		labelPanel = new JPanel();
		totalLabel = new JLabel("Total:");
		
		totalTF = new JTextField(10);
		totalTF.setEnabled(false);
		
		labelPanel.add(totalLabel);
		labelPanel.add(totalTF);
	}
}
