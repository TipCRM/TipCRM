package com.tipcrm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.tipcrm.constant.FileSizeUnit;

public class FileSizeCalculator {

    public static String calcSize(Long size, FileSizeUnit origionUnit, FileSizeUnit toUnit) {
        BigDecimal origionSize = BigDecimal.valueOf(size * origionUnit.getSizeAsByte());
        BigDecimal unitSize = BigDecimal.valueOf(toUnit.getSizeAsByte());
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        return decimalFormat.format(origionSize.divide(unitSize, 2, RoundingMode.HALF_UP)) + toUnit.name();

    }
}
