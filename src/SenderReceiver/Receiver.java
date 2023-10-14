package SenderReceiver;

import Chess.ChessAccord;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Receiver implements Runnable, ChessAccord {
	ArrayList<String> ip_list;
	Socket receiver;
	static boolean isConnect = false;

	public Receiver () {
		ip_list = new ArrayList<>();
	}

	public ArrayList<String> getIp_list() {
		return ip_list;
	}

	public static boolean isConnect() {
		return isConnect;
	}

	public  void joinHouse(String address) throws IOException, InterruptedException {
		receiver = new Socket(address, ChessAccord.DEFAULT_PORT);
		isConnect = true;
		new SecondHead(receiver).start();
	}




	@Override
	public void run() {
		try (DatagramSocket receiver = new DatagramSocket(DEFAULT_PORT)){
			while (!isConnect) {
				byte[] buf = new byte[10];// 缓冲数组
				DatagramPacket message_packet = new DatagramPacket(buf, buf.length);
				receiver.setSoTimeout(1500);
				receiver.receive(message_packet);
				String ip  = message_packet.getAddress().getHostAddress();
				if (!ip_list.contains(ip)) ip_list.add(ip);
			}
		} catch (SocketTimeoutException e) {

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
