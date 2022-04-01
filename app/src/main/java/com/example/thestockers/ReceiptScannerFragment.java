package com.example.thestockers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import okhttp3.Response;


public class ReceiptScannerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Response queryResponse;
//        if(RemoteDBHelper.queryTableQueue.peek() == null) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        if(RemoteDBHelper.queryTableQueue.peek() == null) {
//            System.out.println("Error: query timed out");
//        }
//        else {
//            queryResponse = RemoteDBHelper.queryTableQueue.peek();
//            if(queryResponse.body() == null) { System.out.println("Error: Response body is null"); }
//            else{ System.out.println("Data: " + queryResponse.body()); }
//            RemoteDBHelper.queryTableQueue.clear();
//        }
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://ucx0ybjsh4.execute-api.us-west-1.amazonaws.com/queryall?table=shoppinglist")
//                .build();
//        try(Response response = client.newCall(request).execute()) {
//            System.out.println("Data: ");
////            System.out.print(response.body().string());
//        }
//        catch(Exception e) {
//            System.out.println("Error with executing client call: " + e.toString());
//        }




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_receipt_scanner, container, false);
    }

}