package jtileedit;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutDialog extends JDialog{
	private JButton okButton=new JButton("OK");
	JPanel mainPane=new JPanel();
	private String caption="<html><h1>JTileEditor<h1></html>";
	private String text="<html>name:JTileEditor<br>"
			+"version: 1.0<br>"
			+"author: Vlad Salnikov<br>"
			+"date:08/2014-09/2014<br>"
			+"</html>"; 
	private JLabel textLabel=new JLabel(text),captionLabel=new JLabel(caption);
	
	
	AboutDialog(){
		super();
		this.setTitle("JTileEditor-About");
		this.setPreferredSize(new Dimension(400,500));
		mainPane.setLayout(new GridLayout(3,1,10,10));
		mainPane.add(captionLabel);
		mainPane.add(textLabel);
		mainPane.add(okButton);
		this.setContentPane(mainPane);
		pack();
		
		okButton.addMouseListener(new MouseListener(){
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
