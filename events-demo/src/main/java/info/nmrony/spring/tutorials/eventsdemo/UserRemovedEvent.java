package info.nmrony.spring.tutorials.eventsdemo;

class UserRemovedEvent {

    public String name;

    UserRemovedEvent(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }

}
