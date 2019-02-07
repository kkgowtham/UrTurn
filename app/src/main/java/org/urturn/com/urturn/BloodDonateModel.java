package org.urturn.com.urturn;

public class BloodDonateModel {
    private  String patientName;
    private String withinTime;
    private String bloodGroup;
    private int noOfUnits;
    private String hospitalName;
    private String state;
    private String city;
    private String timeStamp;

    public BloodDonateModel()
    {

    }
    public BloodDonateModel(String patientName,String withinTime, String bloodGroup, int noOfUnits, String hospitalName, String state, String city, String timeStamp) {
        this.patientName = patientName;
        this.withinTime = withinTime;
        this.bloodGroup = bloodGroup;
        this.noOfUnits = noOfUnits;
        this.hospitalName = hospitalName;
        this.state = state;
        this.city = city;
        this.timeStamp = timeStamp;
    }


    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }


    public String getWithinTime() {
        return withinTime;
    }

    public void setWithinTime(String withinTime) {
        this.withinTime = withinTime;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(int noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


}
