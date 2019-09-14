package jtileedit;

public class Main {
	
	public static void main(String[] args) {
		/*
		JFrame frame=new JFrame("Frame");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		*/
		
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				createAndShowGUI();
			}
		});
		
		
		
		
		/*
		Matrix<Integer> m=new Matrix<>(3,2);
		
		for(int i=0;i<6;i++){
			m.set(i, i%2, i/2);
		}
		
		m.print();
		m.fill(10);
		m.print();
		m.reset(1, 6);
		m.fill(-1);
		m.print();
		
		Matrix<Number> m1=new Matrix<>(m);
		m1.print();
		*/
	}
	
	public static void createAndShowGUI(){
		Window w=new Window("JTileEditor");
		w.setVisible(true);
	}

}
