package SenderReceiver;

import Chess.ChessAccord;

import java.io.*;
import java.net.*;

public class Sender implements Runnable, ChessAccord {
	static boolean isConnect = false;
	Socket sender;

	public static boolean isConnect() {
		return isConnect;
	}

	public void creatHouse () throws IOException {
		sender = new ServerSocket(DEFAULT_PORT, 50 , InetAddress.getLocalHost()).accept();
		isConnect = true;
		new FirstHead(sender).start();
	}

	@Override
	public void run() {
		try (DatagramSocket sender_broadcast = new DatagramSocket()){
			InetAddress address = InetAddress.getByName("255.255.255.255");
			String message = " ";
			DatagramPacket message_packet = new DatagramPacket(message.getBytes(), message.length(), address, DEFAULT_PORT);
			while (!isConnect) {
				sender_broadcast.send(message_packet);
				Thread.sleep(100);
			}
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
