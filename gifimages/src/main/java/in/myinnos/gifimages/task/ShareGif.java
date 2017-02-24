package in.myinnos.gifimages.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import in.myinnos.gifimages.helper.Helper;


public class ShareGif extends AsyncTask<Void, Void, File> {

    private static final int BUFFER_SIZE = 4096;

    private Context context;
    private String gifUrl;

    private ProgressDialog dialog;

    public ShareGif(Context context, String gifUrl) {
        this.context = context;
        this.gifUrl = gifUrl;
    }

    @Override
    public void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setMessage("preparing gif to share ...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected File doInBackground(Void... arg0) {
        try {
            URL url = new URL(gifUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                File downloadDir = new File(Environment.getExternalStorageDirectory(), "Download");
                if (!downloadDir.exists()) {
                    downloadDir.mkdir();
                }

                File file = new File(downloadDir, "giphy-" + System.currentTimeMillis() + ".gif");
                file.createNewFile();

                // opens input stream from the HTTP connection
                InputStream inputStream = httpConn.getInputStream();

                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(file);

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                return file;
            } else {
                Log.v("giphy_share", "No file to download. Server replied HTTP code: " + responseCode);
            }

            httpConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(File result) {
        dialog.dismiss();

        if (result != null) {
            Uri contentUri = Helper.getImageContentUri(context, result);

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            sharingIntent.setType("image/gif");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, contentUri);

            // start the chooser
            context.startActivity(Intent.createChooser(sharingIntent, "Share with:"));
        } else {
            Toast.makeText(context, "Error Downloading ...", Toast.LENGTH_SHORT).show();
        }
    }
}