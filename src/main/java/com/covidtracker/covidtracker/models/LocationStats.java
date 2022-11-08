package com.covidtracker.covidtracker.models;

public class LocationStats implements Comparable<LocationStats>{
    private String state;
    private String country;
    private int latestTotalCases;
    private int diffFromPrevDay;
    public String getState(){
        return this.state;
    }
    public String getCountry(){
        return this.country;
    }
    public void setState(String state){
        this.state=state;
    }
    public void setCountry(String country){
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    @Override
    public String toString(){
        return "LocationStats{"+"state = " + state + ", country = "+country+", latestTotalCases = " + latestTotalCases + ", diffFromPrevDay = " + diffFromPrevDay + '}';
    }

    @Override
    public int compareTo(LocationStats o) {
        if(this.state==""){
            return 1;
        }
        else if(o.state==""){
            return -1;
        }
        else{
            return 0;
        }
    }
}
