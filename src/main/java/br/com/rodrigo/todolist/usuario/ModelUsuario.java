package br.com.rodrigo.todolist.usuario;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_usuario")
public class ModelUsuario {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String nickName;

    private String nome;
    private String senha;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
