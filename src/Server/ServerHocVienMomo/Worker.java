/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.ServerHocVienMomo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author thanh
 */
public class Worker implements Runnable {
	private String myName;
	private Socket socket;
	BufferedReader in;
	BufferedWriter out;

	public Worker(Socket s, String name) throws IOException {
		this.socket = s;
		this.myName = name;
		this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
	}

   
	public void run() {
		System.out.println("Client " + socket.toString() + " accepted");
		try {
                        StringBuilder str = new StringBuilder();
                        str.append(in.readLine());
				System.out.println("Server received: " + str + " from " + socket.toString() + " # Client " + myName);
				for(Worker worker : Server.workers) {
					if(myName.equals(worker.myName)) {
                                            Random random = new Random();
                                            int OTP = random.nextInt(90000) + 10000;
                                            
                                            System.out.println("Sent: " +String.valueOf(OTP));
						worker.out.write(String.valueOf(OTP) + "\n");
						worker.out.flush();
						System.out.println("Server write: " + str + " to " + worker.myName);
                                                final String username = "hoducthangtn2nhvt@gmail.com";
                                                final String password = "16112000@Abc";

                                                Properties prop = new Properties();
                                                        prop.put("mail.smtp.host", "smtp.gmail.com");
                                                prop.put("mail.smtp.port", "587");
                                                prop.put("mail.smtp.auth", "true");
                                                prop.put("mail.smtp.starttls.enable", "true"); //TLS

                                                Session session = Session.getInstance(prop,
                                                        new javax.mail.Authenticator() {
                                                            protected PasswordAuthentication getPasswordAuthentication() {
                                                                return new PasswordAuthentication(username, password);
                                                            }
                                                        });

                                                try {

                                                    Message message = new MimeMessage(session);
                                                    message.setFrom(new InternetAddress("hoducthangtn2nhvt@gmail.com"));
                                                    message.setRecipients(
                                                            Message.RecipientType.TO,
                                                            InternetAddress.parse(str.toString())
                                                    );
                                                    message.setSubject("OTP from 3 anh em with love");
                                                    message.setText(String.valueOf(OTP));

                                                    Transport.send(message);

                                                    System.out.println("Done");

                                                } catch (MessagingException e) {
                                                    e.printStackTrace();
                                                }
						break;
					}
				}
			//}
			System.out.println("Closed socket for client " + myName + " " + socket.toString());
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}

