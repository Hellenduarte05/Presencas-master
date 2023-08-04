package com.MundoSenai.Presenca.Service;

import com.MundoSenai.Presenca.Model.m_Pessoa;
import com.MundoSenai.Presenca.Repostory.R_Pessoa;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class s_Pessoa {
    private static R_Pessoa r_Pessoa;

    public s_Pessoa(R_Pessoa r_Pessoa){
        this.r_Pessoa = r_Pessoa;
    }

    public static m_Pessoa getPessoaLogin(String usuario, String senha){
        return r_Pessoa.findByUsuarioESenha(Long.valueOf(usuario),senha);
    }

    public static String cadastrarPessoa(String nome, String email, String cpf, String telefone, String datanasc, String senha, String confsenha){
          String mensagemReturn = "";
        if(!senha.equals(confsenha)) {
            mensagemReturn += "A Senha e a Confirmação de Senha devem serm iguais";
        }if(!CpfValidator.validateCPF(cpf)){
            mensagemReturn += "CPF Inválido";
        }if(nome == null || nome.trim() == ""){
            mensagemReturn += "Deve ser informado o Nome";
        }if ((email == null || email.trim() == "")
                && (NumberCleaner.cleanNumber(telefone) == null
                || NumberCleaner.cleanNumber(telefone).trim() == "")){
            mensagemReturn += "e-Mail ou Telefone precisa ser informado";
    }else{
        m_Pessoa M_Pessoa = new m_Pessoa();
        M_Pessoa.setNome(nome);
        M_Pessoa.setCpf(Long.valueOf(NumberCleaner.cleanNumber(cpf)));
        M_Pessoa.setTelefone(Long.valueOf(NumberCleaner.cleanNumber(telefone)));
        M_Pessoa.setEmail(email);
        M_Pessoa.setData_nasc(LocalDate.parse(datanasc));
        M_Pessoa.setSenha(senha);
        r_Pessoa.save(M_Pessoa);
        mensagemReturn +=  "Cadastro efetuado com sucesso";
    }
     return mensagemReturn;
    }
}
