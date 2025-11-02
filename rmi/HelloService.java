package rmi;

import java.rmi.*;

public interface HelloService extends Remote{
    public String sayHello(String name) throws RemoteException;
}