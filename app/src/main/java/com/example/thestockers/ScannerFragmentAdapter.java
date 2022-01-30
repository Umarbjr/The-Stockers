package com.example.thestockers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ScannerFragmentAdapter extends FragmentStateAdapter{
    private static final int TABCOUNT = 3;

    public ScannerFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 1:
                return new ReceiptScannerFragment();
            case 2:
                return new ManualAddFragment();
        }
        return new BarcodeScannerFragment();
    }

    @Override
    public int getItemCount() {
        return TABCOUNT;
    }
}
