package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

class HelloServer extends UnicastRemoteObject implements HelloService{

    HelloServer() throws RemoteException{}

    @Override
    public String sayHello(String name) {
        return "Hello " + name.trim();
    }
}

public class RemoteSystem {
    public static void main(String[] args) throws RemoteException {
        HelloServer object = new HelloServer();

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("HelloService", object);

        System.out.println("Server is up & running");
    }    
}