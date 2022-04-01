package com.example.thestockers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.mlkit.vision.barcode.BarcodeScanner;
//import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
//import com.google.mlkit.vision.barcode.BarcodeScanning;
//import com.google.mlkit.vision.barcode.common.Barcode;
//import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class BarcodeScannerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Create options for the barcode scanner with upc code formats to scan
//        BarcodeScannerOptions barOptions =
//                new BarcodeScannerOptions.Builder()
//                        .setBarcodeFormats(
//                                Barcode.FORMAT_UPC_A,
//                                Barcode.FORMAT_UPC_E)
//                        .build();
//        // Create bitmap object from image
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        Bitmap bitmapImage = BitmapFactory.decodeFile("C:\\Users\\Tanner\\" +
//                "AndroidStudioProjects\\The-Stockers\\app\\src\\main\\res\\raw\\" +
//                "IMG_20220214_104607127_HDR.bmp", bmOptions);
//        // Changing the image into a usable object for mlkit
//        InputImage testBarcode = InputImage.fromBitmap(bitmapImage, 0);
//        // Creating the barcode scanner
//        BarcodeScanner scanner = BarcodeScanning.getClient(barOptions);
//        // Process the barcode
//        Task<List<Barcode>> result = scanner.process(testBarcode)
//                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
//                    @Override
//                    public void onSuccess(List<Barcode> barcodes) {
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                    }
//                });
////        result[1]
////        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_barcode_scanner, container, false);
    }
}