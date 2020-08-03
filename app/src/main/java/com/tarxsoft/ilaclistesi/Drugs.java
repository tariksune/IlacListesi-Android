package com.tarxsoft.ilaclistesi;

public class Drugs {
    private String drugName;
    private String drugDesc;
    private String drugBarcode;
    private String drugIcon;

    public Drugs() {
    }

    public Drugs(String drugName, String drugDesc, String drugBarcode, String drugIcon) {
        this.drugName = drugName;
        this.drugDesc = drugDesc;
        this.drugBarcode = drugBarcode;
        this.drugIcon = drugIcon;

    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugDesc() {
        return drugDesc;
    }

    public void setDrugDesc(String drugDesc) {
        this.drugDesc = drugDesc;
    }

    public String getDrugBarcode() {
        return drugBarcode;
    }

    public void setDrugBarcode(String drugBarcode) {
        this.drugBarcode = drugBarcode;
    }

    public String getDrugIcon() {
        return drugIcon;
    }

    public void setDrugIcon(String drugIcon) {
        this.drugIcon = drugIcon;
    }
}
