package chat;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.net.*;

import javax.swing.*;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente m=new MarcoCliente();
		
		m.setVisible(true);
		
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}

}
class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
	setTitle("Cliente");
	
	setBounds(1200,300,250,350);
	
	add(new LaminaCliente());
	
	
	}
	
	
}

class LaminaCliente extends JPanel{
	
	private JTextField areaTexto;
	
	private JLabel cliente;
	
	private JButton enviar;
	
	public LaminaCliente() {
		
		setLayout(new BorderLayout());
		
		JPanel miLamina=new JPanel();
		
		miLamina.add(cliente=new JLabel("Cliente"));
		
		miLamina.add(areaTexto=new JTextField(20));
		
		miLamina.add(enviar=new JButton("Enviar"));
		
		add(miLamina,BorderLayout.CENTER);
		
		
		
		enviar.addActionListener(new EnviaTexto());
		
		
		
		
		
	}
	
	private class EnviaTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			try {
				//Creamos el socket que hace de puente entre el cliente y el servidor
				Socket miSocket=new Socket("192.168.56.1",9999);
				//creamos un dataoutputStream para crear un flujo de datos de salida
				DataOutputStream flujoSalida=new DataOutputStream(miSocket.getOutputStream());
				//le decimos que lo escriba
				flujoSalida.writeUTF(areaTexto.getText());
				
				flujoSalida.close();
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
			
		}
		
	}
}