package com.tipcrm.dao.repository;
import com.tipcrm.dao.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, String> {

    Attachment findByIdAndExtAndTypeId(String id, String ext, Integer typeId);

    Attachment findByIdAndTypeId(String id, Integer typeId);
}
