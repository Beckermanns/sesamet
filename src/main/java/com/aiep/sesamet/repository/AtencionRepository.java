package com.aiep.sesamet.repository;

import com.aiep.sesamet.domain.Atencion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Atencion entity.
 */
@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Long> {
    default Optional<Atencion> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Atencion> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Atencion> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select atencion from Atencion atencion left join fetch atencion.centroMedico left join fetch atencion.doctor left join fetch atencion.paciente",
        countQuery = "select count(atencion) from Atencion atencion"
    )
    Page<Atencion> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select atencion from Atencion atencion left join fetch atencion.centroMedico left join fetch atencion.doctor left join fetch atencion.paciente"
    )
    List<Atencion> findAllWithToOneRelationships();

    @Query(
        "select atencion from Atencion atencion left join fetch atencion.centroMedico left join fetch atencion.doctor left join fetch atencion.paciente where atencion.id =:id"
    )
    Optional<Atencion> findOneWithToOneRelationships(@Param("id") Long id);
}
