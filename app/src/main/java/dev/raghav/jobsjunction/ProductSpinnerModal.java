package dev.raghav.jobsjunction;

import java.io.Serializable;

public class ProductSpinnerModal implements Serializable {

    private String masterdataId;
    private String masterdataName;
    private String type;
    private String parent;

    public ProductSpinnerModal(String masterdataId, String masterdataName, String type, String parent) {
        this.masterdataId = masterdataId;
        this.masterdataName = masterdataName;
        this.type = type;
        this.parent = parent;
    }

    public String getMasterdataId() {
        return masterdataId;
    }

    public void setMasterdataId(String masterdataId) {
        this.masterdataId = masterdataId;
    }

    public String getMasterdataName() {
        return masterdataName;
    }

    public void setMasterdataName(String masterdataName) {
        this.masterdataName = masterdataName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
