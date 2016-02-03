package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SmsSetting;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SmsSetting entity.
 */
public interface SmsSettingRepository extends JpaRepository<SmsSetting,Long> {

}
