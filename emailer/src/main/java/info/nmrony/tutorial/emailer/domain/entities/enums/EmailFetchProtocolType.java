package info.nmrony.tutorial.emailer.domain.entities.enums;

public enum EmailFetchProtocolType {
    POP3("POP3"), IMAP("IMAP"), POP3S("POP3S");

    public final String label;

    private EmailFetchProtocolType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
