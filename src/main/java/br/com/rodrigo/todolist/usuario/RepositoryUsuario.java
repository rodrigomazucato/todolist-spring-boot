package br.com.rodrigo.todolist.usuario;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryUsuario extends JpaRepository<ModelUsuario, UUID>{
    Optional<ModelUsuario> findByNickName(String nickName);
}
