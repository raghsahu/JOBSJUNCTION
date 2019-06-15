package dev.raghav.jobsjunction;

class CityModel {

    private String masterdataId;
    private String masterdataName;
    private String type;


    public CityModel(String masterdataId, String masterdataName, String type) {
        this.masterdataId = masterdataId;
        this.masterdataName = masterdataName;
        this.type = type;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMasterdataName() {
        return masterdataName;

    }

    public void setMasterdataName(String masterdataName) {
        this.masterdataName = masterdataName;
    }

    public String getMasterdataId() {
        return masterdataId;
    }

    public void setMasterdataId(String masterdataId) {
        this.masterdataId = masterdataId;
    }
}
