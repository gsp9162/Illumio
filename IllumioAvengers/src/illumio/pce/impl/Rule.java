package illumio.pce.impl;

public class Rule {
	
	//String direction;
	//String protocol;
	int startPortNumber,endPortNumber;
	String startIPV4Address;
	String endIPV4Address;
	
	public Rule(/*String direction, String protocol, */int startPortNumber, int endPortNumber, String startIPV4Address,String endIPV4Address) {
		//this.direction = direction;
		//this.protocol = protocol;
		this.startPortNumber = startPortNumber;
		this.endPortNumber = endPortNumber;
		this.startIPV4Address = startIPV4Address;
		this.endIPV4Address = endIPV4Address;
	}
	
	public Rule() {
		// TODO Auto-generated constructor stub
	}

	/*public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}*/

	public int getStartPortNumber() {
		return startPortNumber;
	}

	public void setStartPortNumber(int startPortNumber) {
		this.startPortNumber = startPortNumber;
	}

	public int getEndPortNumber() {
		return endPortNumber;
	}

	public void setEndPortNumber(int endPortNumber) {
		this.endPortNumber = endPortNumber;
	}

	public String getStartIPV4Address() {
		return startIPV4Address;
	}

	public void setStartIPV4Address(String startIPV4Address) {
		this.startIPV4Address = startIPV4Address;
	}

	public String getEndIPV4Address() {
		return endIPV4Address;
	}

	public void setEndIPV4Address(String endIPV4Address) {
		this.endIPV4Address = endIPV4Address;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return /*direction+"-"+protocol+"-"+*/"["+startPortNumber+"-"+endPortNumber+"]-"+"["+startIPV4Address+"-"+endIPV4Address+"]";
	}
	
	//To Do
	//Override equals function to don't allow duplicates in the HashSet
	/*@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}*/
}