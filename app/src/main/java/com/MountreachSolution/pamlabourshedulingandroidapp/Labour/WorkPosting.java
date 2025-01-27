package com.MountreachSolution.pamlabourshedulingandroidapp.Labour;

public class WorkPosting {
    private String id;
    private String farmerName, mobileNumber, address, workInShort, wages, startTime, endTime, workDate, workImage;

    public WorkPosting(String id, String farmerName, String mobileNumber, String address,
                       String workInShort, String wages, String startTime, String endTime, String workDate, String workImage) {
        this.id = id;
        this.farmerName = farmerName;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.workInShort = workInShort;
        this.wages = wages;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workDate = workDate;
        this.workImage = workImage;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getFarmerName() { return farmerName; }
    public String getMobileNumber() { return mobileNumber; }
    public String getAddress() { return address; }
    public String getWorkInShort() { return workInShort; }
    public String getWages() { return wages; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getWorkDate() { return workDate; }
    public String getWorkImage() { return workImage; }
}

