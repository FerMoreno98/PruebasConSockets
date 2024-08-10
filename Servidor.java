package chat;

import java.awt.*;
import java.io.*;

import java.net.*;

import javax.swing.*;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MarcoServidor m=new MarcoServidor();
		
		m.setVisible(true);
		
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}

class MarcoServidor extends JFrame implements Runnable{
	
	LaminaServidor l;
	
	public MarcoServidor() {
		
		setTitle("Servidor");
		
		setBounds(1200,300,250,350);
		
		l=new LaminaServidor();
		
		add(l);
		
		Thread mihilo=new Thread(this);
		
		mihilo.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			
			
			
			ServerSocket servidor=new ServerSocket(9999);
			
			Socket misocket=servidor.accept();
			
			DataInputStream flujoEntrada=new DataInputStream(misocket.getInputStream());
			
			String mensajeTexto=flujoEntrada.readUTF();
			
			l.getArea().append("\n" + mensajeTexto);
			
			misocket.close();
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

class LaminaServidor extends JPanel{
	
	private JTextArea area;
	
	public LaminaServidor() {
		
		
		
		setLayout(new BorderLayout());
		
		area=new JTextArea(10,20);
		
		JScrollPane panel=new JScrollPane(area);
		
		add(panel,BorderLayout.CENTER);
		
		this.area=area;
		
	}
	
	public JTextArea getArea() {
		
		return area;
	}
	
}


