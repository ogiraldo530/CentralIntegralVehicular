package com.central.integral.interfaces;

import org.springframework.data.repository.PagingAndSortingRepository;


import com.central.integral.entity.ItemCotizacionArticulo;

public interface IItemArticuloDAO extends PagingAndSortingRepository<ItemCotizacionArticulo, Long>{

}
