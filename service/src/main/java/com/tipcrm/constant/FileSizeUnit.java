package com.tipcrm.constant;
public enum FileSizeUnit {
    B(1L), KB(1024L), MB(1024 * 1024L), GB(1024 * 1024 * 1024L);

    private Long sizeAsByte;

    FileSizeUnit(Long sizeAsByte) {
        this.sizeAsByte = sizeAsByte;
    }

    public Long getSizeAsByte() {
        return sizeAsByte;
    }
}
