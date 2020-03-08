package jken.module.core.support.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorEndpoint {

    @GetMapping
    public String errorPage() {
        return "error/error";
    }

    @GetMapping({"/{code}"})
    public String codePage(@PathVariable("code") String code, Model model) {
        model.addAttribute("code", code);
        return "error/code";
    }
}
