package com.tcc.project.services;

import java.util.ArrayList;
import java.util.UUID;

import com.tcc.project.model.Book;

public class BookService {

	public static ArrayList<Book> books = new ArrayList<>();

	public BookService() {}

	public ArrayList<Book> addBook(Book book) {
		if(book == null) {
			return null;
		}
		
		books.add(book);
		
		return books;
	}
	
	public ArrayList<Book> findAll(){
		if (books.isEmpty()) {
			return null;
		}
		
		return books;
	}
	
	public Book find(UUID uuid) {
		if (uuid == null ) {
			return null;
		}
		
		for (Book book : books) {
			if (book.getUuid().equals(uuid)) {
				return book;
			}
		}
		
		return null;
	}
	
	public void delete(UUID uuid) {
		Book book = new Book();
		
		if (uuid != null) {
			book = find(uuid);
		}
		
		if (book != null) {
			books.remove(book);
		}
	}
	
	public Book update(Book book) {
		if (book == null) {
			return null;
		}
		
		Book bookToUpdate = find(book.getUuid());
		
		if (bookToUpdate == null) {
			return null;
		}
		
		updateBook(book, bookToUpdate);
		
		return bookToUpdate;
	}
	
	public void updateBook(Book newBook, Book oldBook) {
		oldBook.setTitle(newBook.getTitle());
		oldBook.setRare(newBook.isRare());
	}

}
