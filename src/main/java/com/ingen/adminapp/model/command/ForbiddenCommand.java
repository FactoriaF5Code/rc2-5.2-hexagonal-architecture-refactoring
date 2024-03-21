package com.ingen.adminapp.model.command;

public class ForbiddenCommand implements Command {
    @Override
    public CommandExecutionResponse run() {
        return new CommandExecutionResponse("ACCESS DENIED");
    }
}
