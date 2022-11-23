package edu.asu.ser421.booktown.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.asu.ser421.booktown.model.Author;
import edu.asu.ser421.booktown.model.Book;
import edu.asu.ser421.booktown.model.exceptions.BooktownInternalException;
import edu.asu.ser421.booktown.model.exceptions.BooktownEntityNotFoundException;
import edu.asu.ser421.booktown.services.BooktownService;

// Normally thsi would be an interface and we'd allow variability in implementation
public class MockBooktownServiceImpl implements BooktownService {
	// This is a mock object for now
	private ArrayList<Author> dummyAuthors = new ArrayList<Author>();
	
	public MockBooktownServiceImpl() {
		List<Book> frostBooks  = new ArrayList<Book>();
		List<Book> fowlerBooks = new ArrayList<Book>();
		
		frostBooks.add(new Book("123456789", "The Road Not Taken", 0));
		frostBooks.add(new Book("987654321", "Stopping by the Woods on a Snowy Evening", 0));
		
		fowlerBooks.add(new Book("111111111", "UML Distilled", 1));
		fowlerBooks.add(new Book("222222222", "Analysis Patterns", 1));
		fowlerBooks.add(new Book("333333333", "Refactoring", 1));
		
		dummyAuthors.addAll(Arrays.asList(
				new Author(0, "Frost", "Robert", frostBooks),
				new Author(1, "Fowler", "Martin", fowlerBooks),
				new Author(2, "Gary", "Kevin", new ArrayList<Book>())
		));
	}
	
	@Override
	public List<Author> getAuthors() {
		return dummyAuthors;
	}
	@Override
	public Author getAuthor(int id) throws BooktownEntityNotFoundException {
		for (Author a : dummyAuthors) {
			if (a.getAuthorID() == id) return a;
		}
		throw new BooktownEntityNotFoundException();
	}
	
	// I like service method to return something to indicate success or failure. In this case the ID
	@Override
	public Author createAuthor(String lastName, String firstName, List<Book> titles) {
		try {
			Author rval = new Author(dummyAuthors.size(), lastName, firstName, titles);
			if (dummyAuthors.add(rval)) {
				return rval;
			} else {
				throw new BooktownInternalException("Internal Service Error, unable to create Author");
			}
		} catch (Throwable t) {
			throw new BooktownInternalException();
		}
	}

	@Override
	public Author updateAuthor(Author author) throws BooktownEntityNotFoundException {
		try {
			int i = 0;
			for (;i < dummyAuthors.size() && dummyAuthors.get(i).getAuthorID() != author.getAuthorID(); i++) {}

			if (i != dummyAuthors.size()) {
				dummyAuthors.set(i, author);
				return author;
			} else {
				throw new BooktownEntityNotFoundException();
			}
		} catch (Throwable t) {
			t.printStackTrace();
			throw new BooktownInternalException();
		}
	}
	
	@Override
	public Author modifyAuthor(Author partialAuthor) throws BooktownEntityNotFoundException {
		// the client contract is to only provide the updated parts, so we can test for null here
		// First we must find the Author though
		try {
			int i = 0;
			for (;i < dummyAuthors.size() && dummyAuthors.get(i).getAuthorID() != partialAuthor.getAuthorID(); i++) {}
			
			if (i != dummyAuthors.size()) {
				Author patchAuthor = dummyAuthors.get(i);
				// now go property-by-property through AuthorRequest
				if (partialAuthor.getFirstName() != null) {
					patchAuthor.setFirstName(partialAuthor.getFirstName());
				}
				if (partialAuthor.getLastName() != null) {
					patchAuthor.setLastName(partialAuthor.getLastName());
				}
				if (partialAuthor.getBooks() != null) {
					patchAuthor.setBooks(partialAuthor.getBooks());
				}

				return patchAuthor;
			} else {
				throw new BooktownEntityNotFoundException();
			}
		} catch (Throwable t) {
			throw new BooktownInternalException();
		}
	}
	
	@Override
	public Author deleteAuthor(int id) throws BooktownEntityNotFoundException {
		try {
			// First we must find the Author though
			int i = 0;
			for (;i < dummyAuthors.size() && dummyAuthors.get(i).getAuthorID() != id; i++) {}
				
			if (i != dummyAuthors.size()) {
				return dummyAuthors.remove(i);
			} else {
				throw new BooktownEntityNotFoundException();
			}
		} catch (Throwable t) {
			throw new BooktownInternalException();
		}
	}
	
	// Book methods
	public List<Book> getBooks() {
		// We don't store Books separately so we have to go through all of the Authors
		List<Book> rval = new ArrayList<Book>();
		for (Author a : dummyAuthors) {
			rval.addAll(a.getBooks());
		}
		return rval;
	}
	public Book getBook(String isbn) throws BooktownEntityNotFoundException  {
		// We don't store Books separately so we have to go through all of the Authors and find ours
		for (Author a : dummyAuthors) {
			for (Book b : a.getBooks()) {
				if (b.getIsbn().equals(isbn)) {
					return b;
				}
			}
		}
		throw new BooktownEntityNotFoundException("No Book found with ISBN " + isbn);
	}
	
	public Book deleteBook(String isbn) throws BooktownEntityNotFoundException {
		// We don't store Books separately so we have to go through all of the Authors and find ours
		AuthorLoop:
		for (Author a : dummyAuthors) {
			for (Book b : a.getBooks()) {
				if (b.getIsbn().equals(isbn)) {
					// now that we found it we have to delete it from Author
					if (a.removeBook(b)) {
						System.out.println("Found Book with ISBN " + isbn + ", removing it!");
						b.setAuthorId(-1);
						return b;
					} else {
						break AuthorLoop;  // This is so bad, I am so lazy.
					}
				}
			}
		}
		// if we made it here we never found the Book or we had an error removing
		throw new BooktownEntityNotFoundException("No Book found with ISBN " + isbn);
	}
}
