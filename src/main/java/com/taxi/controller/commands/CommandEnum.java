package com.taxi.controller.commands;

import com.taxi.controller.exceptions.WrongCommandException;

public enum CommandEnum {

    LOGIN ("LOGIN", new LoginCommand(new UserServiceImpl())) {};


    private String commandPath;
    private Command command;

    CommandEnum(String commandPath, Command command){
        this.commandPath = commandPath;
        this.command = command;
    }

    public Command getCurrentCommand() {
        return command;
    }

    public static Command getByPath(String path) throws WrongCommandException {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.commandPath.equalsIgnoreCase(path))
                return commandEnum.command;
        }
        throw new WrongCommandException(path);
    }

}
