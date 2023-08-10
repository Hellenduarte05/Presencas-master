package com.MundoSenai.Presenca.Controller;

import com.MundoSenai.Presenca.Model.M_resposta;
import com.MundoSenai.Presenca.Model.m_Pessoa;
import com.MundoSenai.Presenca.Service.s_Pessoa;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes("usuario")
public class C_Pessoa {
    @GetMapping("/")
    public String helloWord(){return "Login/login";}

    @PostMapping("/")
    public String postLogin(@RequestParam("usuario") String usuario, @RequestParam("senha") String senha, HttpSession session,RedirectAttributes redirectAttributes ){
        m_Pessoa pessoa = s_Pessoa.getPessoaLogin(usuario, senha);
        session.setAttribute("usuario", pessoa);
        if(session.getAttribute("usuario") == null){
            return "Login/login";
        }else{
            redirectAttributes.addFlashAttribute("nome", pessoa.getNome());
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
    public RedirectView postCadastro(@RequestParam("nome") String nome, @RequestParam("email") String email, @RequestParam("cpf") String cpf,
                                     @RequestParam("telefone") String telefone, @RequestParam("data_nasc") String Datanasc,
                                     @RequestParam("senha") String senha, @RequestParam("confsenha") String confsenha,
                                     RedirectAttributes redirectAttributes) {
        M_resposta resposta = s_Pessoa.cadastrarPessoa(nome, email, cpf, telefone, Datanasc, senha, confsenha);
        redirectAttributes.addFlashAttribute("mensagem", resposta.getMensagem());
        if (resposta.getSucesso()) {
            return new RedirectView("/" , true);
        } else {
            redirectAttributes.addFlashAttribute("nome", nome);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("cpf", cpf);
            redirectAttributes.addFlashAttribute("telefone", telefone);
            redirectAttributes.addFlashAttribute("data_nasc", Datanasc);
            return new RedirectView("/cadastro", true);
        }
    }
}
