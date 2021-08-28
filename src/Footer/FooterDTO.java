package Footer;

public class FooterDTO {
    private String normalEH;
    private String specialEH;
    private String warningHours;
    private String nightlyHours;

    public String getNormalEH() {
        return normalEH;
    }

    public void setNormalEH(String normalEH) {
        this.normalEH = normalEH;
    }

    public String getSpecialEH() {
        return specialEH;
    }

    public void setSpecialEH(String specialEH) {
        this.specialEH = specialEH;
    }

    public String getWarningHours() {
        return warningHours;
    }

    public void setWarningHours(String warningHours) {
        this.warningHours = warningHours;
    }

    public String getNightlyHours() {
        return nightlyHours;
    }

    public void setNightlyHours(String nightlyHours) {
        this.nightlyHours = nightlyHours;
    }
}
