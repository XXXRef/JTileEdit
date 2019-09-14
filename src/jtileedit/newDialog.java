package jtileedit;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class newDialog extends JDialog {
	private JLabel xLabel=new JLabel("Width:"),yLabel=new JLabel("Height:");
	JTextField xText=new JTextField(),yText=new JTextField();
	JButton okButton=new JButton("Create"), cancelButton=new JButton("Cancel");
	
	newDialog(){
		super();
		this.setTitle("New World");
		this.setLayout(new GridLayout(3,2,5,5));
		this.setResizable(false);
		this.add(xLabel);
		this.add(xText);
		this.add(yLabel);
		this.add(yText);
		this.add(okButton);
		this.add(cancelButton);
		this.pack();
		
		//this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		okButton.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				int xTemp, yTemp;
				try{
					xTemp=Integer.parseInt(xText.getText());
					yTemp=Integer.parseInt(yText.getText());
				}
				catch(NumberFormatException nfe){
					JOptionPane.showMessageDialog(okButton,"Invalid number format.");
					System.out.println("okButton.addMouseListener.mouseClicked() exception:"+nfe);
					return;
				}
				if((xTemp<1)||(yTemp<1)){
					JOptionPane.showMessageDialog(okButton,"Size must be >0.");
					return;
				}
				setVisible(false);
				dispose();
				
			}
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});
		cancelButton.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				setVisible(false);
				dispose();
			}
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});
	}
}
