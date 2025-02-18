package br.com.rodrigo.todolist.tarefa;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryTarefa extends JpaRepository<ModelTarefa, UUID>{
    ModelTarefa[] findByIdUsuario(UUID idUsuario);
}
