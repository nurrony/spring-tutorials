package info.nmrony.tutorial.emailer.domain.entities.enums;

public enum EmailFolder {
    INBOX("INBOX"), SENT("SENT"), DRAFT("DRAFT"), TRASH("TRASH"), ARCHIEVED("ARCHIEVED");

    final public String label;

    private EmailFolder(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return getLabel();
    }
}
