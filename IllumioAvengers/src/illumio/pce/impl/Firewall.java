package illumio.pce.impl;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

import illumio.pce.utils.Constants;
import illumio.pce.utils.UtilsImpl;

/**
 *  
 * @author patil.gourang
 *
 */
public class Firewall {
	
	private HashMap<String,HashSet<Rule>>  rulesMap = new HashMap<>();
	
	//Initialize the rules map
	public Firewall(String pathToCSVFile)
	{
		this.rulesMap = readAndPopulateCSVData(pathToCSVFile);
	}
	
	
	/**
	 * This method will parse the csv file and prepare a map based on the direction_protocol as key and its corresponding rules in a List
	 * e.g. inbound_tcp -> [{80,192.168.1.2},{(10000-20000),(192.168.1.1-192.168.2.5)}]
	 * @param cSVFileName
	 * @return HashMap containing direction_protocol as key and corresponding Rules as values in a HashSet.
	 */
	public HashMap<String,HashSet<Rule>> readAndPopulateCSVData(String cSVFileName)
	{
		HashMap<String,HashSet<Rule>> rulesMap = new HashMap<>();
		BufferedReader br = null;
		try 
		{
			String line;
			int startPortNo,endPortNo;
			String startIPAdd,endIPAdd;
			br = new BufferedReader(new FileReader(cSVFileName));
			while((line = br.readLine())!=null)
			{
				String arr [] = line.split(",");
				if(arr[2].contains("-"))//arr[2] is ports range
				{
					String [] portNos = arr[2].split("-");
					startPortNo = Integer.parseInt(portNos[0]);
					endPortNo = Integer.parseInt(portNos[1]);
				}
				else
				{
					startPortNo = Integer.parseInt(arr[2]);
					endPortNo = -1;
				}
				if(arr[3].contains("-"))//arr[2] is ipv4 addresses range
				{
					String [] ipAddrs = arr[3].split("-");
					startIPAdd = ipAddrs[0];
					endIPAdd = ipAddrs[1];
				}
				else
				{
					startIPAdd = arr[3];
					endIPAdd = "";
				}
				Rule r = new Rule(/*arr[0],arr[1],*/startPortNo,endPortNo,startIPAdd,endIPAdd);
				String key = arr[0]+"_"+arr[1];//create a key for the map as direction_protocol
				if(rulesMap.containsKey(key))
				{
					rulesMap.get(key).add(r);
				}
				else
				{
					HashSet<Rule> value = new HashSet<>();
					value.add(r);
					rulesMap.put(key, value);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rulesMap;
	}
	
	/**
	 * This method will get the rules corresponding to direction and protocol from the map and then check iteratively with each rule to check if the packet can be accepted.
	 * @param direction
	 * @param protocol
	 * @param port
	 * @param ip_address
	 * @return true if the params matches rules in the rules map
	 * @throws UnknownHostException
	 */
	public boolean accept_packet(String direction,String protocol,Integer port,String ip_address) throws UnknownHostException
	{
		if(checkValidInput( direction, protocol, port, ip_address))
		{
			HashSet<Rule> rules = rulesMap.get(direction+"_"+protocol);//get the rules corresponding to direction and protocol from the map
			if(rules!=null)
			{
				for(Rule r:rules)
				{
					//check if the port number and ip address is in range or matches the rule 
					if(UtilsImpl.checkIfPortInRangeOrMatches(port,r.startPortNumber,r.endPortNumber) && UtilsImpl.checkIfIpAddInRangeOrMatches(ip_address,r.startIPV4Address,r.endIPV4Address))
					{
						return true;
					}
				}
			}
			
		}
		else
		{
			//System.out.print("Invalid Packet Attributes : ");
		}	
		return false;
	}
	
	/**
	 * Check if all the input params are valid
	 * @param direction
	 * @param protocol
	 * @param port
	 * @param ip_address
	 * @return
	 */
	private boolean checkValidInput(String direction, String protocol, Integer port, String ip_address) {
		if(!(Constants.INBOUND.equals(direction) || Constants.OUTBOUND.equals(direction)))
		{
			return false;
		}
		if(!(Constants.TCP.equals(protocol)||Constants.UDP.equals(protocol)))
		{
			return false;
		}
		if(port<0 || port>65535)
		{
			return false;
		}
		Pattern ipv4 = Pattern.compile(Constants.IPV4Pattern); 
		if(!ipv4.matcher(ip_address).matches())
		{
			return false;
		}
		return true;
	}
	
	/*
	public static void main(String[] args) {
		String pathToCSV = "src/rules.txt";
		Firewall f = new Firewall(pathToCSV);
		try {
			System.out.println(f.accept_packet("inbound", "tcp", 80, "192.168.1.2"));
			System.out.println(f.accept_packet("outbound", "tcp", 10234, "192.168.10.11"));
			System.out.println(f.accept_packet("inbound", "tcp", 81, "192.168.1.2"));
			System.out.println(f.accept_packet("inbound", "udp", 24, "52.12.48.92"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	*/
}
