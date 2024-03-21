package com.ingen.adminapp.application.port;

import com.ingen.adminapp.model.command.CommandExecutionResponse;

public interface RunCommandUseCase {
    CommandExecutionResponse executeCommand(String command);
}
