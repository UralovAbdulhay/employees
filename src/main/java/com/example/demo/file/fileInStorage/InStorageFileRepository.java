package com.example.demo.file.fileInStorage;


        import org.springframework.data.jpa.repository.JpaRepository;

        import java.util.List;
        import java.util.Optional;
        import java.util.UUID;

public interface InStorageFileRepository extends JpaRepository<InStorageFile, UUID> {

    void deleteByHashId(String hashId);



    Optional<InStorageFile> findByHashId(String hashId);

    List<InStorageFile> findByHashIdIn(List<String> hashId);

    boolean existsByHashId(String hashId);




}
