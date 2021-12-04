# Akka MapReduce
### Updated By: Matthew Klich, Brandon Do, Tinashe Tagarisa
This project was updated to work with the latest version of Akka and test the timing of a MapReduce function in Akka.

# Directions
To toggle between the client and server, you need to edit the pom.xml file accordingly. It should commence in the following order:
1. Ensure `client.Client` is commented out in pom.xml
2. Use maven to clean the space. 
3. Compile the code for server launch.
4. Launch server with the maven run command. 
5. Once the server is launched and listening, you can move onto the client. Leave the server running. 
6. Comment out the `MapReduceServer` in pom.xml and uncomment `client.Client`
7. Use maven to clean the space. 
8. Compile the code for client launch.
9. Launch server with the maven run command. 

## Editing the pom.xml

### Before Starting Server
The following should be commented out in the pom.xml file:
```
<mainClass>org.akka.essentials.wc.mapreduce.example.server.MapReduceServer</mainClass>
<!--<mainClass>org.akka.essentials.wc.mapreduce.example.client.Client</mainClass>-->
```

### Before Starting Client
The following should be commented out in the pom.xml file:
```
<!--<mainClass>org.akka.essentials.wc.mapreduce.example.server.MapReduceServer</mainClass>-->
<mainClass>org.akka.essentials.wc.mapreduce.example.client.Client</mainClass>
```
## Commands
### To Clean: 
`mvn clean`

### To Compile: 
`mvn compile`

### To Run:
`mvn exec:java -Dexec.cleanupDaemonThreads=false -Dexec.mainClass=org.akka.essentials.wc.mapreduce.example`

## To Change The txt File Being Read
Navigate to the `Client.java` file and update the file inside the Client() class. Ensure that the file is stored in the resource folder. 

# Design Notes
- ClientActor sends a bunch of messages which are "subtasks". Note that the Identity request pattern is used to gain access to the ActorRef of the remote MapReduce MasterActor.
- ClientActor sends last the TaskInfo message saying how many subtasks were previously sent.
- MasterActor forwards String messages to MapActor which in turns forwards to ReduceActor
- One MapActor is a lengthy one namely the one with content "Thieves! thieves!" this slows the MapReduce computation a bit.
- Meanwhile MasterActor receives the TaskInfo last message and sends back to ClientActor the ShudownInfo
- ClientActor runs system.shutdown() and Client terminates. Note that the MapReduce is still in the middle of the processing and the Client shutdown does not interfere.
- The lengthy MapActor comes back and the message processing continues.
- AggregatorActor receives the TaskInfo and by counting the subtasks confirms that the total number of substasks have been completed and signals completion.