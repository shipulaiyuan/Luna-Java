package com.virtual.luna.controller.publish;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Index.html")
@Controller
public class IndexController {

    @PermitAll
    @GetMapping("/index")
    public String forward() {
        return "forward:/index.html";
    }

}
