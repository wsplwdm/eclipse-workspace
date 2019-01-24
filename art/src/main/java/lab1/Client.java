package lab1;



import lab1.DataFrame;
import lab1.Column;

import lab1.Value;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client {
	private Socket socket = null;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;
	private boolean isConnected = false;
	
	public Client() {

	}

	public void communicate() {

	while (!isConnected) {
	try {
	socket = new Socket("localHost", 4445);
	System.out.println("Connected");
	isConnected = true;
	outputStream = new ObjectOutputStream(socket.getOutputStream());
	//Student student = new Student(1, "Bijoy");
	System.out.println("Object to be written = " + "student");
	outputStream.writeObject("student");

	} catch (SocketException se) {
	se.printStackTrace();
	// System.exit(0);
	} catch (IOException e) {
	e.printStackTrace();
	}
	}
	}
	}