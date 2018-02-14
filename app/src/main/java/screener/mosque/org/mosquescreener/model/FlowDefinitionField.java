package screener.mosque.org.mosquescreener.model;

/**
 * Created by abdelhakim on 31/01/2018.
 */

public enum FlowDefinitionField {
    TYPE, LIMIT0, LIMIT1;

    public String getLCName() {
        return this.name().toLowerCase();
    }
}
