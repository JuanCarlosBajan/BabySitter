package dg.jb.babysitter;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender extends AsyncTask<String,Void,Void> {

    Socket s;
    DataOutputStream dos;

    @Override
    protected Void doInBackground(String... voids) {

        String message = voids[0];
        try {

            s = new Socket("192.168.1.138",7800);
            dos = new DataOutputStream(s.getOutputStream());


        } catch (IOException E){
                E.printStackTrace();
        }


        return null;
    }
}
