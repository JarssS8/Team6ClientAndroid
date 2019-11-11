package com.example.androidapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import clientlogic.logic.ConnectableClientFactory;
import utilities.beans.User;
import utilities.exception.DBException;
import utilities.exception.LogicException;
import utilities.exception.LoginAlreadyTakenException;
import utilities.exception.LoginNotFoundException;
import utilities.exception.ServerConnectionErrorException;
import utilities.exception.WrongPasswordException;
import utilities.interfaces.Connectable;
import utilities.beans.Message;

public class SocketThread extends Thread {
    private String messageType = null;
    private User user = null;

    /**
     * This method extends from class thread and when we do a thread.start() start this method creating a new Thread
     */
    @Override
    public void run() {
        Log.i("SocketThread","New thread created for interact with Logic library");
        if (user != null && messageType != null) {
            try {
                Log.i("SocketThread","Creating a new client sending the IP and the PORT");
                Connectable client = ConnectableClientFactory.getClient("192.168.20.116",5000);
                Log.i("SocketThread","Client created correctly");
                switch (messageType) {
                    case Message.LOGIN_MESSAGE:
                        Log.i("SocketThread","Starting login method on the client");
                        setUser(client.logIn(user));
                        Log.i("SocketThread","Correct login");
                        setMessageType("OK");
                        break;
                    case Message.LOGOUT_MESSAGE:
                        Log.i("SocketThread","Starting login method on the client");
                        client.logOut(user);
                        Log.i("SocketThread","Correct login");
                        setMessageType("OK");
                        break;
                    case Message.SIGNUP_MESSAGE:
                        Log.i("SocketThread","Starting login method on the client");
                        setUser(client.signUp(user));
                        Log.i("SocketThread","Correct login");
                        setMessageType("OK");
                        break;

                }
            } catch (LoginNotFoundException e) {
                setMessageType("LoginError");
                Log.e("SocketThread","Login not found");
            } catch (WrongPasswordException e) {
                setMessageType("PasswordError");
                Log.e("SocketThread","Wrong password");
            } catch (LoginAlreadyTakenException e) {
                setMessageType("LoginTaken");
                Log.e("SocketThread","Login already taken");
            } catch (ServerConnectionErrorException e) {
                setMessageType("ServerError");
                Log.e("SocketThread","Server error");
            }
        }

    }

    /**
     * This method assign a string to a class component
     * @param messageType A string that we want assign
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * This method send a string from this class
     * @return A string from this class
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * This method send a User from this class
     * @return A User from this class
     */
    public User getUser() {
        return user;
    }

    /**
     * This method assign a User to a class component
     * @param user A User that we want assign
     */
    public void setUser(User user) {
        this.user = user;
    }

}
