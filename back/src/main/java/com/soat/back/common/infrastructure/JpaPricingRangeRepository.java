package com.soat.back.common.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPricingRangeRepository  extends CrudRepository<JpaPricingRange, Integer> {
}
