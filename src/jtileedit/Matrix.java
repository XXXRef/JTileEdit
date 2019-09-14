package jtileedit;

import java.util.ArrayList;

public class Matrix<T> {
	private ArrayList<ArrayList<T>> tp;
	private int rows;
	private int cols;
	
	public Matrix(){
		tp=null;
		rows=cols=0;
	}
	public Matrix(int rows,int cols){
		if(!reset(rows,cols)){
			tp=null;
			rows=cols=0;
			return;
		}
	}
	public Matrix(Matrix<? extends T> m){
		this.reset(m.getRowsNumber(), m.getColsNumber());
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				this.set(m.get(j, i), j, i);
			}
		}
	}
	
	public boolean reset(int rows,int cols){
		if(rows<0 || cols<0){
			return false;
		}
		this.rows=rows;
		this.cols=cols;
		tp=new ArrayList<>(rows);
		for(int counter=0;counter<rows;counter++){
			tp.add(new ArrayList<T>(cols));
		}
		for(ArrayList<T> iter:tp){
			for(int counter=0;counter<cols;counter++){
				iter.add(null);
			}
		}
		return true;
	}
	
	public void fill(T val){
		for(ArrayList<T> iter1:tp){
			for(int counter=0;counter<iter1.size();counter++){
				iter1.set(counter, val);
			}
		}
	}
	
	public T get(int x,int y){
		return tp.get(y).get(x);
	}
	
	public boolean set(T val,int x,int y){
		if(x<0 || x>=cols || y<0 || y>=rows){
			return false;
		}
		ArrayList<T> temp=tp.get(y);
		temp.set(x, val);
		return true;
	}
	
	public int getColsNumber(){
		return cols;
	}
	
	public int getRowsNumber(){
		return rows;
	}
	
	public void print(){
		for(ArrayList<T>iter1:tp){
			for(T iter2:iter1){
				System.out.print(iter2+" ");
			}
			System.out.println();
		}
	}
}
