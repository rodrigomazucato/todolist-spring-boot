package br.com.rodrigo.todolist.tarefa;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tb_tarefa")
public class ModelTarefa {
    @Id
    @UuidGenerator
    private UUID idTarefa;
    private UUID idUsuario;

    @Column(length = 50)
    private String titulo;
    private LocalDateTime dataInicio;
    private LocalDateTime dataTermino;
    private String prioridade;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitulo(String titulo) throws Exception {
        if (titulo.length() > 50){
            throw new Exception("O campo título deve ter até 50 caracteres!");
        }
        this.titulo = titulo;
    }
    
}
