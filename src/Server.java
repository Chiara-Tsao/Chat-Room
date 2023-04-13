import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	ServerGui gui;
	Server(ServerGui gui) {
		super();
		this.gui = gui;
	}
	public static void main(String[] args) {
		ServerGui gui = new ServerGui ();
		gui.initialize();
		gui.setVisible(true);
		new Server(gui).start();
	}


	public void run() {
		byte[] buf = new byte[1024];
		int n = 0;
		ServerSocket svs = null;
		//Socket s = null;
		try { //listen client

			svs = new ServerSocket(3000);
			gui.taBoard.append("listen 3000"+ "\n");
			gui.s = svs.accept();
			gui.taBoard.append("Client0 "+ "\n");
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		try {
			InputStream in = gui.s.getInputStream();
			OutputStream out = gui.s.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));;
			while (true) {
				System.out.print("");
				//listen
				if(br.ready()) {
					String rmessage;
					n = in.read(buf);
					System.out.println(n);
					if (n == 0) {
						rmessage ="";
					}
					rmessage = new String(buf, 0, n);
					//rmessage += " (from Client)";
					//out.write(rmessage.getBytes());
					gui.taBoard.append("Client0: " + rmessage + "\n");
				}

				if (gui.send_flag == 1) { 
					String message = gui.tfMessage.getText();
					gui.taBoard.append("Server: " + message + "\n");
					out.write(message.getBytes()); //to client
					gui.send_flag = 0;
					gui.tfMessage.setText("");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			svs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
