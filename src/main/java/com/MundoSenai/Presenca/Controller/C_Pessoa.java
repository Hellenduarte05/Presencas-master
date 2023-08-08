package com.MundoSenai.Presenca.Controller;

import com.MundoSenai.Presenca.Model.m_Pessoa;
import com.MundoSenai.Presenca.Service.s_Pessoa;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("usuario")
public class C_Pessoa {
    @GetMapping("/")
    public String helloWord(){return "Login/login";}

    @PostMapping("/")
    public String postLogin(@RequestParam("usuario") String usuario, @RequestParam("senha") String senha, HttpSession session){
        session.setAttribute("usuario", s_Pessoa.getPessoaLogin(usuario, senha));
        if(session.getAttribute("usuario") == null){
            return "Login/login";
        }else{
            return "redirect:/Home";
        }
    }

    @ModelAttribute("usuario")
    public m_Pessoa getUsuario(HttpSession session){
      return (m_Pessoa) session.getAttribute("usuario");
    }

    @GetMapping("/Home")
    public String getHome(@ModelAttribute("usuario") String usuario){
        if(usuario != null){
            //a sessao existe, redirecionar para a pagina home
            return "Home/home";
        }else{
            //a sessao não existe redirecinar para a pagina de login
            return "redirect:/";
        }
    }

    @GetMapping("/cadastro")
    public String getCadastro(){
        return "Pessoa/cadastro";
    }

    @PostMapping("/cadastro")
    public String postCadastro(@RequestParam("nome") String nome, @RequestParam("email") String email, @RequestParam("cpf") String cpf,
                               @RequestParam("telefone") String telefone, @RequestParam("data_nasc") String Datanasc,
                               @RequestParam("senha") String senha, @RequestParam("confsenha") String confsenha, Model model){
        String mensagem = s_Pessoa.cadastrarPessoa(nome, email, cpf, telefone, Datanasc, senha, confsenha);
        model.addAttribute("mensagem", mensagem);
        model.addAttribute("nome", nome);
        model.addAttribute("email" , email);
        model.addAttribute("cpf" , cpf);
        model.addAttribute("telefone" , telefone);
        model.addAttribute("data_nasc" , Datanasc);
        return "Pessoa/cadastro";
    }

}
