package com.example.thestockers;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReceiptScannerFragment extends Fragment {
    private ImageView capturedImage;
    private Button captureBtn, addAllBtn;
    public TextView resultTV, progressTV;
    public ProgressBar progressBar;
    private Bitmap imageBitmap;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    int CAMERA_PERMISSION_CODE = 200;
    int FILE_WRITE_PERMISSION = 300;
    String scanResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receipt_scanner, container, false);
        capturedImage = view.findViewById(R.id.receipt_logo);
        resultTV = view.findViewById(R.id.scan_resultTV);
        progressTV = view.findViewById(R.id.progressTV);
        progressBar = view.findViewById(R.id.progressBar);
        captureBtn = view.findViewById(R.id.capture_button);
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    captureImage();
                } else {
                    requestPermission();
                }
            }
        });
        addAllBtn = view.findViewById(R.id.add_all_button);
        addAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    // returns true when camera permission is granted.
    private boolean checkPermissions() {
        int cameraPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, CAMERA_PERMISSION_CODE);
        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, FILE_WRITE_PERMISSION);
    }


    public void captureImage() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mStartForResult.launch(takePicture);
    }

    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher, as an instance variable
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == REQUEST_IMAGE_CAPTURE || result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Bundle extras = intent.getExtras();
                        imageBitmap = (Bitmap) extras.get("data");
                        capturedImage.setImageBitmap(imageBitmap);
                        capturedImage.setRotation(90);
                        try {
                            ScanReceipt();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    private File BitmapToJPEG(Bitmap bitmap) throws IOException {
        //create a file to write bitmap data
        File receiptFile = new File(getActivity().getCacheDir(), "receipt_image.jpeg");
        receiptFile.createNewFile();

        //Convert bitmap to byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        byte[] bitmapData = out.toByteArray();

        //write the bytes in file
        try {
            FileOutputStream fos = new FileOutputStream(receiptFile);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return receiptFile;
    }


    public void JavaReceiptOcr(File imageFile) throws Exception {
        final MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg");
        System.out.println("=== Java Receipt OCR ===");
        try{
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("client_id", "TEST")       // Use 'TEST' for testing purpose
                    .addFormDataPart("recognizer", "US")       // can be 'US', 'CA', 'JP', 'SG' or 'auto'
                    .addFormDataPart("ref_no", "ocr_java_123'") // optional caller provided ref code
                    .addFormDataPart("file", imageFile.getName(),
                        RequestBody.create(imageFile, MEDIA_TYPE_JPEG)
                    ).build();

        Request request = new Request.Builder()
                .url("https://ocr.asprise.com/api/v1/receipt")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if(!response.isSuccessful()) throw new IOException("Unexpected code "+ response);
            scanResult = response.body().string();
            System.out.println("Scanned Result >> "+ response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ScanReceipt() throws Exception {
        new fileFromBitmap().execute();
    }

    /*public void ExtractResult(){
        //String items[];
        Gson gson = new Gson();
        ReceiptResponseParser responseResult = gson.fromJson(scanResult, ReceiptResponseParser.class);
        System.out.println("Items List >> "+responseResult.getReceiptItems());
    }*/


    class fileFromBitmap extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressTV.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            //send file to API server to scan
            try {
                JavaReceiptOcr(BitmapToJPEG(imageBitmap));
            }catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //ExtractResult();
            resultTV.setText(scanResult);
            scanResult="";
        }
    }
}
