package org.bananatigeer.read_csv;

public class ReadCSVProcessesCount {

    private int ReceivedCount;
    private int FailedCount;
    private int SuccessCount;

    public ReadCSVProcessesCount(){
        ReceivedCount = 0;
        FailedCount = 0;
        SuccessCount = 0;
    }

    public int getReceived() {
        return SuccessCount + FailedCount;
    }

    public int getFailedCount() {
        return FailedCount;
    }

    public int getSuccessCount() {
        return SuccessCount;
    }

    public void setFailedCount(int failed) {
        FailedCount = failed;
    }

    public void setSuccessCount(int success) {
        SuccessCount = success;
    }
}
