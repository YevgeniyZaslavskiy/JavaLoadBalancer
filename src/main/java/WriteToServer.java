import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class WriteToServer implements Runnable {

	BufferedOutputStream serverBufOutStream;
	BufferedInputStream clientBufInStream;
	Socket clientSock;
	Socket serverSock;

	public WriteToServer(Socket clientSocket, Socket serverSocket) {
		super();
		this.clientSock = clientSocket;
		this.serverSock = serverSocket;
		
		
		try {
			
			BufferedInputStream clientBufInStream = new BufferedInputStream(clientSock.getInputStream());
			BufferedOutputStream serverBufOutStream = new BufferedOutputStream(serverSock.getOutputStream());
			sendHTTPLine( serverBufOutStream, "GET /privacy/index.pdf HTTP/1.0" );
			sendHTTPLine( serverBufOutStream, "Host: 93.74.110.188" );
			sendHTTPLine( serverBufOutStream, "" );

			this.serverBufOutStream = serverBufOutStream;
			this.clientBufInStream = clientBufInStream;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		while (!Thread.interrupted()) {
			try {
				int message = clientBufInStream.read();
				//System.out.println(message);
				if(message != -1)
				{
					serverBufOutStream.write(message);
					serverBufOutStream.flush();
				}
				else
				{
					serverSock.close();
					clientSock.close();
					return;
				}
				

			} catch (IOException e) {
				System.err.println("Client connection to server closed");
				
				return;

			}
		}
	}

	private void sendHTTPLine(OutputStream os, final String line ) throws IOException {
		os.write( line.getBytes() );
		os.write( 13 );
		os.write( 10 );
	}
	
}
