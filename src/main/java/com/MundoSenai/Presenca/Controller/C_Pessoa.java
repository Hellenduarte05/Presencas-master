package com.MundoSenai.Presenca.Controller;

import com.MundoSenai.Presenca.Service.s_Pessoa;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class C_Pessoa {
    @GetMapping("/")
    public String helloWord(){return "Login/login";}

    @PostMapping("/")
    public String postLogin(@RequestParam("usuario") String usuario, @RequestParam("senha") String senha){
        if(s_Pessoa.getPessoaLogin(usuario,senha) == null){
            return "Login/login";
        }else{
            return "Home/home";
        }
    }
    @GetMapping("/cadastro")
    public String getCadastro(){
        return "Pessoa/cadastro";
    }
    @PostMapping("/cadastro")
    public String postCadastro(@RequestParam("nome") String nome, @RequestParam("email") String email, @RequestParam("cpf") String cpf,
                               @RequestParam("telefone") String telefone, @RequestParam("data_nasc") String Datanasc,
                               @RequestParam("senha") String senha, @RequestParam("confsenha") String confsenha){
        s_Pessoa.cadastrarPessoa(nome, email, cpf, telefone, Datanasc, senha, confsenha);
        return "redirect:/";
    }

}
