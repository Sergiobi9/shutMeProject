package com.example.shutmeproject.Traffic;

import android.net.TrafficStats;
import android.util.Log;
import android.widget.Toast;

import com.example.shutmeproject.Bytes.BytesConverterManager;
import com.example.shutmeproject.Helpers.ByteUnitsEnum;

public class TrafficManager {

    public TrafficStats trafficStats = new TrafficStats();
    private final static String TAG = "TrafficManager";
    private BytesConverterManager bytesConverterManager = new BytesConverterManager();

    public TrafficManager() {}

    /* Return number of bytes received across mobile networks since device boot. */
    public double getTotalRxGigaBytes(){
        double dataInBytes =  trafficStats.getTotalRxBytes();
        Log.d(TAG, String.valueOf(dataInBytes));

        double dataInGigaBytes = bytesConverterManager.convertBytes(dataInBytes, ByteUnitsEnum.GIGABYTE);
        return dataInGigaBytes;
    }
}
