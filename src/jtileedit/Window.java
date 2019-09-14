package jtileedit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

//import jtileedit.TexPanel.Rectangle;

class Window extends JFrame{
	
	private JButton newButton,loadButton,saveButton,texButton,aboutButton;
	private JToolBar toolBar;
	private JPanel mainPanel,winPanel;
	private TexPanel texPanel;
	private MatrixPanel worldPanel;
	private JScrollPane texScrollPane, worldScrollPane;
	private AboutDialog aboutDialog;
	newDialog nDialog=new newDialog();
	
	Matrix<Integer> worldMatrix;
	
	{
//var init
		{//button init
			ImageIcon newIcon=createImageIcon("/resources/new.gif");
			ImageIcon loadIcon=createImageIcon("/resources/load.gif");
			ImageIcon saveIcon=createImageIcon("/resources/save.gif");
			ImageIcon texIcon=createImageIcon("/resources/tex.gif");
			ImageIcon aboutIcon=createImageIcon("/resources/about.png");
	
			newButton=new JButton(newIcon);
			loadButton=new JButton(loadIcon);
			saveButton=new JButton(saveIcon);
			texButton=new JButton(texIcon);
			aboutButton=new JButton(aboutIcon);
			
			newButton.setToolTipText("New file");
			loadButton.setToolTipText("Load file");
			saveButton.setToolTipText("Save file");
			texButton.setToolTipText("Load textures");
			aboutButton.setToolTipText("About");
			
			newButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					nDialog.setVisible(true);
				}
			});
			
			loadButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JFileChooser fileChooser=new JFileChooser();
					fileChooser.showOpenDialog(loadButton);
					//TODO its possible to pass File but not path, and if to do so, its not necessary to invoke getAbsolutePath()
					onLoad(fileChooser.getSelectedFile());
				}
			});
			
			saveButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					//check if is filled
					Matrix<Integer> tempMatrix=worldPanel.getMatrix();
					for(int i=0;i<tempMatrix.getRowsNumber();i++){
						for(int j=0;j<tempMatrix.getColsNumber();j++){
							if(tempMatrix.get(j, i)==null){
								JOptionPane.showMessageDialog(saveButton,"Cant save unfilled map. Please fill whole world.");
								return;
							}
						}
					}
					JFileChooser fileChooser=new JFileChooser();
					fileChooser.showSaveDialog(saveButton);
					onSave(fileChooser.getSelectedFile());
				}
			});
			
			texButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JFileChooser fileChooser=new JFileChooser();
					fileChooser.showOpenDialog(texButton);
					loadImages(fileChooser.getSelectedFile().getAbsolutePath());
					worldPanel.revalidate();
					worldPanel.repaint();
				}
			});
			
			aboutButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					//JOptionPane.showMessageDialog(aboutButton, "JTileEditor v0.5, 2014","JTileEditor",JOptionPane.WARNING_MESSAGE);
					aboutDialog=new AboutDialog();
					aboutDialog.setVisible(true);
					
				}
			});
		}
		
		
		
		toolBar=new JToolBar("toolBar");
		//toolBar filling		
		toolBar.add(newButton);
		toolBar.addSeparator();
		toolBar.add(loadButton);
		toolBar.addSeparator();
		toolBar.add(saveButton);
		toolBar.addSeparator();
		toolBar.add(texButton);
		toolBar.addSeparator();
		toolBar.add(aboutButton);
		//BufferedImage bi=createBufferedImage("pic.png");
		
		int texSize=32;
		int colSize=4;
		
		{//tex area init
			texPanel=new TexPanel();
			//texPanel.loadImages("pics/tex_pack1");
			JPanel texTempPanel=new JPanel();
			texTempPanel.setLayout(new BorderLayout());
			texTempPanel.add(texPanel, BorderLayout.NORTH);
			texScrollPane=new JScrollPane(texTempPanel);
			//texScrollPane.setViewportView(texPanel);
			texScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			texScrollPane.setBorder(new TitledBorder("Textures"));
			texScrollPane.setPreferredSize(new Dimension(200,500));
			texPanel.addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent e){
					worldPanel.setIndex(texPanel.getCurrentIndex());
				}
			});
		}
		
		/*
		Dimension tempDimension=texPanel.getPreferredSize();
		int temp_int=tempDimension.width<100?100:tempDimension.width;
		*/
		
		
		{//world area init
			worldPanel=new MatrixPanel();
			JPanel tempWorldPanel=new JPanel();
			tempWorldPanel.add(worldPanel);
			//tempWorldPanel.setBackground(new Color(0,0,0));
			worldScrollPane=new JScrollPane(tempWorldPanel);
			worldScrollPane.setPreferredSize(new Dimension(500,500));
			worldScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			worldScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			worldScrollPane.setBorder(new TitledBorder("World"));
		}

		nDialog.okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int xTemp, yTemp;
				try{
					xTemp=Integer.parseInt(nDialog.xText.getText());
					yTemp=Integer.parseInt(nDialog.yText.getText());
				}
				catch(NumberFormatException nfe){
					System.out.println("nDialog.okButton.addMouseListener.mouseClicked() exception:"+nfe);
					return;
				}
				if((xTemp<1)||(yTemp<1)){
					return;
				}
				worldPanel.resetSize(yTemp, xTemp);
			}
			
		});

