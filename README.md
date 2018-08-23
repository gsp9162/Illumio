# Illumio
Illumio Coding Challenge


1.    Testing the solution
The file testcases.csv file which contains all the default other tests which will check the boundary conditions. Every line of the CSV file contains the following parameters like direction, protocol, port, IP addresses. To test any test case just add it in the file in the given format. The lines with * will be ignored.
I have added test cases that will check all the boundary conditions for the given parameters. 
I have also added a rules_big.csv which contains 500k rules to test it for regression. Feel free to add more test cases that you would like to test.

2.    Implementation of the Solution.
It is simple to do a text compare for the inputs like direction and protocol and also a simple integer comparison to check the port range.
For checking if the given IP Address is in the range I used the IP Address to Long conversion to get the corresponding Long value and then do the range check. I felt this was a clean and simple solution.

I have used a HashMap to store all the rules.
The key of the map is the direction + protocol eg: inbound_tcp. 
This value of the map is a HashSet of Rule objects which contains the all the rules that have direction as “inbound” and protocol as “TCP”
Eg:
Rule =
{
    startPort:10000
    endPort:20000
    startIPAddress:192.168.1.1
    endIPAddress:192.168.1.10
} 

Eg.
RulesMap = 
{
inbound_tcp : { {startPort :, endPort:, startIPAddress:, endIPAddress} ,
            {startPort :, endPort:, startIPAddress:, endIPAddress}}
Outbound_udp : { {startPort :, endPort:, startIPAddress:, endIPAddress} ,
            {startPort :, endPort:, startIPAddress:, endIPAddress}}

}
 
I used this following structure since in the first step we can eliminate a lot of considering the rules are evenly distributed between the protocols and direction.
The lookup cost will be O(1) for this one.
Then we need to check if the HashSet contains the rule we are looking for. Here we are iterating each element in the HashSet to check if the IPAddress and the Port Number is in range. This will take O(n) time for its completion.
The purpose of the HashSet is to avoid duplicate entries for rules.  
The storage space required here is the O(n) where n is the number of rules we need to add in the map. Each rule object needs a couple of integers and a long datatype storage.
Inserting new rules will take O(1) time but it can degrade to O(n) in the worst case considering open addressing or chaining is used.
We can also use an Array List instead of HashSet since inserting new rules to the map will take O(1) time for ArrayList but HashSet can help avoid duplicate rules by simple overriding equals and hashCode method.

3.    Possible Improvements
This is a simple implementation but it could it be enhanced further by using an trie structure to store the IP Addresses in the binary form or making use of HashMaps in the value for our current HashMaps.

The value in our current HashMap can have binary trie data structure by converting the IP Address to a binary representation. This approach will use the property that IP Addresses are made of 4 octets and its binary representation can be mapped into a trie so that the time complexity of searching for and rule with single IP Address will be limited to a depth of the trie which will be maximum of 32. If the rule has an IP Address range like 192.168.1.1 – 192.168.1.10 we could use the first three octets to map it to the trie and then the leaf nodes can have the range variables 1 and 10 to check the range. So, the worst-case time complexity is equal to the depth of the binary tree which will be less than 32 in case of IPV4 addresses.

Also, the value can further point to a new HashMap which will make use of the IP Address to find a hash value that can point to a particular set of IP Addresses which and reduce the time complexity. So, we have a HashMap in which the value points to another HashMap. This can simply reduce the time complexity to O(1) considering the best case for a perfect hash function but could degrade to O(n) if a proper hash function is not selected.

The range part makes it convoluted to implement a simple HashSet solution. Considering a HashSet, if there was a specific port and IP addresses the time complexity could be reduced cold to O(1) by simple using contains instead comparing each rule which executed in O(1) time. Considering we have enough memory we can create separate entries which cover all the range and help us reduce the time complexity just at the expense of space.

4.	Team Preference
I would love to work with the Platform team.
I have been working as a backend developer and problems like high scalability and availability interest me. I am focusing my Master's Degree in Distributed Computing and Machine Learning and I guess the problems mentioned seems to deal with the same and would help me learn a lot about it.
Also, I had mentioned caching as an improvement to the above solution to improve performance and getting to work on actual problems will help me improve my understanding and tackling such problems.

The platform team would be my second preference. This team is also focuses on reliability and performance about which I would like to learn a lot.

