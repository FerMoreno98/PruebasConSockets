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

class LaminaCliente extends JPanel implements Runnable{
	
	private JTextField areaTexto,nick,ip;
	
	private JLabel cliente;
	
	private JButton enviar;
	
	private JTextArea campochat;
	
	public LaminaCliente() {
		
		setLayout(new BorderLayout());
		
		JPanel miLamina=new JPanel();
		
		miLamina.add(nick=new JTextField(5));
		
		miLamina.add(cliente=new JLabel("CHAT"));
		
		miLamina.add(ip=new JTextField(10));
		
		miLamina.add(campochat=new JTextArea(12,20));
		
		campochat.setEditable(false);
		
		miLamina.add(areaTexto=new JTextField(20));
		
		miLamina.add(enviar=new JButton("Enviar"));
		
		
		
		add(miLamina,BorderLayout.CENTER);
		
		
		
		enviar.addActionListener(new EnviaTexto());
		
		Thread mihilo=new Thread(this);
		
		mihilo.start();
		
		
		
		
		
	}
	
	private class EnviaTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			try {
				//Creamos el socket que hace de puente entre el cliente y el servidor
				Socket miSocket=new Socket("192.168.56.1",9999);
				
				PaqueteEnvio datos=new PaqueteEnvio();
				
				datos.setNick(nick.getText());
				
				datos.setIp(ip.getText());
				
				datos.setMensaje(areaTexto.getText());
				
				ObjectOutputStream paqueteDatos=new ObjectOutputStream(miSocket.getOutputStream());
				
				paqueteDatos.writeObject(datos);
				
				miSocket.close();
				//creamos un dataoutputStream para crear un flujo de datos de salida
			/*	DataOutputStream flujoSalida=new DataOutputStream(miSocket.getOutputStream());
				//le decimos que lo escriba
				flujoSalida.writeUTF(areaTexto.getText());
				
				flujoSalida.close();*/
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
			
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			
			ServerSocket servidorCliente=new ServerSocket(9090);
			
			Socket cliente;
			
			PaqueteEnvio paqueteRecibido;
			
			while(true) {
				
				cliente=servidorCliente.accept();
				
				ObjectInputStream flujoEntrada=new ObjectInputStream(cliente.getInputStream());
				
				paqueteRecibido=(PaqueteEnvio) flujoEntrada.readObject();
				
				campochat.append("\n" + paqueteRecibido.getNick() + ": "+ paqueteRecibido.getMensaje());
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
	}
}

class PaqueteEnvio implements Serializable{
	
	private String nick,ip,mensaje;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	
}