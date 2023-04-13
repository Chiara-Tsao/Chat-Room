import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

class Client extends Thread {
	ClientGui gui;
	Client(ClientGui gui) {
		super();
		this.gui = gui;
	}
	public static void main(String[] args) {
		ClientGui gui = new ClientGui ();
		gui.initialize();
		gui.setVisible(true);;
	}

	public void run() {
		OutputStream out;
		InputStream in;
		BufferedReader br;
		try {
			out = gui.s.getOutputStream();
			in = gui.s.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));

			int n;
			byte[] buf = new byte[1024];

			while (true) { 
				System.out.print("");
				//
				if(br.ready()) {
					String rmessage;
					n = in.read(buf);

					System.out.println(n);
					if (n == 0) {
						rmessage ="";
					}
					rmessage = new String(buf, 0, n);

					//rmessage += " (from Server)";
					//out.write(rmessage.getBytes());
					gui.taBoard.append("Server: " + rmessage + "\n");
				}

				if (gui.send_flag == 1) { //
					String message = gui.tfMessage.getText();
					gui.taBoard.append("Client0: " + message + "\n");
					out.write(message.getBytes()); //to server
					gui.send_flag = 0;
					gui.tfMessage.setText("");
				}

			}
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
}
