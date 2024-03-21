package com.ingen.adminapp.adapters.in.rest.command;

import com.ingen.adminapp.application.port.RunCommandUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/park")
public class CommandController {

    private final RunCommandUseCase runCommandUseCase;

    public CommandController(@Autowired RunCommandUseCase runCommandUseCase) {
        this.runCommandUseCase = runCommandUseCase;
    }

    @GetMapping("/admin/command")
    public String runCommand(@RequestParam(name = "command", required = true) String command) {
        return runCommandUseCase.executeCommand(command).msg();
    }
}
