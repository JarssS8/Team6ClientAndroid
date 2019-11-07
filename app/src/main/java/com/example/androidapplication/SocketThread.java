package com.example.androidapplication;

import android.util.Log;

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
                Connectable client = ConnectableClientFactory.getClient();

                switch (messageType) {
                    case Message.LOGIN_MESSAGE:
                        Log.d("Thread", "Trying to set user");
                        setUser(client.logIn(user));
                        Log.d("Thread", "Set user succesfull " + user.getLogin());

                        break;
                    case Message.LOGOUT_MESSAGE:
                        client.logOut(user);
                        break;
                    case Message.SIGNUP_MESSAGE:
                        setUser(client.signUp(user));
                        break;

                }
            } catch (LoginNotFoundException e) {
                e.printStackTrace();
            } catch (WrongPasswordException e) {
                e.printStackTrace();
            } catch (LoginAlreadyTakenException e) {
                e.printStackTrace();
            } catch (ServerConnectionErrorException e) {
                e.printStackTrace();
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

}
