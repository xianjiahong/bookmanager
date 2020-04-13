package com.book.service;

import java.util.List;

import com.book.pojo.BookCategory;

public interface CategoryService {

	boolean saveCategory(String category);

	List<BookCategory> getCategoryList();
	
}
