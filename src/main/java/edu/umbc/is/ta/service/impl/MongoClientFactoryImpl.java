package edu.umbc.is.ta.service.impl;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.MongoClient;

public class MongoClientFactoryImpl {

	private static final MongoClientFactoryImpl factory = new MongoClientFactoryImpl();
	
	public static MongoClient getMongoClient(String host, int port) throws Exception {
		try {
			return factory.getClient(host, port);
		} catch (UnknownHostException e) {
			throw new Exception(e);
		}
	}
	
	private final Map<MongoServer, MongoClient> clients;
	
	public MongoClientFactoryImpl() {
		clients = new HashMap<MongoServer, MongoClient>();
	}
	
	public MongoClient getClient(String host, int port) throws UnknownHostException {
		final MongoServer server = new MongoServer(host, port);
		if(clients.containsKey(server)) {
			return clients.get(server);
		} else {
			final MongoClient client = new MongoClient(host, port);
			clients.put(server, client);
			return client;
		}
	}
	
	private class MongoServer {
		
		private final String host;
		private final int port;
		
		public MongoServer(String host, int port) {
			this.host = host;
			this.port = port;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((host == null) ? 0 : host.hashCode());
			result = prime * result + port;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MongoServer other = (MongoServer) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (host == null) {
				if (other.host != null)
					return false;
			} else if (!host.equals(other.host))
				return false;
			if (port != other.port)
				return false;
			return true;
		}

		private MongoClientFactoryImpl getOuterType() {
			return MongoClientFactoryImpl.this;
		}
	}
}
