package org.jahanzaib.personalsite.prayertimes.repository;

import org.jahanzaib.personalsite.prayertimes.entity.Mosque;
import org.springframework.data.repository.Repository;

public interface MosqueRepository extends Repository<Mosque, Integer> {
    Mosque getById(int id);
}
