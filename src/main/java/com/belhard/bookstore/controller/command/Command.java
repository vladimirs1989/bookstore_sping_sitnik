package com.belhard.bookstore.controller.command;

import org.springframework.ui.Model;

public interface Command {
    String  execute(Model model);
}
