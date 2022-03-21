package com.example.thestockers;

import static android.Manifest.permission.CAMERA;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;

public class ReceiptScannerFragment extends Fragment {
    private ImageView capturedImage;
    private Button captureBtn, addAllBtn;
    private TextView resultTV;
    private Bitmap imageBitmap;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receipt_scanner, container, false);
        capturedImage = view.findViewById(R.id.receipt_logo);
        resultTV = view.findViewById(R.id.scan_resultTV);
        captureBtn = view.findViewById(R.id.capture_button);
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(checkPermissions()) {
                   captureImage();
                   detectText();
               }else{
                   //requestPermission();
                   Toast.makeText(getActivity(), "This feature is unavailable because it " +
                           "requires a permission that the user has denied", Toast.LENGTH_LONG).show();
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

    private boolean checkPermissions(){
        int cameraPermission = ContextCompat.checkSelfPermission(getActivity(), CAMERA);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        int PERMISSION_CODE = 200;
        //ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, PERMISSION_CODE);
        requestPermissionLauncher.launch(Manifest.permission.CAMERA);
    }

    private void captureImage(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicture.resolveActivity(getActivity().getPackageManager())!=null){
           mStartForResult.launch(takePicture);
        }
    }

    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher, as an instance variable.
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(getActivity(), "Permission Granted.", Toast.LENGTH_SHORT).show();
                    captureImage();
                } else {
                    Toast.makeText(getActivity(), "This feature is unavailable because it " +
                            "requires a permission that the user has denied", Toast.LENGTH_LONG).show();
                }
            });

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == REQUEST_IMAGE_CAPTURE
                            && result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Bundle extras = intent.getExtras();
                        imageBitmap = (Bitmap) extras.get("data");
                        capturedImage.setImageBitmap(imageBitmap);
                    }
                }

            });

    private void detectText(){
        InputImage image = InputImage.fromBitmap(imageBitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        Task<Text> result = recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(@NonNull Text text) {
                StringBuilder result = new StringBuilder();
                for(Text.TextBlock block: text.getTextBlocks()){
                    String blockText = block.getText();
                    Point[] blockCornerPoint = block.getCornerPoints();
                    Rect blockFrame = block.getBoundingBox();
                    for(Text.Line line: block.getLines()){
                        String lineText = line.getText();
                        Point[] lineCornerPoint = line.getCornerPoints();
                        Rect lineRect = line.getBoundingBox();
                        for (Text.Element element: line.getElements()){
                            String elementText = element.getText();
                            result.append(elementText);
                        }
                        resultTV.setText(blockText);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed to detect text from image."+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}