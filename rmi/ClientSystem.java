package rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientSystem {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);

        HelloService stub = (HelloService)registry.lookup("HelloService");
        System.out.println(stub.sayHello("Nolan Zimmer"));
    }
}