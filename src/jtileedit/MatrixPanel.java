package jtileedit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MatrixPanel extends JPanel implements MouseListener, MouseMotionListener{
	Matrix<Integer> mtrx;
	ArrayList<Associate<Integer,BufferedImage>> imageList=new ArrayList<Associate<Integer,BufferedImage>>();
	int texSize=32;
	int index=-1;
	boolean isButton1MousePressed=false;
	class SelectedArea{
		 int x,y,xh,yh;
		 
		 SelectedArea(){
			 x=y=xh=yh=0;
		 }
		 
		 SelectedArea(int x,int y, int xh,int yh){
			 this.x=x;
			 this.y=y;
			 this.xh=xh;
			 this.yh=yh;
		 }
	}
	SelectedArea selectedArea=new SelectedArea();
	
	
	MatrixPanel(){
		this(0,0);
	}
	
	MatrixPanel(int rows,int cols){
		super();
		mtrx=new Matrix<>(rows,cols);
		//this.imageList=imageList;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setPreferredSize(new Dimension(0,0));
		this.setBackground(new Color(0,0,0));
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
//draw matrix
		g.setColor(new Color(255,255,255));
		Integer tempInt;
		for(int i=0;i<mtrx.getRowsNumber();i++){
			for(int j=0;j<mtrx.getColsNumber();j++){
				tempInt=mtrx.get(j, i);
				for(Associate<Integer,BufferedImage> iter: imageList){
					if(tempInt==iter.getK()){
						g.drawImage(iter.getV(), j*texSize, i*texSize, texSize, texSize, null);
						break;
					}
					//not found
				}
				
				
				//if(mtrx.get(j, i)==null){
					g.drawRect(j*texSize, i*texSize, texSize, texSize);
				//}
			}
		}
		//for(int i=0;i<)
		g.setColor(new Color(0,0,255));
		g.drawRect(selectedArea.x,selectedArea.y, selectedArea.xh,selectedArea.yh);
	}
	
	public boolean resetSize(int rows, int cols){
		boolean result=mtrx.reset(rows, cols);
		this.setPreferredSize(new Dimension(cols*texSize+1,rows*texSize+1));
		selectedArea=new SelectedArea();
		if(result==true){
			this.revalidate();
			this.repaint();
		}
		return result;
	}
	
	void setMatrix(Matrix<Integer> mPar){
		mtrx=mPar;
		this.setPreferredSize(new Dimension(mtrx.getColsNumber()*texSize+1,mtrx.getRowsNumber()*texSize+1));
		selectedArea=new SelectedArea();
		this.revalidate();
		this.repaint();
	}
	
	Matrix<Integer> getMatrix(){
		return mtrx;
	}
	
	void setIndex(int par){
		index=par;
	}
	
	void loadImageList(ArrayList<Associate<Integer,BufferedImage>> imageList){
		this.imageList=imageList;
		selectedArea=new SelectedArea();
		this.revalidate();
		this.repaint();
	}
	
	public void mouseClicked(MouseEvent e){
		
		
		if(index!=-1){
			mtrx.set(imageList.get(index).getK(), e.getX()/texSize, e.getY()/texSize);
		}
		
		selectedArea.x=e.getX()/texSize*texSize;
		selectedArea.y=e.getY()/texSize*texSize;
		selectedArea.xh=selectedArea.yh=texSize;
		this.revalidate();
		this.repaint();
		
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		selectedArea.x=e.getX()/texSize*texSize;
		selectedArea.y=e.getY()/texSize*texSize;
		selectedArea.xh=selectedArea.yh=texSize;
		if(e.getButton()==MouseEvent.BUTTON1){
			isButton1MousePressed=true;
		}
		this.repaint();
		this.revalidate();
	}
	public void mouseReleased(MouseEvent e){
		if(e.getButton()==MouseEvent.BUTTON1){
			isButton1MousePressed=false;
		}
		if(e.getButton()==MouseEvent.BUTTON3){
		int tempGetX=selectedArea.xh=e.getX()/texSize*texSize;
		int tempGetY=selectedArea.yh=e.getY()/texSize*texSize;
		
		if(selectedArea.x<selectedArea.xh){//x axis
			selectedArea.xh-=selectedArea.x;
		}else{
			selectedArea.xh=selectedArea.x-selectedArea.xh;
			selectedArea.x=tempGetX;
		}
			
		if(selectedArea.y<selectedArea.yh){//y axis
			selectedArea.yh-=selectedArea.y;
		}else{
			selectedArea.yh=selectedArea.y-selectedArea.yh;
			selectedArea.y=tempGetY;
		}
			
		selectedArea.xh+=texSize;
		selectedArea.yh+=texSize;
		
		//bounds checking
		if(selectedArea.x<0){
			selectedArea.xh+=selectedArea.x;
			selectedArea.x=0;
			
		}
		if(selectedArea.y<0){
			selectedArea.yh+=selectedArea.y;
			selectedArea.y=0;
		}
		if((selectedArea.x+selectedArea.xh)>=this.getWidth()){
			selectedArea.xh=(this.getWidth()-selectedArea.x)/texSize*texSize;
		}
		if((selectedArea.y+selectedArea.yh)>=this.getHeight()){
			selectedArea.yh=(this.getHeight()-selectedArea.y)/texSize*texSize;
		}
		
		//matrix filling
		if(index!=-1){
			for(int j=selectedArea.y/texSize;j<(selectedArea.y+selectedArea.yh)/texSize;j++){
				for(int i=selectedArea.x/texSize;i<(selectedArea.x+selectedArea.xh)/texSize;i++){
					mtrx.set(imageList.get(index).getK(),i,j);
				}
			}
		}
		
		this.repaint();
		this.revalidate();
		}
		/*
		int tempGetX=selectedArea.xh=e.getX()/texSize*texSize;//actually its not height, just saving result 
		int tempGetY=selectedArea.yh=e.getY()/texSize*texSize;
		
		
		if((selectedArea.xh!=selectedArea.x)||(selectedArea.yh!=selectedArea.y)){//
			
			if(selectedArea.xh<0){
				tempGetX=selectedArea.xh=0;
				JOptionPane.showMessageDialog(null, "electedArea.xh<0");
			}
			if(selectedArea.xh>=this.getWidth()){
				tempGetX=selectedArea.xh=this.getWidth()-1;
				JOptionPane.showMessageDialog(null, "selectedArea.xh>=this.getWidth()");
			}
			if(selectedArea.yh<0){
				tempGetY=selectedArea.yh=0;
			}
			if(selectedArea.yh>=this.getHeight()){
				tempGetY=selectedArea.yh=this.getHeight()-1;
			}
			System.out.println("mouseReleased:\n\tthis.getWidth():"+this.getWidth()+" this.getHeight():"+this.getHeight());
			
			if(selectedArea.x<tempGetX){
				selectedArea.xh=tempGetX-selectedArea.x;
			}else{
				selectedArea.xh=selectedArea.x-tempGetX;
				selectedArea.x=tempGetX;
			}
		
			if(selectedArea.y<tempGetY){
				selectedArea.yh=tempGetY-selectedArea.y;
			}else{
				selectedArea.yh=selectedArea.y-tempGetY;
				selectedArea.y=tempGetY;
			}
			tempGetX=e.getX()/texSize*texSize;
			tempGetY=e.getY()/texSize*texSize;
			if((tempGetX>=0)&&(tempGetX<this.getWidth())){
				System.out.println("\tx++");
				selectedArea.xh+=texSize;
			}
			if((tempGetY>=0)&&(tempGetY<this.getHeight())){
				System.out.println("\tx++");
				selectedArea.yh+=texSize;
			}
		}else{
			selectedArea.xh=selectedArea.yh=texSize;
		}
		System.out.println("selectedArea:\n\tx="+selectedArea.x+"\n\ty="+selectedArea.y+"\n\txh="+selectedArea.xh+"\n\tyh="+selectedArea.yh);
		this.revalidate();
		this.repaint();
		*/
	}
	public void mouseMoved(MouseEvent e){
	
		
	}
	public void mouseDragged(MouseEvent e){
		if(isButton1MousePressed){
			selectedArea.x=e.getX()/texSize*texSize;
			selectedArea.y=e.getY()/texSize*texSize;
			if(index!=-1){
				mtrx.set(imageList.get(index).getK(),e.getX()/texSize,e.getY()/texSize);
			}
			this.repaint();
			this.revalidate();
		}
	}
	
	
}
