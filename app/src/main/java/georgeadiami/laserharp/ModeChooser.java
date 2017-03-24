package georgeadiami.laserharp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

public class ModeChooser extends AppCompatActivity {

    private TextView textView;
    private RetrievePacket task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_chooser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        String ipAddress = intent.getStringExtra(MainActivity.TIVA_IP);
        int port = intent.getIntExtra(MainActivity.TIVA_PORT, 80);
        textView = (TextView) findViewById(R.id.receiveMsg);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        task = new RetrievePacket();
        task.execute();
    }

    public class RetrievePacket extends AsyncTask<Void, Void, Boolean> {

        private String lText;

        protected Boolean doInBackground(Void... params) {

            byte[] lMsg = new byte[1500];
            DatagramPacket dp = new DatagramPacket(lMsg, lMsg.length);
            UDP udp = UDP.getInstance();
            
            try {
                //disable timeout for testing
                //ds.setSoTimeout(100000);
                udp.ds.receive(dp);
                lText = new String(lMsg, 0, dp.getLength());
                Log.i("UDP packet received", lText);

            } catch (SocketException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {

            }
            return true;
        }

        protected void onPostExecute(final Boolean success) {

            if (success) {
                textView.setText(lText);
            }
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        task.cancel(true);
    }

}