//winPanel filling
		winPanel=new JPanel(new FlowLayout());//work area panel
		winPanel.add(worldScrollPane);
		winPanel.add(texScrollPane);
//mainPanel filling
		mainPanel=new JPanel(new BorderLayout());//app main panel
		mainPanel.add(toolBar,BorderLayout.NORTH);
		mainPanel.add(winPanel);
		
		setContentPane(mainPanel);
	}
	
	
	
	Window(String windowName,int width,int height){
		super(windowName);
		//setSize(width,height);
		pack();
		//setResizable(false);
		//setVisible(isVisible);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	Window(){
		this("Win",500,500);
	}
	
	 Window(String name){
		this(name,500,500);
	}
	
	static javax.swing.ImageIcon createImageIcon(String path){
		java.net.URL imgURL= Window.class.getResource(path);
		if(imgURL==null){
			System.out.println("1");
			return null;	
		}
		return new ImageIcon(imgURL);
	}
	
	public static java.awt.image.BufferedImage createBufferedImage(String path){
		java.awt.image.BufferedImage tempImage=null;
		try{
			tempImage=ImageIO.read(new java.io.File(path));
		}
		catch(Exception e){
			System.out.println("createImage: exception: "+e+ " path: "+path
					);
			return null;
		}
		
		
		System.out.println("tempImage=="+tempImage);
		return tempImage;
	}
	
	void loadImages(String path){
		File file=new File(path);
		if((!file.canRead())||(!file.exists())){
			System.out.println("loadImages: file opening error.");
			return;
		}
		BufferedReader br;
		try{
			br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		}catch(Exception e){
			System.out.println("loadImages exception1: "+e);
			return;
		}
		
		String line;
		System.out.println("loadImages: path="+path);
		path=path.substring(0,path.lastIndexOf("\\"));
		ArrayList<BufferedImage> imageMas=new ArrayList<>();
		String texID;
		ArrayList<Associate<Integer,BufferedImage>> imageList=new ArrayList<Associate<Integer,BufferedImage>>();
		try{
			while((line=br.readLine())!=null){
				texID=line.substring(0,line.indexOf(" "));
				line=line.substring(line.indexOf(" ")+1);
				imageList.add(new Associate<Integer,BufferedImage>(Integer.valueOf(texID),Window.createBufferedImage(path+"\\"+line)));
				imageMas.add(Window.createBufferedImage(path+"\\"+line));
			}
			br.close();
		}
		catch(Exception e){
			System.out.println("loadImages exception2: "+e);
			return;
		}
		
		worldPanel.loadImageList(imageList);
		texPanel.setImages(imageMas);
		int add=texPanel.getHeight()>490?18:0;	
		texScrollPane.setSize(new Dimension(texPanel.getWidth()+10+add,500));
		texScrollPane.setPreferredSize(new Dimension(texPanel.getWidth()+10+add,500));
		texScrollPane.setMinimumSize(new Dimension(texPanel.getWidth()+10+add,500));
		texScrollPane.setMaximumSize(new Dimension(texPanel.getWidth()+10+add,500));
		System.out.println("texScrollPane: height="+texScrollPane.getHeight()+" width="+texScrollPane.getWidth());
		System.out.println("texPanel: height="+texPanel.getHeight()+" width="+texPanel.getWidth());
		System.out.println("add="+add);
		pack();
	}
	
	void onLoad(File file){
		FileInputStream fis=null;
		DataInputStream dis=null;
		try{
			fis=new FileInputStream(file);
			dis=new DataInputStream(fis);
				
			Matrix<Integer> loadMatrix=new Matrix<>(dis.readInt(),dis.readInt());
			for(int i=0;i<loadMatrix.getRowsNumber();i++){
				for(int j=0;j<loadMatrix.getColsNumber();j++){
					loadMatrix.set(dis.readInt(), j, i);
				}
			}
			worldPanel.setMatrix(loadMatrix);
		}catch(IOException e){
			JOptionPane.showMessageDialog(loadButton, "Error loading file:"+e);
		}
		finally{
			try{
				if(dis!=null){
					dis.close();
				}
				if(fis!=null){
					fis.close();
				}
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(loadButton, "Error closing load file");
			}
			
		}
	}
	
	void onSave(File file){
		FileOutputStream fos=null;
		DataOutputStream dos=null;
		try{
			fos=new FileOutputStream(file);
			dos=new DataOutputStream(fos);
			
			Matrix<Integer> tempMatrix=worldPanel.getMatrix();
			//JOptionPane.showMessageDialog(loadButton, tempMatrix);
	
			dos.writeInt(tempMatrix.getRowsNumber());
			dos.writeInt(tempMatrix.getColsNumber());
			
			for(int i=0;i<tempMatrix.getRowsNumber();i++){
				for(int j=0;j<tempMatrix.getColsNumber();j++){
					dos.writeInt(tempMatrix.get(j, i));
					//JOptionPane.showMessageDialog(loadButton,"i="+i+" j="+j);
				}
			}
			
		}catch(Exception e){
			//JOptionPane.showMessageDialog(loadButton, "Error saving file:"+e+" oos="+oos);
		}
		finally{
			try{
				if(dos!=null){
					dos.close();
				}
				if(fos!=null){
					fos.close();
				}
			}
			catch(Exception e){
				//JOptionPane.showMessageDialog(loadButton, "Error closing save file");
			}
			
		}
		
	}
}