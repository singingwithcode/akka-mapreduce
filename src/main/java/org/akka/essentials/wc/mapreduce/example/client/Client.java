package org.akka.essentials.wc.mapreduce.example.client;

import akka.actor.*;
import akka.kernel.*;

import com.typesafe.config.*;

public class Client implements Bootable {
	public Client() {
		final String fileName = "BlackPanther.txt";

		ActorSystem system = ActorSystem.create("ClientApplication",
				ConfigFactory.load().getConfig("MapReduceClientApp"));

		final ActorRef fileReadActor = system.actorOf(Props.create(
				FileReadActor.class));

		String remotePath = "akka.tcp://MapReduceApp@127.0.0.1:2552/user/masterActor";
		ActorRef clientActor = system.actorOf(Props.create(ClientActor.class, remotePath));

		fileReadActor.tell(fileName, clientActor);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("TIMER STARTED");
		long startTime = System.nanoTime();
		new Client();
		long endTime = System.nanoTime();
		double dur = ((double)(endTime - startTime)/502277739);

		System.out.println("TIMER STOPPED, DURATION: " + dur + "ms");
	}

	public void shutdown() {
		// TODO Auto-generated method stub
	}

	public void startup() {
		// TODO Auto-generated method stub
	}
}
