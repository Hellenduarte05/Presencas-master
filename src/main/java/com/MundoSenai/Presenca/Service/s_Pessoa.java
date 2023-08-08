package com.MundoSenai.Presenca.Service;

import com.MundoSenai.Presenca.Model.m_Pessoa;
import com.MundoSenai.Presenca.Repostory.R_Pessoa;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.MundoSenai.Presenca.Service.NumberCleaner.cleanNumber;

@Service
public class s_Pessoa {
    private static R_Pessoa r_Pessoa;

    public s_Pessoa(R_Pessoa r_Pessoa){
        this.r_Pessoa = r_Pessoa;
    }

    public static m_Pessoa getPessoaLogin(String usuario, String senha){
        usuario = NumberCleaner.cleanNumber(usuario);
        if(usuario.equals("")){
            return r_Pessoa.findByUsuarioESenha(null,senha);
        }else{
            return r_Pessoa.findByUsuarioESenha(Long.valueOf(usuario),senha);
        }
    }

    public static String cadastrarPessoa(String nome, String email, String cpf, String telefone, String datanasc, String senha, String confsenha){
        boolean cadastroValido = true;
        String mensagemReturn = "";
        telefone = NumberCleaner.cleanNumber(telefone);
        if(telefone.equals("")){
            telefone = null;
        }
        if(!senha.equals(confsenha)) {
            mensagemReturn += "A Senha e a Confirmação de Senha devem ser iguais<br/>";
            cadastroValido = false;
        }
        if(!CpfValidator.validateCPF(cpf)){
            mensagemReturn += "CPF Inválido<br/>";
            cadastroValido = false;
        }
        if(nome == null || nome.trim() == ""){
            mensagemReturn += "Deve ser informado o Nome<br/>";
            cadastroValido = false;
        }
        if ((email == null || email.trim() == "")
                && (telefone == null)){
            mensagemReturn += "e-Mail ou Telefone precisa ser informado<br/>";
            cadastroValido = false;
        }
        if(cadastroValido){
        m_Pessoa M_Pessoa = new m_Pessoa();
        M_Pessoa.setNome(nome);
        M_Pessoa.setCpf(Long.valueOf(cleanNumber(cpf)));
        if(telefone != null){
           M_Pessoa.setTelefone(Long.valueOf(telefone));
        }
        M_Pessoa.setEmail(email);
        M_Pessoa.setData_nasc(LocalDate.parse(datanasc));
        M_Pessoa.setSenha(senha);
        try{
            r_Pessoa.save(M_Pessoa);
            mensagemReturn +=  "Cadastro efetuado com sucesso";
        }catch (DataIntegrityViolationException e){
            if(e.getMessage().contains("u_cpf")){
                mensagemReturn += "o Cpf informado já foi cadastro";
            }else{
                mensagemReturn += "Erro ao cadastrar";
            }
            mensagemReturn += e.getMessage();
        }
    }
     return mensagemReturn;
    }
}
