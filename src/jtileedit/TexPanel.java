package jtileedit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class TexPanel extends JPanel implements MouseListener{
	
	ArrayList<BufferedImage> imageMas;
	
	private int colSize;
	private int texSize;
	
	private int currentIndex=-1;
	
	private class Rectangle{
		int x,y,height,width;
		Rectangle(){
			x=y=height=width=0;
		}
		
		Rectangle(int xPar,int yPar,int heightPar,int widthPar){
			x=xPar;
			y=yPar;
			height=heightPar;
			width=widthPar;
		}
	}
	
	Rectangle selectRect=new Rectangle();
	
	private final String texFileName="map.txt";
	
	{
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	TexPanel(int texSize,int col){
		super();
		this.texSize=texSize;
		this.colSize=col;
		imageMas=null;
		this.setSize(new Dimension(0,0));
		this.setPreferredSize(new Dimension(0,0));
		this.addMouseListener(this);
		//this.setBackground(new Color(0,0,0));
	}
	
	TexPanel(){
		this(32,3);
	}
	
	public void mouseClicked(MouseEvent e){
		
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		if((currentIndex=(e.getX()/texSize+e.getY()/texSize*colSize))<imageMas.size()){
			selectRect=new Rectangle((e.getX()/texSize)*texSize,(e.getY()/texSize)*texSize,texSize,texSize);
			this.repaint();
		}else{
			currentIndex=-1;
		}
	}
	public void mouseReleased(MouseEvent e){}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawImages(g);
//drawing lines
		if(imageMas==null){
			return;
		}
		g.setColor(new Color(0,0,0));
		int add=imageMas.size()%colSize==0?0:1;
		for(int j=0;j<(imageMas.size()/colSize+add+1);j++){
			g.drawLine(0, j*texSize, this.getWidth(), j*texSize);
		}
		for(int i=0;i<colSize;i++){
			g.drawLine((i+1)*texSize, 0, (i+1)*texSize,this.getHeight());
		}
		g.setColor(new Color(0,255,0));
		g.drawRect(selectRect.x, selectRect.y, selectRect.width, selectRect.height);
	}
	
	private void drawImages(Graphics g){
		if(imageMas==null){
			return;
		}
		for(int i=0;i<imageMas.size();i++){
			g.drawImage(imageMas.get(i),(i%colSize)*texSize,(i/colSize)*texSize,texSize,texSize,null);
		}
	}
	
	public int getCurrentIndex(){
		return currentIndex;
	}
	
	void setImages(final ArrayList<BufferedImage> arg){
		imageMas=arg;
		int row=imageMas.size()/colSize;
		row+=imageMas.size()%colSize==0?0:1;
		this.setPreferredSize(new Dimension(colSize*texSize,row*texSize));
		this.setSize(new Dimension(colSize*texSize,row*texSize));
		this.setMinimumSize(new Dimension(colSize*texSize,row*texSize));
		this.setMaximumSize(new Dimension(colSize*texSize,row*texSize));
		this.revalidate();
		this.repaint();
		System.out.println("texPanel: height="+this.getHeight()+" width="+this.getWidth());
	}
	
	int getColSize(){
		return colSize;
	} 
	
	int getTexSize(){
		return texSize;
	}
	
}
