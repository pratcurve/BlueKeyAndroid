package com.example.prateek.bluekey;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by prateek on 4/3/17.
 */

public class SaveLock {

    private String lockname, lockAddress, lockStatus;
    private Context context;

    public SaveLock(String lockname, String lockAddress, String lockStatus, Context context) {
        this.lockname = lockname;
        this.lockAddress = lockAddress;
        this.lockStatus = lockStatus;
        this.context = context;
    }

    public void saveToFile() {
        //save data in sqlite database
        String filename = lockAddress.replace(":", "");
        File file = new File(context.getFilesDir(), filename);
        String lockData = lockname+","+lockAddress+","+lockStatus;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(filename, MODE_PRIVATE);
            fileOutputStream.write(lockData.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("SuccessResponse", "saving in to database sqlite");
    }
}
