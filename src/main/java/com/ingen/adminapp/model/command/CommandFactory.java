package com.ingen.adminapp.model.command;

public class CommandFactory {
    public Command createCommand(String c) {
        if ("accessSecurity".equals(c)) {
            return new AccessSecurityCommand();
        }
        return new ForbiddenCommand();
    }
}
