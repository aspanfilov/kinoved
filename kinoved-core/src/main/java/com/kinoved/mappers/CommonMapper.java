package com.kinoved.mappers;

public interface CommonMapper<E, D> {
    E toEntity(D dto);
//    D toDto(E entity);
}
