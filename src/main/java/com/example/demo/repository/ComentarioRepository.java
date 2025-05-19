
package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
<<<<<<< Updated upstream
    
}
=======
   	List<Comentario> findByActividad_IdActividad(Long idActividad);

}
>>>>>>> Stashed changes
