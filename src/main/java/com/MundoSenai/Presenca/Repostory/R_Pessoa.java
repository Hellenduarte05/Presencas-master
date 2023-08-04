package com.MundoSenai.Presenca.Repostory;

import com.MundoSenai.Presenca.Model.m_Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface R_Pessoa extends JpaRepository<m_Pessoa, Long> {
    @Query(value="SELECT * FROM pessoa WHERE cpf = :usuario and senha =:senha limit 1",nativeQuery = true)
    m_Pessoa findByUsuarioESenha(@Param("usuario") Long usuario, @Param ("senha") String senha);
}
