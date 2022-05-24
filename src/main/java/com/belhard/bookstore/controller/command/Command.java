package com.belhard.bookstore.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

public interface Command {
    String  execute(Model model);
}
