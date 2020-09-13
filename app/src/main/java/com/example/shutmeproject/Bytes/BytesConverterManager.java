package com.example.shutmeproject.Bytes;

import com.example.shutmeproject.Helpers.ByteUnitsEnum;

import java.text.DecimalFormat;

public class BytesConverterManager {

    /*
        1 Byte = 8 Bits
        1 Kilobyte = 1024 Bytes
        1 Megabyte = 1048576 Bytes
        1 Gigabyte = 1073741824 Bytes
        1 Terabyte = 1099511627776 bytes
     */

    private final double KILOBYTE = 1024.0;
    private final double MEGABYTE = 1048576.0;
    private final double GIGABYTE = 1073741824.0;
    private final double TERABYTE = 1099511627776.0;

    public BytesConverterManager() {}

    public double convertBytes(double bytes, ByteUnitsEnum unit){
        DecimalFormat df = new DecimalFormat("0.00");
        String result = "";

        switch (unit){
            case KILOBYTE:
                result = df.format(bytes/KILOBYTE).replace(",", ".");
                return Double.parseDouble(result);
            case MEGABYTE:
                result = df.format(bytes/MEGABYTE).replace(",", ".");
                return Double.parseDouble(result);
            case GIGABYTE:
                result = df.format(bytes/GIGABYTE).replace(",", ".");
                return Double.parseDouble(result);
            case TERABYTE:
                result = df.format(bytes/TERABYTE).replace(",", ".");
                return Double.parseDouble(result);
            default:
                return bytes;
        }
    }
}
