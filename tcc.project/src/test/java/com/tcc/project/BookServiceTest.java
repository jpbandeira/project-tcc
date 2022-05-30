package com.tcc.project;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tcc.project.model.Book;
import com.tcc.project.services.BookService;

public class BookServiceTest {

	private BookService bookService;

	public Book setUp() {
		return new Book(UUID.randomUUID(), "title", true);
	}

	public void cleanList() {
		BookService.books.clear();
	}

	@Before
	public void before() {
		this.bookService = new BookService();
	}

	@Test
	public void shouldNotFindAllBooks() {
		ArrayList<Book> books = this.bookService.findAll();

		Assert.assertNull(books);
	}

	@Test
	public void bookShouldNotBeAdded() {
		ArrayList<Book> books = this.bookService.addBook(null);

		Assert.assertNull(books);
	}

	@Test
	public void bookShouldBeAdded() {
		Book bookSetUp = setUp();

		ArrayList<Book> books = this.bookService.addBook(bookSetUp);

		Assert.assertNotNull(books);
		this.cleanList();
	}

	@Test
	public void findABookWithInvalidUUID() {
		Book book = this.bookService.find(UUID.randomUUID());

		Assert.assertNull(book);
	}

	@Test
	public void findABookWithANullUUID() {
		Book book = this.bookService.find(null);

		Assert.assertNull(book);
	}

	@Test
	public void shouldFindABook() {
		Book bookSetUp = setUp();
		this.bookService.addBook(bookSetUp);

		Book book = this.bookService.find(bookSetUp.getUuid());

		Assert.assertNotNull(book);
		this.cleanList();
	}

	@Test
	public void deleteABookWithInvalidUUID() {
		Book bookSetUp = setUp();
		this.bookService.addBook(bookSetUp);

		this.bookService.delete(UUID.randomUUID());

		Assert.assertNotNull(this.bookService.find(bookSetUp.getUuid()));
		this.cleanList();
	}

	@Test
	public void deleteABookWithANullUUID() {
		Book bookSetUp = setUp();
		this.bookService.addBook(bookSetUp);

		this.bookService.delete(null);

		Assert.assertNotNull(this.bookService.find(bookSetUp.getUuid()));
		this.cleanList();
	}

	@Test
	public void bookShouldBeDeleted() {
		Book bookSetUp = setUp();
		this.bookService.addBook(bookSetUp);

		this.bookService.delete(bookSetUp.getUuid());

		Assert.assertNull(this.bookService.find(bookSetUp.getUuid()));
		this.cleanList();
	}


	@Test
	public void shouldFindAllBooks() {
		Book bookSetUp1 = setUp();
		Book bookSetUp2 = setUp();
		this.bookService.addBook(bookSetUp1);
		this.bookService.addBook(bookSetUp2);

		ArrayList<Book> books = this.bookService.findAll();

		Assert.assertNotNull(books);
		this.cleanList();
	}

	@Test
	public void shouldNotUpdateABookRegister() {
		Book updated = this.bookService.update(null);

		Assert.assertNull(updated);
	}

	@Test
	public void updateABookWithAnInvalidObjetc() {
		Book bookSetUp = setUp();
		this.bookService.addBook(setUp());

		Book updated = this.bookService.update(bookSetUp);

		Assert.assertNull(updated);
		this.cleanList();
	}

	@Test
	public void shouldUpdateABookRegister() {
		Book bookSetUp = setUp();
		this.bookService.addBook(bookSetUp);

		Book bookToUpdate = new Book(bookSetUp.getUuid(), "Title 2", false);

		Book updated = this.bookService.update(bookToUpdate);

		Assert.assertEquals(bookToUpdate.getTitle(), updated.getTitle());
		Assert.assertEquals(bookToUpdate.isRare(), updated.isRare());
		Assert.assertNotNull(updated);
		this.cleanList();
	}

}
