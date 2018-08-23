package illumio.pce.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import illumio.pce.impl.Firewall;

public class TestFirewall {
	
	public static void main(String[] args) throws IOException {
		
		String pathToRules = "src/rules.csv";
		String pathToTestCases = "src/testcases.csv";
		
		Firewall f = new Firewall(pathToRules);
		
		BufferedReader br = new BufferedReader(new FileReader(pathToTestCases));
		String line;
		StringBuffer sb = new StringBuffer();
		while((line = br.readLine())!=null)
		{
			if(!line.contains("*"))
			{
				String [] params = line.split(",");
				String message = f.accept_packet(params[0], params[1], Integer.parseInt(params[2]), params[3]) ? "Packet Accepted" : "Packet Rejected";
				sb.append(message+"\n");
			}
		}
		System.out.println(sb.toString());
	}

}
