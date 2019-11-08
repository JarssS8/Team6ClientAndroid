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

    @Override
    public void run() {
        if (user != null && messageType != null) {
            try {
                Connectable client = ConnectableClientFactory.getClient("192.168.20.55",5000);
                switch (messageType) {
                    case Message.LOGIN_MESSAGE:
                        Log.d("Thread", "Trying to set user");
                        setUser(client.logIn(user));
                        Log.d("Thread", "Set user succesfull " + user.getLogin());
                        setMessageType("OK");
                        break;
                    case Message.LOGOUT_MESSAGE:
                        client.logOut(user);
                        setMessageType("OK");
                        break;
                    case Message.SIGNUP_MESSAGE:
                        setUser(client.signUp(user));
                        setMessageType("OK");
                        break;

                }
            } catch (LoginNotFoundException e) {
                setMessageType("LoginError");
            } catch (WrongPasswordException e) {
                setMessageType("PasswordError");
            } catch (LoginAlreadyTakenException e) {
                setMessageType("LoginTaken");
            } catch (ServerConnectionErrorException e) {
                setMessageType("ServerError");
            }
        }

    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProperty(String key){
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("Properties.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

}
