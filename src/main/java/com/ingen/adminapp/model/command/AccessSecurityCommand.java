package com.ingen.adminapp.model.command;

public class AccessSecurityCommand implements Command {
    @Override
    public CommandExecutionResponse run() {
        return new CommandExecutionResponse(
                "Ah ah ah, you didn't say the magic word\n".repeat(100)
        );
    }
}
