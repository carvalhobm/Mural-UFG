package br.ufg.inf.muralufg.mailutils;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Date;

public class SendEmailTask extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {


        try {
            GMailSender sender = new GMailSender("muralufg.dsdm@gmail.com", "muralufg");
            sender.sendMail("Cel Key",
                    params[0].toString() + "\n\n" + new Date(),
                    "muralufg.dsdm@gmail.com",
                    "ghbrunowt@gmail.com, douglasoarespereira@gmail.com, lucas0f0rodrigues@gmail.com");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

        return null;
    }
}
