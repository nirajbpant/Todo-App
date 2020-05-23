package com.example.todomvvm.data.user;

class CheckInWrapper {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final boolean exists;

    CheckInWrapper(String email, String firstName, String lastName, boolean exists) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.exists = exists;
    }


    public static CheckInWrapper notFound() {
        return new CheckInWrapper(null, null, null, false);
    }

    public static CheckInWrapper sucess(String email, String firstName, String lastName) {
        return new CheckInWrapper(email, firstName, lastName, true);
    }
}
