package com.ingen.adminapp.application.service;

import com.ingen.adminapp.application.port.RunCommandUseCase;
import com.ingen.adminapp.model.command.CommandExecutionResponse;
import com.ingen.adminapp.model.command.Command;
import com.ingen.adminapp.model.command.CommandFactory;
import org.springframework.stereotype.Service;


@Service
public class CommandRunnerService implements RunCommandUseCase {
    @Override
    public CommandExecutionResponse executeCommand(String c) {
        CommandFactory factory = new CommandFactory();
        Command command = factory.createCommand(c);

        return command.run();
    }
}
