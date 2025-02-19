package br.com.rodrigo.todolist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hi")
public class TesteRota {
    @GetMapping
    public String hello(){
        return "Hi Lorena!";
    }
}
