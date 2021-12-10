package Server.ServerHocVienMomo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author thanh
 */
public class Server {

    public static int port = 1237;
    public static int numThread = 2;
    private static ServerSocket server = null;
    public static Vector<Worker> workers = new Vector<>();

    public static void main(String[] args) throws IOException {
        int i = 0;
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        try {
            server = new ServerSocket(port);
            System.out.println("Server binding at port " + port);
            System.out.println("Waiting for client...");
            while (true) {
                i++;
                Socket socket = server.accept();
                Worker client = new Worker(socket, Integer.toString(i));
                workers.add(client);
                executor.execute(client);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (server != null) {
                server.close();
            }
        }
    }
//public static void main(String args[]) throws AddressException, MessagingException{
//        try {
//            ServerSocket server = new ServerSocket(1615);
//            System.out.println("Listening...");
//            while(true){
//                Socket socker = server.accept();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(socker.getInputStream()));
//                PrintWriter writer = new PrintWriter(new OutputStreamWriter(socker.getOutputStream()));
//
//                StringBuilder str = new StringBuilder();
//                str.append(reader.readLine());
////                String line = reader.readLine();
//
//                System.out.println("Received: " +str);
//                Random random = new Random();
//                int OTP = random.nextInt(90000) + 10000;
//
//                System.out.println("Sent: " +String.valueOf(OTP));
//
//                writer.println(String.valueOf(OTP));
//                writer.flush();
//
//
//                final String username = "hoducthangtn2nhvt@gmail.com";
//                final String password = "16112000@Abc";
//
//                Properties prop = new Properties();
//                        prop.put("mail.smtp.host", "smtp.gmail.com");
//                prop.put("mail.smtp.port", "587");
//                prop.put("mail.smtp.auth", "true");
//                prop.put("mail.smtp.starttls.enable", "true"); //TLS
//
//                Session session = Session.getInstance(prop,
//                        new javax.mail.Authenticator() {
//                            protected PasswordAuthentication getPasswordAuthentication() {
//                                return new PasswordAuthentication(username, password);
//                            }
//                        });
//
//                try {
//
//                    Message message = new MimeMessage(session);
//                    message.setFrom(new InternetAddress("hoducthangtn2nhvt@gmail.com"));
//                    message.setRecipients(
//                            Message.RecipientType.TO,
//                            InternetAddress.parse(str.toString())
//                    );
//                    message.setSubject("OTP from 3 anh em with love");
//                    message.setText(String.valueOf(OTP));
//
//                    Transport.send(message);
//
//                    System.out.println("Done");
//
//                } catch (MessagingException e) {
//                    e.printStackTrace();
//                }
//
//
//                        reader.close();
//                        writer.close();
//                        socker.close();
//
//                    }
//                } catch (IOException ex) {
//                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//                }
    // }
}
