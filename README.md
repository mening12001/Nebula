# Nebula
# General-purpose Distributed Computing Framework in Java (POC)

This project is experimental in nature. You should view this implementation simply as a proof of concept.
This "framework" gives you the possibility to execute remotely almost any computation/task on a given server or cluster of servers. 
It implemens in a way, superficially, a General-purpose Distributed Computing System. You can execute a REMOTE LAMBDA practically.

At first, it was developed just for my own amusement while I was still in collage. It was written during a couple of courses, as an asignment.
The given asignment wasn't preceisly this, but something way more rudimentary.It was expected to explicitly implement a part of a particular algorithm
on the server, and the other part on the client. Therefore, I had this idea, with a more general scope. It was approximately functional.
This is a reimplementation, supporting also Remote Lambdas(at that time, lambda wasn't supported in java).

The project consists in to seperate modules: Nebula-Master and Nebula-Slave.

# Nebula-Slave
Nebula-Slave is actually a server, that once started, runs indefinitely; constantly waiting to receive a Computation, one after the other.
The so called Slave(the server in discussion), executes the Computation and returns the obtained result. 
In order to start a Slave instance, execute the Main1.java (there is a "main" method where a new instance of Server is created on port 12345
and started).
```java
Server slave_server = new Server(server_port);
slave_server.start();
```

# Nebula-Master
Nebula-Master contains a client that permits you to send a Computation to any Slave in order to be executed; waiting in turn after the result. 
In order to send a computation to a running Slave(Server) instance, you simply create a new Slave(address_of_slave, port_of_slave) and invoke the method
"compute(Labda or Computation object)". 
```java
Slave slave = new Slave(remote_server_address, remote_server_port);
slave.compute(() -> {System.out.println("example remote lambda executed"); return true;});
```
The lambda is simply autoblackmagically executed on the remote server, returning as a result in this case "true".


# Run the Example
Firstly, it is necessary to start at least a Slave instance. For simplicity, we start the server on localhost, port 12345. In order to do that,
execute the class Main1.java that receides in Nebula-Slave.
Secondly, execute the Main.java class that receides in Nebula-Master. 
As it can be observed in Main.java class from Nebula-Master, it is sent for execution an ActualComputation, but also a lambda(Remote Lambda).
I consider that the code is self-explanatory.

Don't retain yourself from participating, from developing further this intriguing project. Thank you!
