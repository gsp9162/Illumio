package illumio.pce.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;

import illumio.pce.impl.Rule;

public class UtilsImpl {
	
	public static boolean checkIfPortInRangeOrMatches(int portNo,int startPort,int endPort )
	{
		if(endPort == -1)
		{
			return portNo == startPort;
		}
		else 
		{
			if(portNo>=startPort && portNo<=endPort)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if IpV4Add is in the specified range
	 * @param ipv4Add
	 * @param startAdd
	 * @param endAdd
	 * @return true is ipV4add is in the given range
	 * @throws UnknownHostException
	 */
	public static boolean checkIfIpAddInRangeOrMatches(String ipv4Add,String startAdd,String endAdd) throws UnknownHostException
	{
		if(endAdd.equals(""))
		{
			return ipv4Add.equals(startAdd);
		}
		else
		{
			long ipLow = ipToLong(InetAddress.getByName(startAdd));
			long ipHigh = ipToLong(InetAddress.getByName(endAdd));
			long ipToTest = ipToLong(InetAddress.getByName(ipv4Add));
			return (ipToTest >= ipLow && ipToTest <= ipHigh);
		}
	}

	/**
	 *  Helpler function to convert Ip Add to long value
	 * @param ip
	 * @return
	 */
	public static long ipToLong(InetAddress ip) {
		byte[] octets = ip.getAddress();
		long result = 0;
		for (byte octet : octets) {
			result <<= 8;
			result |= octet & 0xff;
		}
		return result;
	}
}
